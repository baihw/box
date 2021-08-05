package com.wee0.box.data.util;

import com.wee0.box.data.IDataField;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.validator.Validator;

import java.math.BigDecimal;
import java.sql.Timestamp;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/26 7:25
 * @Description 数据处理工具类
 * <pre>
 * 补充说明
 * </pre>
 **/
public class DataUtils {

    /**
     * 字段值合法性校验
     *
     * @param field 字段信息
     * @param value 字段值
     */
    public static Object check(IDataField field, Object value) {
        if (null == field)
            throw new IllegalArgumentException("field cannot be null!");
        final String _fieldName = field.getName();
        if (null == value) {
            // 值为空时，如果字段时必须字段，则报错。
            CheckUtils.checkState(field.isRequired(), "field '%s' is required!", _fieldName);
            return null;
        }

        Object _result = null;
        String _valueString = String.valueOf(value);
        // 值不为空时，判断是否符合规则
        switch (field.getType()) {
            case Char:
            case Str:
                int _len = (int) field.getLength();
                CheckUtils.checkState(_valueString.length() > _len,
                        "field '%s' value '%s' length greater than %s!",
                        _fieldName, _valueString, _len);
                _result = _valueString;
                break;
            case Bool:
                boolean _expResult = ("True".equalsIgnoreCase(_valueString) || "False".equalsIgnoreCase(_valueString));
                CheckUtils.checkState(!_expResult, "field '%s' value '%s' is not a Bool!",
                        _fieldName, _valueString);
                _result = Boolean.parseBoolean(String.valueOf(value));
                break;
            case Int:
                try {
                    _result = Integer.parseInt(_valueString);
                } catch (NumberFormatException e) {
                    CheckUtils.checkState(true, "field '%s' value '%s' is not a Int!",
                            _fieldName, _valueString);
                }
                break;
            case Decimal:
                try {
                    _result = new BigDecimal(_valueString);
                } catch (NumberFormatException e) {
                    CheckUtils.checkState(true, "field '%s' value '%s' is not a Decimal!",
                            _fieldName, _valueString);
                }
                break;
            case Date:
                try {
                    _result = Timestamp.valueOf(_valueString);
                } catch (Exception e) {
                    CheckUtils.checkState(true, "field '%s' value '%s' is not a Date!",
                            _fieldName, _valueString);
                }
                break;
        }

        // 基本规则校验通过后，校验是否满足附加规则
        Validator.validate(_valueString, field.getDataRules());
//        String[] _dataRules = field.getDataRules();
//        if (null != _dataRules && 0 != _dataRules.length) {
//            // 执行规则校验
//            for (String _dataRule : _dataRules) {
//                ValidateUtils.impl().validatePattern(_valueString, _dataRule);
//            }
//        }

        // 返回最终类型值
        return _result;
    }

    public static void main(String[] args) {
//        boolean _val = Boolean.parseBoolean("12");
//        System.out.println("_val: " + _val);
        Timestamp _val = Timestamp.valueOf("2021-07-21 12:12:12a");
        System.out.println("_val: " + _val);
    }
}
