package com.wee0.box.data.empire;

import com.wee0.box.data.IDataField;
import com.wee0.box.data.IDataModel;
import com.wee0.box.data.metadata.MetadataUtil;
import com.wee0.box.util.shortcut.CheckUtils;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.empire.commons.StringUtils;
import org.apache.empire.db.DBColumn;
import org.apache.empire.db.DBDatabase;
import org.apache.empire.db.DBTable;

import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/12 7:03
 * @Description 一个基于Empire的数据模型对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
@EqualsAndHashCode
@ToString
public class EmpireDataModel implements IDataModel {

    private DBTable _table;
    //    private Set<IDataField> _fields;
    // 字段集合
    private Map<String, EmpireDataField> _fields;
    // 主键集合
    private Collection<String> _primaryKeys = null;
    // 不允许为空的字段名称集合
    private Set<String> _requiredKeys = new HashSet<>(16, 1.0f);

    EmpireDataModel(DBDatabase db, String name) {
        if (null == db || null == name)
            throw new IllegalArgumentException("db or name cannot be null!");
        this._table = new DBTable(name, db);
        this._fields = new HashMap<>(16, 1.0f);
    }

    @Override
    public String getName() {
        return this._table.getName();
    }

    @Override
    public IDataField addField(String comment, String name, IDataField.Type type, double length, boolean isRequired,
                               String generateRule, String[] dataRules) {
        EmpireDataField _field = this._fields.get(name);
        if (null == _field) {
            generateRule = CheckUtils.checkTrimEmpty(generateRule, null);
            _field = new EmpireDataField(this._table, name, type, length, isRequired, generateRule, comment, dataRules);
            this._fields.put(name, _field);
        }
        if (_field.isRequired())
            this._requiredKeys.add(name);
        return _field;
    }

//    @Override
//    public IDataModel addField(IDataField field) {
//        if (null != field)
//            this._fields.put(field.getName(), field);
//        return this;
//    }

    @Override
    public Collection<IDataField> getFields() {
        return Collections.unmodifiableCollection(this._fields.values());
    }

    @Override
    public IDataField getField(String name) {
        return this._fields.get(name);
    }

    @Override
    public IDataModel addIndex(String indexName, boolean unique, String... fieldNames) {
        if (null == fieldNames || 0 == fieldNames.length)
            throw new IllegalArgumentException("fieldNames cannot be empty!");

        if (StringUtils.isEmpty(indexName)) // throw new IllegalArgumentException("indexName cannot be empty!");
            indexName = MetadataUtil.generateIndexName(unique, this.getName(), fieldNames);

        DBColumn[] _columns = getColumnsByName(fieldNames);
        this._table.addIndex(indexName, unique, _columns);
        return this;
    }

//    @Override
//    public IDataModel addIndex(boolean unique, String... fieldNames) {
//        String _indexName = MetadataUtil.generateIndexName(unique, this.getName(), fieldNames);
//        return addIndex(_indexName, unique, fieldNames);
//    }

    @Override
    public IDataModel setPrimaryKeys(String... fieldNames) {
        if (null == fieldNames || 0 == fieldNames.length)
            throw new IllegalArgumentException("fieldNames cannot be empty!");

        DBColumn[] _columns = getColumnsByName(fieldNames);
        this._table.setPrimaryKey(_columns);

        this._primaryKeys = Collections.unmodifiableCollection(Arrays.asList(fieldNames));
        return this;
    }

    @Override
    public Collection<String> getPrimaryKeys() {
        return this._primaryKeys;
    }

    @Override
    public Set<String> getRequiredKeys() {
        return null;
    }


    /************************************************************
     * 内部方法
     ************************************************************/

    /**
     * 获取指定名称的列对象集合
     *
     * @param fieldNames 名称列表
     * @return 列对象集合
     */
    DBColumn[] getColumnsByName(String... fieldNames) {
        if (null == fieldNames || 0 == fieldNames.length)
            throw new IllegalArgumentException("fieldNames cannot be empty!");

        final int _LEN = fieldNames.length;
        DBColumn[] _columns = new DBColumn[_LEN];
        for (int _i = 0; _i < _LEN; _i++) {
            String _fieldName = fieldNames[_i];
            _columns[_i] = this._fields.get(_fieldName).getDBTableColumn();
        }
        return _columns;
    }

}
