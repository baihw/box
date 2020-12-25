package com.wee0.box.generator;

import com.wee0.box.BoxConfig;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/9/5 7:22
 * @Description 生成器快捷入口
 * <pre>
 * 补充说明
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

}
