package com.wee0.box.data;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/26 7:02
 * @Description 数据创建新增操作对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataStoreOperation {

    /**
     * 操作指定名称的模型
     *
     * @param modelName 模型名称
     * @return 链式调用对象
     */
    IDataStoreOperation withModel(String modelName);

    /**
     * 设置指定名称的字段值
     *
     * @param fieldName 字段名称
     * @param value     字段值
     * @return 链式调用对象
     */
    IDataStoreOperation field(String fieldName, Object value);

    Object apply();

    /**
     * 清空当前模型设置数据
     */
    IDataStoreOperation clear();

}
