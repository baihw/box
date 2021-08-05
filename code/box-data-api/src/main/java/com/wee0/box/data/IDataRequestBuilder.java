package com.wee0.box.data;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:26
 * @Description 数据请求对象构建器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataRequestBuilder {

    /**
     * 选择要操作的模型名称
     *
     * @param modelName 模型名称
     * @return 链式调用对象
     */
    IDataRequestBuilder use(String modelName);

    /**
     * 选择要操作的模型字段名称列表
     *
     * @param fieldNames 字段名称列表
     * @return 链式调用对象
     */
    IDataRequestBuilder field(String... fieldNames);

    /**
     * 选择要操作的模型字段信息
     *
     * @param fields 模型字段信息列表
     * @return 链式调用对象
     */
    IDataRequestBuilder field(IDataRequestField... fields);

    /**
     * 根据当前配置信息构建数据请求对象
     *
     * @return 数据请求对象
     */
    IDataRequest build();
}
