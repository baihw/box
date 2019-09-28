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

package com.wee0.box.sql.impl;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.ISqlHelper;
import com.wee0.box.sql.ds.DsManager;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.ClassUtils;

import java.beans.Introspector;
import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.sql.*;
import java.util.*;
import java.util.function.Function;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:22
 * @Description 一个简单的SQL操作助手实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleSqlHelper implements ISqlHelper {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleSqlHelper.class);

    // 检查JDBC 4.1 中的 getObject(int, Class) 方法是否有效
    private static final boolean getObjectWithTypeAvailable = ClassUtils.hasMethod(ResultSet.class, "getObject", int.class, Class.class);


    @Override
    public int update(String sql, Object[] sqlParams) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return update(_conn, sql, sqlParams);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public int[] batch(String sql, Object[][] sqlParams) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return batch(_conn, sql, sqlParams);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> queryMap(String sql, Object[] sqlParams) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return queryMap(_conn, sql, sqlParams);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public List<Map<String, Object>> queryMapList(String sql, Object[] sqlParams) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return queryMapList(_conn, sql, sqlParams);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public <T> T queryBean(String sql, Object[] sqlParams, Class<T> type) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        CheckUtils.checkNotNull(type, "type cannot be null!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return queryBean(_conn, sql, sqlParams, type);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public <T> List<T> queryBeanList(String sql, Object[] sqlParams, Class<T> type) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        CheckUtils.checkNotNull(type, "type cannot be null!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return queryBeanList(_conn, sql, sqlParams, type);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public <T> T queryScalar(String sql, Object[] sqlParams, Class<T> requiredType) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return queryScalar(_conn, sql, sqlParams, requiredType);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Long queryScalar(String sql, Object[] sqlParams) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return queryScalar(_conn, sql, sqlParams);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Long queryScalar(String sql) {
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (Connection _conn = DsManager.impl().getDefaultDataSource().getConnection();) {
            return queryScalar(_conn, sql);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public int update(Connection conn, String sql, Object[] sqlParams) {
        CheckUtils.checkNotNull(conn, "conn can't be null!");
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (PreparedStatement _statement = conn.prepareStatement(sql);) {
            // sql参数处理
            setStatementParameters(_statement, sqlParams);
            // 执行sql，获取结果。
            return _statement.executeUpdate();
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public int[] batch(Connection conn, String sql, Object[][] sqlParams) {
        CheckUtils.checkNotNull(conn, "conn can't be null!");
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (PreparedStatement _statement = conn.prepareStatement(sql);) {
            int[] _types = getStatementParameterTypes(_statement);
            // sql参数处理
            if (null != sqlParams && 0 != sqlParams.length) {
                for (Object[] _sqlRowParams : sqlParams) {
                    for (int _i = 0, _iLen = _sqlRowParams.length; _i < _iLen; _i++) {
                        Object _paramObj = _sqlRowParams[_i];
                        if (null == _paramObj) {
                            _statement.setNull(_i + 1, _types[_i]);
                        } else {
                            _statement.setObject(_i + 1, _paramObj);
//                            _statement.setObject(_i + 1, _paramObj, _types[_i]);
                        }
                    }
                    _statement.addBatch();
                }
            }
            // 执行sql，获取结果。
            return _statement.executeBatch();
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> queryMap(Connection conn, String sql, Object[] sqlParams) {
        return doQuery(conn, sql, new Function<ResultSet, Map>() {
            @Override
            public Map<String, Object> apply(ResultSet resultSet) {
                if (null == resultSet)
                    return Collections.emptyMap();
                try {
                    ResultSetMetaData _metaData = resultSet.getMetaData();
                    int _colCount = _metaData.getColumnCount();
                    if (resultSet.next()) {
                        Map<String, Object> _result = new LinkedHashMap<>(_colCount);
                        for (int _i = 0; _i < _colCount; _i++) {
                            String _key = lookupColumnName(_metaData, _i + 1);
                            Object _value = getResultSetValue(resultSet, _i + 1);
                            _result.put(_key, _value);
                        }
                        return _result;
                    }
                    return Collections.emptyMap();
                } catch (SQLException e) {
                    throw new BoxRuntimeException(e);
                }
            }
        }, sqlParams, Map.class);
    }

    @Override
    public List<Map<String, Object>> queryMapList(Connection conn, String sql, Object[] sqlParams) {
        return doQuery(conn, sql, new Function<ResultSet, List>() {
            @Override
            public List<Map<String, Object>> apply(ResultSet resultSet) {
                if (null == resultSet)
                    return Collections.emptyList();
                try {
                    ResultSetMetaData _metaData = resultSet.getMetaData();
                    int _colCount = _metaData.getColumnCount();
                    String[] _colNames = new String[_colCount];
                    for (int _i = 0; _i < _colCount; _i++)
                        _colNames[_i] = lookupColumnName(_metaData, _i + 1);

                    List<Map<String, Object>> _result = new ArrayList<>();
                    while (resultSet.next()) {
                        Map<String, Object> _rowMap = new LinkedHashMap<>(_colCount);
                        for (int _i = 0; _i < _colCount; _i++) {
                            Object _value = getResultSetValue(resultSet, _i + 1);
                            _rowMap.put(_colNames[_i], _value);
                        }
                        _result.add(_rowMap);
                    }
                    return _result;
                } catch (SQLException e) {
                    throw new BoxRuntimeException(e);
                }
            }
        }, sqlParams, List.class);
    }

    @Override
    public <T> T queryBean(Connection conn, String sql, Object[] sqlParams, Class<T> type) {
        return doQuery(conn, sql, new Function<ResultSet, T>() {
            @Override
            public T apply(ResultSet resultSet) {
                if (null == resultSet)
                    return null;
                try {
                    ResultSetMetaData _metaData = resultSet.getMetaData();
                    int _colCount = _metaData.getColumnCount();
                    if (resultSet.next()) {
                        T _rowObj = InternalBeanHelper.me().newInstance(type);
                        for (int _i = 0; _i < _colCount; _i++) {
                            String _key = lookupColumnName(_metaData, _i + 1).toLowerCase(Locale.US);
                            Object _value = getResultSetValue(resultSet, _i + 1);
                            InternalBeanHelper.me().setProperty(_rowObj, _key, _value);
                        }
                        return _rowObj;
                    }
                    return null;
                } catch (Exception e) {
                    throw new BoxRuntimeException(e);
                }
            }
        }, sqlParams, type);
    }

    @Override
    public <T> List<T> queryBeanList(Connection conn, String sql, Object[] sqlParams, Class<T> type) {
        return doQuery(conn, sql, new Function<ResultSet, List>() {
            @Override
            public List<T> apply(ResultSet resultSet) {
                if (null == resultSet)
                    return Collections.emptyList();
                try {
                    ResultSetMetaData _metaData = resultSet.getMetaData();
                    int _colCount = _metaData.getColumnCount();
                    String[] _colNames = new String[_colCount];
                    for (int _i = 0; _i < _colCount; _i++)
                        _colNames[_i] = lookupColumnName(_metaData, _i + 1).toLowerCase(Locale.US);

                    List<T> _result = new ArrayList<>();
                    while (resultSet.next()) {
                        T _rowObj = InternalBeanHelper.me().newInstance(type);
                        for (int _i = 0; _i < _colCount; _i++) {
                            Object _value = getResultSetValue(resultSet, _i + 1);
                            InternalBeanHelper.me().setProperty(_rowObj, _colNames[_i], _value);
                        }
                        _result.add(_rowObj);
                    }
                    return _result;
                } catch (Exception e) {
                    throw new BoxRuntimeException(e);
                }
            }
        }, sqlParams, List.class);
    }

    @Override
    public <T> T queryScalar(Connection conn, String sql, Object[] sqlParams, Class<T> requiredType) {
        return doQuery(conn, sql, new Function<ResultSet, T>() {
            @Override
            public T apply(ResultSet resultSet) {
                if (null == resultSet)
                    return null;
                try {
//                    if (1 != resultSet.getMetaData().getColumnCount())
//                        throw new IllegalStateException("column count must be one");
                    if (resultSet.next()) {
                        Object _result = getResultSetValue(resultSet, 1, requiredType);
                        if (null != _result && null != requiredType && !requiredType.isInstance(_result)) {
                            return (T) convertValueToRequiredType(_result, requiredType);
                        }
                        return (T) _result;
                    }
                    return null;
                } catch (SQLException e) {
                    throw new BoxRuntimeException(e);
                }
            }
        }, sqlParams, requiredType);
    }

    @Override
    public Long queryScalar(Connection conn, String sql, Object[] sqlParams) {
        return queryScalar(conn, sql, sqlParams, Long.class);
    }

    @Override
    public Long queryScalar(Connection conn, String sql) {
        return queryScalar(conn, sql, null, Long.class);
    }

    /**
     * 执行查询语句，获取查询结果
     *
     * @param <T>              返回类型
     * @param conn             数据库连接
     * @param sql              sql语句
     * @param resultSetHandler 结果集处理函数
     * @param sqlParams        sql语句参数
     * @param requiredType     结果类型
     * @return 结果对象
     */
    private static <T> T doQuery(Connection conn, String sql, Function<ResultSet, T> resultSetHandler, Object[] sqlParams, Class<T> requiredType) {
        CheckUtils.checkNotNull(conn, "conn can't be null!");
        sql = CheckUtils.checkNotTrimEmpty(sql, "sql can't be empty!");
        try (PreparedStatement _statement = conn.prepareStatement(sql);) {
            // sql参数处理
            setStatementParameters(_statement, sqlParams);

            // 执行sql，获取结果。
            try (ResultSet _resultSet = _statement.executeQuery();) {
                return resultSetHandler.apply(_resultSet);
            }
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    /**
     * 查找列名称
     *
     * @param resultSetMetaData 结果集元数据
     * @param columnIndex       列索引
     * @return 列名称
     * @throws SQLException sql异常
     */
    private static String lookupColumnName(ResultSetMetaData resultSetMetaData, int columnIndex) throws SQLException {
        String _result = resultSetMetaData.getColumnLabel(columnIndex);
        if (null == _result || _result.length() < 1) {
            _result = resultSetMetaData.getColumnName(columnIndex);
        }
        return _result;
    }

    /**
     * 转换结果为指定类型
     *
     * @param value        结果
     * @param requiredType 期望类型
     * @param <T>          类型限制
     * @return 类型结果
     */
    private static <T> T convertValueToRequiredType(Object value, Class<T> requiredType) {
        if (null == value)
            return null;
        if (requiredType.isInstance(value))
            return (T) value;
        if (String.class == requiredType)
            return (T) value.toString();
        if (!Number.class.isAssignableFrom(requiredType)) {
            throw new IllegalArgumentException("Value [" + value + "] is of type [" + value.getClass().getName() + "] and cannot be converted to required type [" + requiredType.getName() + "]");
        }

        Number _numberValue = (Number) value;
        if (Byte.class == requiredType)
            return (T) Byte.valueOf(_numberValue.byteValue());
        if (Short.class == requiredType)
            return (T) Short.valueOf(_numberValue.shortValue());
        if (Integer.class == requiredType)
            return (T) Integer.valueOf(_numberValue.intValue());
        if (Long.class == requiredType)
            return (T) Long.valueOf(_numberValue.longValue());
        if (Float.class == requiredType)
            return (T) Float.valueOf(_numberValue.floatValue());
        if (Double.class == requiredType)
            return (T) Double.valueOf(_numberValue.doubleValue());
        if (BigDecimal.class == requiredType)
            return (T) new BigDecimal(_numberValue.toString());
        if (Float.class == requiredType)
            return (T) Float.valueOf(_numberValue.floatValue());
        if (Float.class == requiredType)
            return (T) Float.valueOf(_numberValue.floatValue());
        if (Float.class == requiredType)
            return (T) Float.valueOf(_numberValue.floatValue());

        throw new IllegalArgumentException("Could not convert number [" + _numberValue + "] of type [" + _numberValue.getClass().getName() + "] to unsupported target class [" + requiredType.getName() + "]");
    }

    /**
     * 获取指定类型的结果
     *
     * @param rs           结果集
     * @param index        索引
     * @param requiredType 对象类型
     * @return 结果对象
     * @throws SQLException sql异常
     */
    private static Object getResultSetValue(ResultSet rs, int index, Class<?> requiredType) throws SQLException {
        if (rs.wasNull())
            return null;
        if (null == requiredType)
            return getResultSetValue(rs, index);

        // 获取指定类型的结果
        if (String.class == requiredType)
            return rs.getString(index);
        if (boolean.class == requiredType || Boolean.class == requiredType)
            return rs.getBoolean(index);
        if (byte.class == requiredType || Byte.class == requiredType)
            return rs.getByte(index);
        if (short.class == requiredType || Short.class == requiredType)
            return rs.getShort(index);
        if (int.class == requiredType || Integer.class == requiredType)
            return rs.getInt(index);
        if (long.class == requiredType || Long.class == requiredType)
            return rs.getLong(index);
        if (float.class == requiredType || Float.class == requiredType)
            return rs.getFloat(index);
        if (double.class == requiredType || Double.class == requiredType || Number.class == requiredType)
            return rs.getDouble(index);

        if (BigDecimal.class == requiredType)
            return rs.getBigDecimal(index);
        if (java.sql.Date.class == requiredType)
            return rs.getDate(index);
        if (java.sql.Time.class == requiredType)
            return rs.getTime(index);
        if (java.sql.Timestamp.class == requiredType || java.util.Date.class == requiredType)
            return rs.getTimestamp(index);
        if (byte[].class == requiredType)
            return rs.getBytes(index);
        if (Blob.class == requiredType)
            return rs.getBlob(index);
        if (Clob.class == requiredType)
            return rs.getClob(index);
        if (requiredType.isEnum()) {
            // 枚举类型只返回字符串或者整型
            Object _obj = rs.getObject(index);
            if (_obj instanceof String) {
                return _obj;
            } else if (_obj instanceof Number) {
                // 数值转换为枚举索引
                return ((Number) _obj).intValue();
            } else {
                // Postgres getObject 返回的是 PGObject，我们返回string。
                return rs.getString(index);
            }
        }

        // 其它未知类型的列依靠 ResultSet.getObject 方法处理。
        if (getObjectWithTypeAvailable) {
            try {
                return rs.getObject(index, requiredType);
            } catch (AbstractMethodError err) {
                log.debug("JDBC driver does not implement JDBC 4.1 'getObject(int, Class)' method", err);
            } catch (SQLFeatureNotSupportedException ex) {
                log.debug("JDBC driver does not support JDBC 4.1 'getObject(int, Class)' method", ex);
            } catch (SQLException ex) {
                log.debug("JDBC driver has limited support for JDBC 4.1 'getObject(int, Class)' method", ex);
            }
        }

        String _typeName = requiredType.getSimpleName();
        if ("LocalDate".equals(_typeName)) {
            return rs.getDate(index);
        } else if ("LocalTime".equals(_typeName)) {
            return rs.getTime(index);
        } else if ("LocalDateTime".equals(_typeName)) {
            return rs.getTimestamp(index);
        }

        // 返回没有指定类型的结果
        return getResultSetValue(rs, index);
    }

    /**
     * 获取结果
     *
     * @param rs    结果集
     * @param index 索引
     * @return 结果
     * @throws SQLException sql异常
     */
    private static Object getResultSetValue(ResultSet rs, int index) throws SQLException {
        Object _obj = rs.getObject(index);
        if (null == _obj)
            return null;
        final String _className = _obj.getClass().getName();
        if (_obj instanceof Blob) {
            Blob _blob = (Blob) _obj;
            _obj = _blob.getBytes(1, (int) _blob.length());
        } else if (_obj instanceof Clob) {
            Clob _clob = (Clob) _obj;
            _obj = _clob.getSubString(1, (int) _clob.length());
        } else if ("oracle.sql.TIMESTAMP".equals(_className) || "oracle.sql.TIMESTAMPTZ".equals(_className)) {
            _obj = rs.getTimestamp(index);
        } else if (null != _className && _className.startsWith("oracle.sql.DATE")) {
            String metaDataClassName = rs.getMetaData().getColumnClassName(index);
            if ("java.sql.Timestamp".equals(metaDataClassName) || "oracle.sql.TIMESTAMP".equals(metaDataClassName)) {
                _obj = rs.getTimestamp(index);
            } else {
                _obj = rs.getDate(index);
            }
        } else if (_obj instanceof java.sql.Date) {
            if ("java.sql.Timestamp".equals(rs.getMetaData().getColumnClassName(index))) {
                _obj = rs.getTimestamp(index);
            }
        }
        return _obj;
    }

    /**
     * 获取sql处理器参数类型
     *
     * @param statement sql处理器
     * @return 参数类型集合
     * @throws SQLException sql异常
     */
    private static int[] getStatementParameterTypes(PreparedStatement statement) throws SQLException {
        ParameterMetaData _parameterMetaData = statement.getParameterMetaData();
        final int _COUNT = _parameterMetaData.getParameterCount();
        int[] _result = new int[_COUNT];
        // 获取参数元数据时是否遇到错误
        boolean _hasException = false;
        for (int _i = 0; _i < _COUNT; _i++) {
            if (_hasException) {
                // 大部分数据库驱动中的null值都适用于VARCHAR类型，类型获取出错就默认使用VARCHAR。
                _result[_i] = Types.VARCHAR;
            } else {
                try {
                    _result[_i] = _parameterMetaData.getParameterType(_i + 1);
                } catch (Exception e) {
                    _hasException = true;
                }
            }
        }
        return _result;
    }

    /**
     * 设置sql处理器参数
     *
     * @param statement sql处理器
     * @param params    参数列表
     * @throws SQLException sql异常
     */
    private static void setStatementParameters(PreparedStatement statement, Object[] params) throws SQLException {
        if (null == params || 0 == params.length)
            return;

        // 比较提供的参数数量与预编译sql中的参数数量是否一致
        final int _paramsLength = params.length;
        ParameterMetaData _parameterMetaData = statement.getParameterMetaData();
        if (_paramsLength != _parameterMetaData.getParameterCount()) {
            // 如果给出的参数数量与sql中的参数数量不一致，则报错。
            throw new RuntimeException("params length: " + _paramsLength + " != statement parameter count: " + _parameterMetaData.getParameterCount());
        }

        // 获取参数元数据时是否遇到错误
        boolean _hasException = false;
        for (int _i = 0; _i < _paramsLength; _i++) {
            final Object _param = params[_i];
            if (null != _param) {
                statement.setObject(_i + 1, _param);
            } else {
                // 大部分数据库驱动中的null值都适用于VARCHAR类型，部分如oracle之类的数据库对null值类型有严格要求的需要获取类型。
                int _nullType = Types.VARCHAR;
                if (!_hasException) {
                    try {
                        _nullType = _parameterMetaData.getParameterType(_i + 1);
                    } catch (Exception e) {
                        _hasException = true;
                    }
                }
                statement.setNull(_i + 1, _nullType);
            }
        }
    }

//    // 线程本地数据库连接对象，用于数据库事务使用。
//    protected final ThreadLocal<Connection> LOCAL_CONNECTION = new ThreadLocal<Connection>();
//
//    /**
//     * 获取数据库连接对象
//     *
//     * @return 数据库连接对象
//     * @throws SQLException 数据库异常
//     */
//    private Connection getConnection() throws SQLException {
//        Connection _conn = LOCAL_CONNECTION.get();
//        if (null == _conn) {
//            _conn = DsManager.impl().getDefaultDataSource().getConnection();
//        }
//        return _conn;
//    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleSqlHelper() {
        if (null != SimpleSqlHelperHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleSqlHelperHolder {
        private static final SimpleSqlHelper _INSTANCE = new SimpleSqlHelper();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleSqlHelperHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleSqlHelper me() {
        return SimpleSqlHelperHolder._INSTANCE;
    }

}
