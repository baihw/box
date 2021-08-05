package com.wee0.box.data;

import lombok.EqualsAndHashCode;
import lombok.ToString;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:36
 * @Description 一个简单的数据请求对象模型字段信息实现
 * <pre>
 * 补充说明
 * </pre>
 **/
@EqualsAndHashCode
@ToString
public class SimpleDataRequestField implements IDataRequestField {


    // 字段名称
    private String _name;
    // 排序方法
    private Order _order;
    // 值转换器
    private String _transformer;

    SimpleDataRequestField(String name){
        this._name = name;
    }

    @Override
    public IDataRequestField setName(String name) {
        this._name = name;
        return this;
    }

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public IDataRequestField setOrder(Order order) {
        this._order = order;
        return this;
    }

    @Override
    public Order getOrder() {
        return this._order;
    }

    @Override
    public String getTransformer() {
        return this._transformer;
    }
}
