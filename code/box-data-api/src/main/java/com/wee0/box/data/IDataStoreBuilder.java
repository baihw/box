package com.wee0.box.data;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/12 7:12
 * @Description 数据存储对象构建器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataStoreBuilder {

    /**
     * 配置信息
     *
     * @param name  配置名称
     * @param value 配置值
     * @return 链式调用对象
     */
    IDataStoreBuilder config(String name, Object value);

    /**
     * 配置信息
     *
     * @param key   配置项
     * @param value 配置值
     * @return 链式调用对象
     */
    IDataStoreBuilder config(IDataStore.Key key, Object value);

    /**
     * 使用指定名称的模型
     *
     * @param modelName 模型名称
     * @return 链式调用对象
     */
    IDataStoreBuilder withModel(String modelName);

    /**
     * 为当前使用的模型配置字段信息
     *
     * @param comment      字段注释信息
     * @param fieldName    字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param isRequired   是否必须有值
     * @param generateRule 使用的字段值生成规则
     * @param dataRules    字段值校验规则
     * @return 链式调用对象
     */
    IDataStoreBuilder field(String comment, String fieldName, IDataField.Type type, double length, boolean isRequired,
                            String generateRule, String[] dataRules);

    /**
     * 为当前使用的模型配置字段信息
     *
     * @param fieldName    字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param isRequired   是否必须有值
     * @param generateRule 使用的字段值生成规则
     * @param dataRules    字段值校验规则
     * @return 链式调用对象
     */
    default IDataStoreBuilder field(String fieldName, IDataField.Type type, double length,
                                    boolean isRequired, String generateRule, String[] dataRules) {
        return this.field(null, fieldName, type, length, isRequired, generateRule, dataRules);
    }

    /**
     * 为当前使用的模型配置字段信息
     *
     * @param fieldName    字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param isRequired   是否必须有值
     * @param generateRule 使用的字段值生成规则
     * @return 链式调用对象
     */
    default IDataStoreBuilder field(String fieldName, IDataField.Type type, double length, boolean isRequired, String generateRule) {
        return this.field(null, fieldName, type, length, isRequired, generateRule, null);
    }

    /**
     * 为当前使用的模型配置字段信息
     *
     * @param fieldName  字段名称
     * @param type       字段类型
     * @param length     字段长度
     * @param isRequired 是否必须有值
     * @return 链式调用对象
     */
    default IDataStoreBuilder field(String fieldName, IDataField.Type type, double length, boolean isRequired) {
        return this.field(null, fieldName, type, length, isRequired, null, null);
    }

    /**
     * 为当前使用的模型配置字段信息
     *
     * @param comment      字段注释信息
     * @param fieldName    字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param generateRule 使用的字段值生成规则
     * @return 链式调用对象
     */
    default IDataStoreBuilder field(String comment, String fieldName, IDataField.Type type, double length, String generateRule) {
        return this.field(comment, fieldName, type, length, true, generateRule, null);
    }

    /**
     * 为当前使用的模型配置字段信息
     *
     * @param fieldName    字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param generateRule 使用的字段值生成规则
     * @return 链式调用对象
     */
    default IDataStoreBuilder field(String fieldName, IDataField.Type type, double length, String generateRule) {
        return this.field(null, fieldName, type, length, true, generateRule, null);
    }

    /**
     * 设置主键字段
     *
     * @param fieldNames 主键字段名称列表
     * @return 链式调用对象
     */
    IDataStoreBuilder primaryKeys(String... fieldNames);

    /**
     * 为当前使用的模型配置索引
     *
     * @param indexName  索引名称
     * @param unique     是否唯一索引
     * @param fieldNames 索引字段名
     * @return 链式调用对象
     */
    IDataStoreBuilder index(String indexName, boolean unique, String... fieldNames);

    /**
     * 为当前使用的模型配置索引，索引名称自动生成
     *
     * @param unique     是否唯一索引
     * @param fieldNames 索引字段名
     * @return 链式调用对象
     */
    default IDataStoreBuilder index(boolean unique, String... fieldNames) {
        return this.index(null, unique, fieldNames);
    }

    /**
     * 根据收集到的信息构建数据存储对象
     *
     * @return 数据存储对象
     */
    IDataStore build();

}
