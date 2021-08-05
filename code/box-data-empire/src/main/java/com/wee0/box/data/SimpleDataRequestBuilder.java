package com.wee0.box.data;


import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:26
 * @Description 一个简单的数据请求对象构建器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleDataRequestBuilder implements IDataRequestBuilder {

    private Map<String, IDataRequestModel> _models = new LinkedHashMap<>(6, 0.5f);

    private IDataRequestModel _currModel;

    /**
     * 选择要操作的模型名称
     *
     * @param modelName 模型名称
     * @return 链式调用对象
     */
    @Override
    public IDataRequestBuilder use(String modelName) {
        IDataRequestModel _cm = this._models.get(modelName);
        if (null == _cm) {
            _cm = new SimpleDataRequestModel(modelName);
            this._currModel = _cm;
            this._models.put(modelName, _cm);
        }
//        this._currModel = _models.putIfAbsent(modelName, new SimpleDataRequestModel(modelName));
        return this;
    }

    /**
     * 选择要操作的模型字段名称列表
     *
     * @param fieldNames 字段名称列表
     * @return 链式调用对象
     */
    @Override
    public IDataRequestBuilder field(String... fieldNames) {
        getCurrModel().addField(fieldNames);
        return this;
    }

    @Override
    public IDataRequestBuilder field(IDataRequestField... fields) {
        getCurrModel().addField(fields);
        return this;
    }

    @Override
    public IDataRequest build() {
        SimpleDataRequest _result = new SimpleDataRequest(this._models.values());
        return _result;
    }

    /**
     * @return 当前操作的模型对象。
     */
    private IDataRequestModel getCurrModel() {
        if (null == this._currModel)
            throw new IllegalStateException("please use model before...");
        return this._currModel;
    }

}
