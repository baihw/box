package com.wee0.box.data;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/12 7:02
 * @Description 数据模型对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataModel {

    /**
     * @return 模型名称
     */
    String getName();

    /**
     * 根据指定信息增加一个数据模型字段
     *
     * @param comment      字段注释
     * @param name         字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param isRequired   是否必须有值
     * @param generateRule 使用的字段值生成规则
     * @param dataRules    字段数据检验规则
     * @return 数据模型字段对象
     */
    IDataField addField(String comment, String name, IDataField.Type type, double length, boolean isRequired,
                        String generateRule, String[] dataRules);

    /**
     * 根据指定信息增加一个数据模型字段
     *
     * @param name         字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param isRequired   是否必须有值
     * @param generateRule 使用的字段值生成规则
     * @param dataRules    字段数据检验规则
     * @return 数据模型字段对象
     */
    default IDataField addField(String name, IDataField.Type type, double length,
                                boolean isRequired, String generateRule, String[] dataRules) {
        return this.addField(null, name, type, length, isRequired, generateRule, dataRules);
    }

    /**
     * 根据指定信息增加一个数据模型字段
     *
     * @param type         字段类型
     * @param length       字段长度
     * @param isRequired   是否必须有值
     * @param generateRule 使用的字段值生成规则
     * @return 数据模型字段对象
     */
    default IDataField addField(String name, IDataField.Type type, double length, boolean isRequired, String generateRule) {
        return this.addField(null, name, type, length, isRequired, generateRule, null);
    }

    /**
     * 根据指定信息增加一个数据模型字段
     *
     * @param name       字段名称
     * @param type       字段类型
     * @param length     字段长度
     * @param isRequired 是否必须有值
     * @return 数据模型字段对象
     */
    default IDataField addField(String name, IDataField.Type type, double length, boolean isRequired) {
        return this.addField(null, name, type, length, isRequired, null, null);
    }

    /**
     * 根据指定信息增加一个数据模型字段
     *
     * @param comment      字段注释
     * @param name         字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param generateRule 使用的字段值生成规则
     * @return 数据模型字段对象
     */
    default IDataField addField(String comment, String name, IDataField.Type type, double length, String generateRule) {
        return this.addField(null, name, type, length, true, generateRule, null);
    }

    /**
     * 根据指定信息增加一个数据模型字段
     *
     * @param name         字段名称
     * @param type         字段类型
     * @param length       字段长度
     * @param generateRule 使用的字段值生成规则
     * @return 数据模型字段对象
     */
    default IDataField addField(String name, IDataField.Type type, double length, String generateRule) {
        return this.addField(null, name, type, length, true, generateRule, null);
    }

//    /**
//     * 增加一个数据模型字段
//     *
//     * @param field 数据模型字段
//     * @return 链式调用对象
//     */
//    IDataModel addField(IDataField field);

    /**
     * @return 字段集合
     */
    Collection<IDataField> getFields();

    /**
     * 获取指定名称的字段对象
     *
     * @param name 字段名称
     * @return 字段对象
     */
    IDataField getField(String name);

    /**
     * 增加一个数据模型索引
     *
     * @param indexName  索引名称
     * @param unique     是否唯一
     * @param fieldNames 索引字段名称列表
     * @return 链式调用对象
     */
    IDataModel addIndex(String indexName, boolean unique, String... fieldNames);

    /**
     * 增加一个数据模型索引
     *
     * @param unique     是否唯一
     * @param fieldNames 索引字段名称列表
     * @return 链式调用对象
     */
    default IDataModel addIndex(boolean unique, String... fieldNames) {
        return this.addIndex(null, unique, fieldNames);
    }

    /**
     * 设置主键字段
     *
     * @param fieldNames 主键字段名称列表
     * @return 链式调用对象
     */
    IDataModel setPrimaryKeys(String... fieldNames);

    /**
     * @return 主键字段名称集合
     */
    Collection<String> getPrimaryKeys();

    /**
     * @return 不允许空的字段名称集合
     */
    Set<String> getRequiredKeys();

}
