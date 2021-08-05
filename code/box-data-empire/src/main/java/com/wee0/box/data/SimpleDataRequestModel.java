package com.wee0.box.data;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:29
 * @Description 一个简单的数据请求对象模型信息实现
 * <pre>
 * 补充说明
 * </pre>
 **/
@EqualsAndHashCode
@ToString
public class SimpleDataRequestModel implements IDataRequestModel {

    // 模型处理动作
    private Action _action;
    // 模型名称
    private String _name;
    // 模型来源
    private String _from;
    // 数据请求条件信息
    private IDataRequestWhere _where;
    // 每页数据量
    private int _pageSize;
    // 当前页码
    private int _currPage;
    // 显示执行计划
    private boolean _explain;

    private Set<IDataRequestField> _fields;

    SimpleDataRequestModel(String name) {
        this._name = name;
    }

    @Override
    public Action getAction() {
        return this._action;
    }

    @Override
    public String getName() {
        return this._name;
    }

    @Override
    public String getFrom() {
        return this._from;
    }

    @Override
    public IDataRequestWhere getWhere() {
        return this._where;
    }

    @Override
    public int getPageSize() {
        return this._pageSize;
    }

    @Override
    public int getCurrPage() {
        return this._currPage;
    }

    @Override
    public boolean isExplain() {
        return this._explain;
    }

    @Override
    public IDataRequestModel addField(String... fieldNames) {
        if (null == fieldNames || 0 == fieldNames.length)
            return this;
        for (String _fieldName : fieldNames) {
            this.addField(new SimpleDataRequestField(_fieldName));
        }
        return this;
    }

    @Override
    public IDataRequestModel addField(IDataRequestField... fields) {
        if (null == this._fields)
            this._fields = new HashSet<>(16, 1.0f);
        if (null == fields || 0 == fields.length)
            return this;
        for (IDataRequestField _field : fields) {
            this._fields.add(_field);
        }
        return this;
    }

    @Override
    public Set<IDataRequestField> getFields() {
        return null == this._fields ? Collections.emptySet() : this._fields;
    }
}
