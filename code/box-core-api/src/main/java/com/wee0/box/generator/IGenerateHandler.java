package com.wee0.box.generator;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 6:59
 * @Description 数据生成处理器
 * <pre>
 *
 * </pre>
 **/
public interface IGenerateHandler {

    /**
     * @return 数据生成处理器名称
     */
    String getName();

    /**
     * 生成指定规则的数据对象
     *
     * @param args 参数集合
     * @return 数据对象
     */
    Object generate(Object... args);

//    /**
//     * 通过指定规则生成指定类型的数据
//     *
//     * @param cla  数据类
//     * @param args 参数集合
//     * @param <T>  数据类型
//     * @return 指定类型的数据
//     */
//    <T> T generate(Class<T> cla, Object... args);
}
