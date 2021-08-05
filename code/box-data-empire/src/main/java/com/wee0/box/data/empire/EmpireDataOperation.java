package com.wee0.box.data.empire;

import com.wee0.box.util.shortcut.CheckUtils;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/26 7:12
 * @Description 基于Empire的数据操作基础对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public class EmpireDataOperation {

    //    protected final DBDatabase _DB;
//    protected final DBDatabaseDriver _DRIVER;
//    protected DataSource _DS;
    protected final EmpireDataStore _STORE;

    // 模型名称
    protected String _modelName;
    // 模型配置数据
    protected Map<String, Object> _data = new LinkedHashMap<>(16, 1.0f);

    EmpireDataOperation(EmpireDataStore store) {
        this._STORE = store;
    }

    protected void useModel(String modelName) {
        this._modelName = CheckUtils.checkNotTrimEmpty(modelName, "modelName cannot be empty!");
//        Map<String, Object> _items = this._data.get(modelName);
//        if (null == _items) {
//            _items = new LinkedHashMap<>(16, 1.0f);
//            this._data.put(modelName, _items);
//        }
//        this._currItems = _items;
    }

    protected void setField(String fieldName, Object value) {
//        CheckUtils.checkNotNull(this._currItems, "please specify the model name first!");
        fieldName = CheckUtils.checkNotTrimEmpty(fieldName, "fieldName cannot be empty!");
        this._data.put(fieldName, value);
    }

    protected void clearData() {
        this._data.clear();
    }

//    public static void main(String[] args) {
//        // yyyy-mm-dd hh:mm:ss[.fffffffff]
//        Timestamp _timestamp = Timestamp.valueOf("2021-07-26 07:37:52");
//        System.out.println("_timestamp: " + _timestamp);
//        _timestamp = Timestamp.valueOf("2021-07-26 07:37:52.1234567890");
//        System.out.println("_timestamp: " + _timestamp);
//    }


}
