package com.wee0.box.generator;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/7/10 6:52
 * @Description 数据生成器
 * <pre>
 * rule规则：
 * name + “|” + args
 * </pre>
 **/
public interface IGenerator {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.generator.impl.SimpleGenerator";

    /**
     * 规则名称分隔符
     */
    char DEF_NAME_SEPARATOR = '|';

    /**
     * 参数分割正则表达式
     */
    String DEF_ARGS_SPLIT_REGEX = ",";

    /**
     * 生成指定规则的数据对象
     *
     * @param rule 生成规则
     * @return 生成的数据对象
     */
    Object generate(String rule);

    /**
     * 增加一个数据生成处理器实现对象
     *
     * @param generateHandler 数据生成处理器实现对象
     */
    void addGenerateHandler(IGenerateHandler generateHandler);

}
