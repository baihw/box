package com.wee0.box.data;

import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:29
 * @Description 数据请求对象模型信息
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataRequestModel {

    /**
     * 模型处理动作
     */
    enum Action {
        /**
         * 查询
         */
        Query,

        /**
         * 保存
         */
        Save,

        /**
         * 删除
         */
        Delete,

        Insert,
        Update,
    }

    /**
     * @return 模型处理动作
     */
    Action getAction();

    /**
     * @return 模型名称
     */
    String getName();

    /**
     * @return 模型来源
     */
    String getFrom();

    /**
     * @return 数据请求条件信息
     */
    IDataRequestWhere getWhere();

    /**
     * @return 每页数据量
     */
    int getPageSize();

    /**
     * @return 当前页码
     */
    int getCurrPage();

    /**
     * @return 是否需要提供执行计划
     */
    boolean isExplain();

//    /**
//     * @return 注释说明信息
//     */
//    String getNote();

    /**
     * 增加指定名称的字段信息
     *
     * @param fieldNames 字段名称列表
     * @return 链式调用对象
     */
    IDataRequestModel addField(String... fieldNames);

    /**
     * 增加满足指定字段描述信息要求的字段
     *
     * @param fields 字段描述信息列表
     * @return 链式调用对象
     */
    IDataRequestModel addField(IDataRequestField... fields);

    /**
     * @return 字段描述信息集合
     */
    Set<IDataRequestField> getFields();

}
