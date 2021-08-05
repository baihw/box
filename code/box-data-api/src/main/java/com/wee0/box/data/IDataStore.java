package com.wee0.box.data;

import com.wee0.box.beans.IDestroyable;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:30
 * @Description 数据存储对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataStore extends IDestroyable {

    /**
     * 配置键
     */
    enum Key {
        /**
         * 方言
         */
        DIALECT,

        /**
         * 数据源
         */
        DATASOURCE,

        /**
         * 自动生成数据定义语句
         */
        GENERATE_DDL,
    }

    /**
     * 增加一个指定名称的模型对象
     *
     * @param modelName 模型对象名称
     * @return 模型对象
     */
    IDataModel addModel(String modelName);

    /**
     * 获取指定名称的模型对象
     *
     * @param modelName 模型对象名称
     * @return 模型对象
     */
    IDataModel getModel(String modelName);

    /**
     * 创建一个数据创建新增操作对象
     *
     * @return 数据创建新增操作对象
     */
    IDataStoreCreator newCreator();

    /**
     * 创建一个数据更新操作对象
     *
     * @return 数据保存操作对象
     */
    IDataStoreUpdater newUpdater();

    /**
     * 创建一个数据删除操作对象
     *
     * @return 数据删除操作对象
     */
    IDataStoreDeleter newDeleter();

    /**
     * 创建一个数据选择操作对象
     *
     * @return 数据选择操作对象
     */
    IDataStoreSelector newSelector();

    /**
     * 应用数据操作请求，得到执行结果。
     *
     * @param request 数据请求对象
     * @return 数据响应对象
     */
    IDataResponse apply(IDataRequest request);

}
