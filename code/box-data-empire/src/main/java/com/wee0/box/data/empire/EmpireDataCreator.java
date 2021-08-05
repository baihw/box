package com.wee0.box.data.empire;

import com.wee0.box.data.IDataField;
import com.wee0.box.data.IDataModel;
import com.wee0.box.data.IDataStoreCreator;
import com.wee0.box.data.util.DataUtils;
import com.wee0.box.generator.Generator;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.CheckUtils;
import org.apache.empire.db.DBRecord;
import org.apache.empire.db.DBTable;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/26 7:12
 * @Description 基于Empire的数据创建新增操作对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class EmpireDataCreator extends EmpireDataOperation implements IDataStoreCreator {

    // 日志对象
    private static final ILogger log = LoggerFactory.getLogger(EmpireDataCreator.class);

    EmpireDataCreator(EmpireDataStore store) {
        super(store);
    }

    @Override
    public IDataStoreCreator withModel(String modelName) {
        super.useModel(modelName);
        return this;
    }

    @Override
    public IDataStoreCreator field(String fieldName, Object value) {
        super.setField(fieldName, value);
        return this;
    }

    @Override
    public IDataStoreCreator clear() {
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

        // 处理所有需要生成值的字段
        _model.getFields().forEach(_field -> {
            final String _fieldName = _field.getName();
            Object _val = this._data.get(_fieldName);
            if (null != _val) {
                _cols.put(_fieldName, _val);
                return;
            }

            String _generateRule = _field.getGenerateRule();
            if (null != _generateRule) {
                _val = Generator.generate(_generateRule);
                _cols.put(_fieldName, _val);
                return;
            }

//            if (null != _field.getDefValue()) {
//                _cols.put(_fieldName, _field.getDefValue());
//                return;
//            }

            // 如果没有指定值，并且也没有指定值生成规则，则判断是否为必须有值的字段。
            CheckUtils.checkState(_field.isRequired(), "model '%s' field '%s' is required!",
                    _modelName, _fieldName);
        });

        // 对所有字段值进行数据合法性校验
        _cols.forEach((_key, _val) -> {
            IDataField _field = _model.getField(_key);
            // 基础校验
            DataUtils.check(_field, _val);
        });

        DBTable _table = _STORE.getDB().getTable(this._modelName);
        DBRecord _record = new DBRecord();
        _record.create(_table);
        _cols.forEach((_key, _val) -> {
            _record.setValue(_table.getColumn(_key), _val);
        });
        try {
            _record.update(_STORE.getDataSource().getConnection());
            return 1;
        } catch (SQLException e) {
            e.printStackTrace();
            return 0;
        }
    }

}
