/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.sql.template.impl;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.ds.DsManager;
import com.wee0.box.sql.template.ISqlTemplateHelper;
import com.wee0.box.template.TemplateHandler;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.IoUtils;
import com.wee0.box.util.shortcut.StringUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectStreamException;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 7:08
 * @Description 一个简单的数据库相关模板生成助手实现
 * <pre>
 * 此组件依赖ITemplateHandler, IDsManager。
 * </pre>
 **/
public class SimpleSqlTemplateHelper implements ISqlTemplateHelper {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleSqlTemplateHelper.class);

    @Override
    public void generateEntities(Map<String, Object> dataModel, String templateName, File outputDirectory, Set<String> excludeColumns, Set<String> excludeTables, INamePolicy namePolicy) {
        CheckUtils.checkNotNull(dataModel, "dataModel can't be null!");
        templateName = CheckUtils.checkNotTrimEmpty(templateName, "templateName can't be empty!");
        CheckUtils.checkNotNull(outputDirectory, "outputDirectory can't be null!");
        if (!outputDirectory.exists())
            outputDirectory.mkdirs();
        if (null != excludeTables) {
            excludeTables = excludeTables.stream().map(String::toUpperCase).collect(Collectors.toSet());
        } else {
            excludeTables = Collections.emptySet();
        }

        List<SimpleTableInfo> _tables;
        try {
            _tables = findTables(excludeColumns);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }

        for (SimpleTableInfo _tableInfo : _tables) {
            String _tableName = _tableInfo.getName();
            String _tableNameUpper = _tableName.toUpperCase();
            if (excludeTables.contains(_tableNameUpper)) {
                log.debug("exclude table: {}", _tableName);
                continue;
            }
            String _entityName = toJavaName(_tableName);
            if (null != namePolicy)
                _entityName = namePolicy.renameEntity(_tableName, _entityName);
            Map<String, Object> _dataModel = createDefaultDataModel(_tableNameUpper, _entityName, _tableInfo.getComment(), _tableInfo.getColumns());
            _dataModel.putAll(dataModel);
            String _entityContent = TemplateHandler.impl().process(templateName, _dataModel);
            String _entityFileName = _entityName + ".java";
            log.debug("generator entity from {} to {}.", _tableName, _entityFileName);
            try (FileOutputStream _outputStream = new FileOutputStream(new File(outputDirectory, _entityFileName));) {
                IoUtils.impl().write(_entityContent, _outputStream);
            } catch (IOException e) {
                throw new BoxRuntimeException(e);
            }
        }
    }

    @Override
    public void generateEntities(Map<String, Object> dataModel, String templateName, File outputDirectory, Set<String> excludeColumns) {
        generateEntities(dataModel, templateName, outputDirectory, excludeColumns, null, null);
    }

    // 查找所有表信息
    private List<SimpleTableInfo> findTables(Set<String> excludeColumns) throws SQLException {
        if (null != excludeColumns) {
            excludeColumns = excludeColumns.stream().map(String::toUpperCase).collect(Collectors.toSet());
        }
        List<SimpleTableInfo> _tables = new ArrayList<>(32);
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            DatabaseMetaData _metaData = _conn.getMetaData();
//            List<Map<String, Object>> _typeList = new ArrayList<>();
//            try (ResultSet _typeRS = _metaData.getTypeInfo();) {
//                while (_typeRS.next()) {
//                    Map<String, Object> _typeItem = new HashMap<>(3);
//                    int _dataType = _typeRS.getInt("DATA_TYPE");
//                    _typeItem.put("dataType", _dataType);
//                    _typeItem.put("jdbcType", _typeRS.getString("TYPE_NAME"));
//                    _typeItem.put("javaType", toJavaType(_dataType));
//                    _typeList.add(_typeItem);
//                }
//            }

            boolean _isOracle = _metaData.getDatabaseProductName().toLowerCase().contains("oracle");
            try (ResultSet _tableRS = _isOracle ? _metaData.getTables(_conn.getCatalog(), _metaData.getUserName().toUpperCase(), null, new String[]{"TABLE"}) : _metaData.getTables(null, "%", "%", new String[]{"TABLE"});) {
                w1:
                while (_tableRS.next()) {
                    String _tableType = _tableRS.getString("TABLE_TYPE");
                    if (!"table".equalsIgnoreCase(_tableType))
                        continue w1;
                    String _tableName = _tableRS.getString("TABLE_NAME");
                    String _tableComment = _tableRS.getString("REMARKS");

                    List<Map<String, Object>> _columnList = new ArrayList<>();
                    try (ResultSet _columnRS = _metaData.getColumns(null, null, _tableName, null);) {
                        w2:
                        while (_columnRS.next()) {
                            Map<String, Object> _columnItem = new HashMap();
                            String _columnName = _columnRS.getString("COLUMN_NAME");
                            String _columnNameUpper = _columnName.toUpperCase();
                            if (null != excludeColumns && excludeColumns.contains(_columnNameUpper))
                                continue w2;
                            String _columnJavaName = toJavaName(_columnName);
                            _columnItem.put("nameJavaField", StringUtils.unCapitalize(_columnJavaName));
                            _columnItem.put("nameJava", _columnJavaName);
                            _columnItem.put("name", _columnNameUpper);
                            Integer _dataType = _columnRS.getInt("DATA_TYPE");
                            _columnItem.put("type", _dataType);
                            _columnItem.put("typeJdbc", _columnRS.getString("TYPE_NAME"));
                            _columnItem.put("typeJava", toJavaType(_dataType));
                            _columnItem.put("comment", _columnRS.getString("REMARKS"));
                            _columnList.add(_columnItem);
                        }
                    }
                    _tables.add(new SimpleTableInfo(_tableName, _tableComment, _columnList));
                }
            }
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
        return _tables;
    }

    // 创建一个默认的数据模型
    private static Map<String, Object> createDefaultDataModel(String tableName, String entityName, String tableComment, List<Map<String, Object>> columns) {
        Map<String, Object> _dataModel = new HashMap<>();
        _dataModel.put("tableComment", tableComment);
        _dataModel.put("tableName", tableName);
        _dataModel.put("entityName", entityName);
        _dataModel.put("columns", columns);
        return _dataModel;
    }

    // jdbc类型转java类型
    private static String toJavaType(int type) {
        String _result = "String";
        switch (type) {
            case Types.INTEGER:
            case Types.TINYINT:
                _result = "Integer";
                break;
            case Types.BIGINT:
                _result = "Long";
                break;
            case Types.FLOAT:
            case Types.DOUBLE:
            case Types.DECIMAL:
            case Types.REAL:
                _result = "Double";
                break;
            case Types.BOOLEAN:
            case Types.BIT:
                _result = "Boolean";
                break;
            case Types.DATE:
            case Types.TIME:
            case Types.TIMESTAMP:
                _result = "java.util.Date";
                break;
            case Types.CHAR:
            case Types.VARCHAR:
            case Types.NVARCHAR:
                _result = "String";
                break;
        }
        return _result;
    }

    // 数据库列名转java字段名
    private static String toJavaName(String columnName) {
        if (null == columnName || 0 == (columnName = columnName.trim()).length())
            return "";
        StringBuilder _builder = new StringBuilder(columnName.length());
        _builder.append(Character.toUpperCase(columnName.charAt(0)));
        for (int _i = 1, _iLen = columnName.length(); _i < _iLen; _i++) {
            char _c = columnName.charAt(_i);
            if ('_' == _c) {
                _builder.append(Character.toUpperCase(columnName.charAt(++_i)));
            } else {
                _builder.append(Character.toLowerCase(_c));
            }
        }
        return _builder.toString();
    }


    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleSqlTemplateHelper() {
        if (null != SimpleSqlTemplateHelperHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleSqlTemplateHelperHolder {
        private static final SimpleSqlTemplateHelper _INSTANCE = new SimpleSqlTemplateHelper();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleSqlTemplateHelperHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleSqlTemplateHelper me() {
        return SimpleSqlTemplateHelperHolder._INSTANCE;
    }

}
