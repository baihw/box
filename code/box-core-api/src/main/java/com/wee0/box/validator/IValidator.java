package com.wee0.box.validator;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 9:52
 * @Description 数据规则校验器
 * <pre>
 * 规则：
 * name + “|” + args
 * </pre>
 **/
public interface IValidator {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.validator.impl.SimpleValidator";

    /**
     * 规则名称分隔符
     */
    char DEF_NAME_SEPARATOR = '|';

    /**
     * 参数分割正则表达式
     */
    String DEF_ARGS_SPLIT_REGEX = ",";

    /**
     * 校验给定值是否满足所有指定的校验规则，规则之间默认是并且关系。
     *
     * @param value    校验值
     * @param patterns 规则集合
     */
    void validate(Object value, String... patterns);

    /**
     * 增加一个数据校验处理器实现对象
     *
     * @param validateHandler 数据校验处理器实现对象
     */
    void addValidateHandler(IValidateHandler validateHandler);

}
