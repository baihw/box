package com.wee0.box.data.metadata;


import com.wee0.box.data.IDataField;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/12 7:25
 * @Description 元数据相关操作工具类
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MetadataUtil {


    /**
     * 根据已知信息生成一个索引名称
     *
     * @param unique     是否唯一索引
     * @param modelName  模型名称
     * @param fieldNames 字段名称
     * @return 索引名称
     */
    public static String generateIndexName(boolean unique, String modelName, String... fieldNames) {
        StringBuilder _sb = new StringBuilder();
        _sb.append(unique ? "UNIQUE_" : "INDEX_").append(modelName.toUpperCase());
        for (String _fieldName : fieldNames) {
            _sb.append('_').append(_fieldName.toUpperCase());
        }
        return _sb.toString();
    }

    /**
     * 根据字段数据类型猜测一个可能合适的字段长度值。
     *
     * @param fieldType 字段数据类型
     * @return 字段数据长度
     */
    public static int gussFieldLength(IDataField.Type fieldType) {
        int _length = 1;
        switch (fieldType) {
            case Int:
                _length = 4;
                break;
            case Str:
                _length = 32;
                break;
            case Decimal:
                _length = 13;
                break;
        }
        return _length;
    }

}
