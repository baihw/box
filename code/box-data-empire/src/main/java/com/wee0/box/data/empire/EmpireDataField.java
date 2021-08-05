package com.wee0.box.data.empire;

import com.wee0.box.data.IDataField;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.apache.empire.data.DataType;
import org.apache.empire.db.DBTable;
import org.apache.empire.db.DBTableColumn;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/12 7:03
 * @Description 一个基于Empire的数据模型字段实现
 * <pre>
 * 补充说明
 * </pre>
 **/
@EqualsAndHashCode
@ToString
public class EmpireDataField implements IDataField {

    private final DBTableColumn _COLUMN;
    private final DBTable _TABLE;

    // 字段名称
    private String _name;
//    // 关联的数据存储中的字段名称
//    private String _dataName;

    // 字段类型
    private Type _type;
    // 数据长度
    private double _length;
    //    // 是否为主键，如果一个模型中只有一个唯一
//    private boolean _isPrimary;
    // 是否必须有值 // 是否允许空值 nullAllowed
    private boolean _isRequired;
    //    // 默认值
//    private Object _defValue;
    // 字段值生成器规则，如果配置了值生成器 ，则默认值设置无效。
    private String _generateRule;

    // 字段注释
    private String _comment;
    // 数据校验规则
    private String[] _dataRules;

    EmpireDataField(DBTable table, String name, Type type, double length, boolean isRequired,
                    String generateRule, String comment, String[] dataRules) {
        if (null == table) throw new IllegalArgumentException("table cannot be null!");
        this._TABLE = table;
        this._name = name;
        this._type = type;
        this._length = length;
        this._isRequired = isRequired;
//        this._defValue = defValue;
        this._generateRule = generateRule;
        this._comment = comment;
        this._dataRules = dataRules;
        this._COLUMN = buildDBTableColumn();
    }


    @Override
    public String getName() {
        return this._COLUMN.getName();
    }

    @Override
    public Type getType() {
        return this._type;
    }

    @Override
    public double getLength() {
        return this._length;
    }

    @Override
    public boolean isRequired() {
        return this._isRequired;
    }

    @Override
    public String getGenerateRule() {
        return this._generateRule;
    }

    @Override
    public String getComment() {
        return this._comment;
    }

    @Override
    public String[] getDataRules() {
        return this._dataRules;
    }

    DBTableColumn getDBTableColumn() {
        return this._COLUMN;
    }

    /************************************************************
     * 内部方法
     ************************************************************/
    /**
     * @return 数据库列对象
     */
    DBTableColumn buildDBTableColumn() {
        DataType _dataType;
        switch (this._type) {
            case Str:
                _dataType = DataType.VARCHAR;
                break;
            case Char:
                _dataType = DataType.CHAR;
                break;
            case Bool:
                _dataType = DataType.BOOL;
                break;
            case Int:
                _dataType = DataType.INTEGER;
                break;
            case Decimal:
                _dataType = DataType.DECIMAL;
                break;
            case Date:
                _dataType = DataType.DATETIME;
                // _dataType = DataType.TIMESTAMP;
                break;
            default:
                throw new IllegalStateException("unKnow type: " + this._type);
        }
        return this._TABLE.addColumn(this._name, _dataType, this._length, this._isRequired);
    }


}
