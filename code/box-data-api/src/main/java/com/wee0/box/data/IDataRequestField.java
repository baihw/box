package com.wee0.box.data;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:29
 * @Description 数据请求对象模型字段信息
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDataRequestField {

    /**
     * 字段排序规则
     */
    enum Order {

        /**
         * 升序
         */
        ASC,

        /**
         * 降序
         */
        DESC,
    }

    /**
     * 设置字段名称
     *
     * @param name 字段名称
     * @return 链式调用对象
     */
    IDataRequestField setName(String name);

    /**
     * @return 字段名称
     */
    String getName();

    /**
     * 设置字段排序规则
     *
     * @param order 排序规则
     * @return 链式调用对象
     */
    IDataRequestField setOrder(Order order);

    /**
     * @return 字段排序规则
     */
    Order getOrder();

    /**
     * @return 使用的值转换器名称
     */
    String getTransformer();

}
