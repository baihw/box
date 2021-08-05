package com.wee0.box.data.empire;

import com.wee0.box.data.IDataField;
import com.wee0.box.data.IDataModel;
import com.wee0.box.data.IDataStoreUpdater;
import com.wee0.box.data.util.DataUtils;
import com.wee0.box.generator.Generator;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.CheckUtils;
import org.apache.empire.db.DBCommand;
import org.apache.empire.db.DBRecord;
import org.apache.empire.db.DBTable;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/26 7:12
 * @Description 基于Empire的数据创建更新操作对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class EmpireDataUpdater extends EmpireDataOperation implements IDataStoreUpdater {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(EmpireDataUpdater.class);

    // 模型名称
    private String _modelName;
    // 模型配置数据
    private Map<String, Object> _data = new LinkedHashMap<>(16, 1.0f);

    EmpireDataUpdater(EmpireDataStore store) {
        super(store);
    }

    @Override
    public IDataStoreUpdater withModel(String modelName) {
        super.useModel(modelName);
        return this;
    }

    @Override
    public IDataStoreUpdater field(String fieldName, Object value) {
        super.setField(fieldName, value);
        return this;
    }

    @Override
    public IDataStoreUpdater clear() {
        super.clearData();
        return this;
    }

    @Override
    public int save() {
        CheckUtils.checkNotNull(this._modelName, "please specify the model name first!");

        // 检查模型数据规则
        IDataModel _model = _STORE.getModel(this._modelName);
        CheckUtils.checkNotNull(_model, "modelName '%s' was not found!", this._modelName);
        log.debug("save model: {}", _model);

        Map<String, Object> _cols = new LinkedHashMap<>(this._data.size() + 6);

        // 对所有字段值进行数据合法性校验
        this._data.forEach((_key, _val) -> {
            IDataField _field = _model.getField(_key);
            // 基础校验
            DataUtils.check(_field, _val);
        });

        DBTable _table = _STORE.getDB().getTable(this._modelName);
        try {
            DBCommand _cmd = _STORE.getDB().createCommand();
            _cmd.getInsert();
            _cmd.getUpdate();
            _cmd.getDelete(_table);
            _cmd.select();

            _cmd.set();


            DBRecord _record = new DBRecord();
            _record.read(_table, "", _STORE.getDataSource().getConnection());
            _cols.forEach((_key, _val) -> {
                _record.setValue(_table.getColumn(_key), _val);
            });

            _record.update(_STORE.getDataSource().getConnection());
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
