package com.wee0.box.data;

import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:25
 * @Description 一个简单的数据请求对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
@EqualsAndHashCode
@ToString
public class SimpleDataRequest implements IDataRequest {

    // 请求的模型信息集合
    private Set<IDataRequestModel> _models;

    SimpleDataRequest(Collection<IDataRequestModel> models) {
        this._models = new HashSet<>(models);
    }

    SimpleDataRequest() {

    }

    @Override
    public Set<IDataRequestModel> getModels() {
        return this._models;
    }

    public IDataRequest addModel(IDataRequestModel model) {
        if (null == this._models)
            this._models = new HashSet<>(6, 0.5f);
        this._models.add(model);
        return this;
    }

    //    class TableInfo {
//        private Map<String, TableHideInfo> _hides = null;
//        private Map<String, ColumnInfo> _columns = null;
//    }
//
//    class HideInfo {
//        // 描述信息
//        private String _desc;
//        private String _type = "query";
//        private boolean _explain;
//        private Set<String> _fields;
//        private String _from;
//        private Map<String, String> _where;
//        private Set<String> _orders;
//        private String _transform;
//        private int _rows;
//        private int _page;
//    }
//
//    class TableHideInfo extends HideInfo {
//
//    }
//
//    class ColumnHideInfo extends HideInfo {
//
//    }
//
//    class ColumnInfo {
//
//        private Map<String, ColumnHideInfo> _hides = null;
//
//    }
}
