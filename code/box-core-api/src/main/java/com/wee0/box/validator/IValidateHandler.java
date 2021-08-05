package com.wee0.box.validator;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 9:50
 * @Description 数据校验处理器
 * <pre>
 *
 * </pre>
 **/
public interface IValidateHandler {

    /**
     * @return 数据校验处理器名称
     */
    String getName();

    /**
     * 校验给定值是否满足指定模式
     *
     * @param value   校验值
     * @param options 校验选项参数
     */
    void validate(Object value, String... options);

}
