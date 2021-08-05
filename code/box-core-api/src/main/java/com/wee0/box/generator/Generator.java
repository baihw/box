package com.wee0.box.generator;

import com.wee0.box.BoxConfig;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 6:52
 * @Description 数据生成器
 * <pre>
 * rule规则：
 * name + “|” + args
 * </pre>
 **/
public final class Generator {

    // 实现类实例
    private static final IGenerator IMPL = BoxConfig.impl().getInterfaceImpl(IGenerator.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static IGenerator impl() {
        return IMPL;
    }

    /**
     * 生成指定规则的字符串
     *
     * @param rule 生成规则
     * @return 生成的字符串
     */
    public static Object generate(String rule) {
        return IMPL.generate(rule);
    }

//    /**
//     * 通过指定规则生成指定类型的数据
//     *
//     * @param cla  数据类
//     * @param rule 生成规则
//     * @param <T>  数据类型
//     * @return 指定类型的数据
//     */
//    public static <T> T generate(Class<T> cla, String rule) {
//        return IMPL.generate(cla, rule);
//    }

}
