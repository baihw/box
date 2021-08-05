package com.wee0.box.validator;

import com.wee0.box.BoxConfig;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 9:56
 * @Description 数据生成器
 * <pre>
 * 规则：
 * name + “|” + args
 * </pre>
 **/
public final class Validator {

    // 实现类实例
    private static final IValidator IMPL = BoxConfig.impl().getInterfaceImpl(IValidator.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static IValidator impl() {
        return IMPL;
    }

    /**
     * 校验给定值是否满足所有指定的校验规则，规则之间默认是并且关系。
     *
     * @param value    校验值
     * @param patterns 规则集合
     */
    public static void validate(Object value, String... patterns) {
        IMPL.validate(value, patterns);
    }

}
