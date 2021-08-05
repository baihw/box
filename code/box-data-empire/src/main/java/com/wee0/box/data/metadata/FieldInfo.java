package com.wee0.box.data.metadata;

import com.wee0.box.data.IDataField;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:02
 * @Description 字段信息
 * <pre>
 * 补充说明
 * </pre>
 **/
@ToString
@EqualsAndHashCode
public class FieldInfo {

    // 字段名称
    @Getter
    private String fieldName;

    // 关联的数据存储中的字段名称
    @Getter
    @Setter
    private String dataName;

    // 字段类型
    @Getter
    @Setter
    private IDataField.Type fieldType;

//    // 字段提示，如：char。
//    @Setter
//    @Getter
//    private String hint;

//    // 关联的数据存储中的字段数据类型
//    private DataType dataType;

    // 数据长度
    @Setter
    @Getter
    private double length;

//    // 默认值
//    @Getter
//    @Setter
//    private String defValue;

    // 值生成器名称，如果配置了值生成器，则默认值设置无效。
    @Getter
    @Setter
    private String generateRule;

    // 是否允许空值 nullAllowed
    // 是否必须有值
    @Getter
    @Setter
    private boolean isRequired;

//    // 是否为主键，如果一个模型中只有一个唯一
//    @Getter
//    @Setter
//    private boolean isPrimary;

    // 字段注释
    @Getter
    @Setter
    private String comment;

    // 数据校验规则
    @Getter
    @Setter
    private String[] dataRules;

    public FieldInfo(String fieldName, IDataField.Type fieldType, double length) {
        this.fieldName = fieldName;
        this.dataName = fieldName;
        this.fieldType = fieldType;
        this.length = length;

        this.isRequired = false;
//        this.isPrimary = false;
    }

    public FieldInfo(String fieldName, IDataField.Type fieldType) {
        this(fieldName, fieldType, MetadataUtil.gussFieldLength(fieldType));
    }


}
