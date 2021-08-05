package com.wee0.box.data;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/26 7:02
 * @Description 数据更新操作对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataStoreUpdater {

    /**
     * 操作指定名称的模型
     *
     * @param modelName 模型名称
     * @return 链式调用对象
     */
    IDataStoreUpdater withModel(String modelName);

    /**
     * 设置指定名称的字段值
     *
     * @param fieldName 字段名称
     * @param value     字段值
     * @return 链式调用对象
     */
    IDataStoreUpdater field(String fieldName, Object value);

//    /**
//     * 批量设置字段名称
//     *
//     * @param fieldNames 字段名称集合
//     * @return 链式调用对象
//     */
//    IDataSaver fields(String... fieldNames);
//
//    /**
//     * 批量设置字段值
//     *
//     * @param values 字段值集合
//     * @return 链式调用对象
//     */
//    IDataSaver values(Object... values);

    /**
     * 清空当前模型设置数据
     */
    IDataStoreUpdater clear();

    /**
     * 执行保存操作，返回受影响的记录数。
     *
     * @return 受影响的记录数
     */
    int save();

}
