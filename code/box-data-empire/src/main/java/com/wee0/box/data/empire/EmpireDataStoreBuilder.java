package com.wee0.box.data.empire;

import com.wee0.box.data.IDataField;
import com.wee0.box.data.IDataModel;
import com.wee0.box.data.IDataStore;
import com.wee0.box.data.IDataStoreBuilder;
import com.wee0.box.data.metadata.FieldInfo;
import com.wee0.box.data.metadata.IndexInfo;
import com.wee0.box.data.metadata.ModelInfo;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.apache.empire.commons.StringUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/6/12 7:12
 * @Description 一个基于Empire的数据存储对象构建器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class EmpireDataStoreBuilder implements IDataStoreBuilder {

    private static final ILogger log = LoggerFactory.getLogger(EmpireDataStoreBuilder.class);

    // 选项信息集合
    private Map<String, Object> _config;

    private Map<String, ModelInfo> _models;
    private ModelInfo _currModel;

    EmpireDataStoreBuilder() {
        this._config = new HashMap<>(16, 1.0f);
        this._models = new HashMap<>(32, 1.0f);
    }


    @Override
    public IDataStoreBuilder config(String name, Object value) {
        this._config.put(name, value);
        return this;
    }

    @Override
    public IDataStoreBuilder config(IDataStore.Key key, Object value) {
        this._config.put(key.name(), value);
        return this;
    }

    @Override
    public IDataStoreBuilder withModel(String modelName) {
        if (StringUtils.isEmpty(modelName)) return this;
        this._currModel = this._models.get(modelName);
        if (null == this._currModel) {
            this._currModel = new ModelInfo(modelName);
            this._models.put(modelName, this._currModel);
        }
        return this;
    }

    @Override
    public IDataStoreBuilder field(String comment, String fieldName, IDataField.Type type, double length,
                                   boolean isRequired, String generateRule, String[] dataRules) {
        FieldInfo _field = new FieldInfo(fieldName, type, length);
        _field.setComment(comment);
        _field.setRequired(isRequired);
//        _field.setDefValue(defValue);
        _field.setGenerateRule(generateRule);
        _field.setDataRules(dataRules);
        getCurrModel().addField(_field);
        return this;
    }

    @Override
    public IDataStoreBuilder primaryKeys(String... fieldNames) {
        getCurrModel().setPrimaryKeys(fieldNames);
        return this;
    }

    @Override
    public IDataStoreBuilder index(String indexName, boolean unique, String... fieldNames) {
        getCurrModel().addIndex(new IndexInfo(indexName, fieldNames, unique));
        return this;
    }

//    @Override
//    public IDataStoreBuilder index(boolean unique, String... fieldNames) {
//        if (null == fieldNames || 0 == fieldNames.length)
//            throw new IllegalArgumentException("fieldNames cannot be empty!");
//
//        ModelInfo _model = getCurrModel();
//        String _indexName = genereateIndexName(unique, _model.getName(), fieldNames);
//
//        List<FieldInfo> _fields = new ArrayList<>(fieldNames.length);
//        for (String _fieldName : fieldNames) {
//            _model.getField(_fieldName);
//        }
//        return index(null, unique, fieldNames);
//    }
//
//    @Override
//    public IDataStoreBuilder index(String indexName, boolean unique, String... fieldNames) {
//        if (null == fieldNames || 0 == fieldNames.length)
//            throw new IllegalArgumentException("fieldNames cannot be empty!");
//        if (StringUtils.isEmpty(indexName)) // throw new IllegalArgumentException("indexName cannot be empty!");
//            indexName = MetadataUtil.generateIndexName(unique, null, null );
//        getCurrModel().addIndex(new IndexInfo(indexName, null, unique));
//        return this;
//    }

    @Override
    public IDataStore build() {
        EmpireDataStore _store = new EmpireDataStore();

        for (ModelInfo _modelInfo : this._models.values()) {
            IDataModel _model = _store.addModel(_modelInfo.getName());
            // 设置字段信息
            _modelInfo.getFields().values().forEach((_fieldInfo) -> {
                _model.addField(_fieldInfo.getComment(),
                        _fieldInfo.getFieldName(),
                        _fieldInfo.getFieldType(),
                        _fieldInfo.getLength(),
                        _fieldInfo.isRequired(),
//                        _fieldInfo.getDefValue(),
                        _fieldInfo.getGenerateRule(),
                        _fieldInfo.getDataRules());
            });

            // 设置主键
            String[] _primaryKeys = _modelInfo.getPrimaryKeys();
            if (null != _primaryKeys && _primaryKeys.length > 0)
                _model.setPrimaryKeys(_primaryKeys);

            // 设置索引
            _modelInfo.getIndexes().forEach((_indexInfo) -> {
                _model.addIndex(_indexInfo.getName(), _indexInfo.isUnique(), _indexInfo.getFieldNames());
            });
        }

        // 初始化
        _store.init(this._config);

        return _store;
    }

    /************************************************************
     * 内部方法
     ************************************************************/
    /**
     * @return 当前操作的模型对象。
     */
    private ModelInfo getCurrModel() {
        if (null == this._currModel)
            throw new IllegalStateException("please with model before...");
        return this._currModel;
    }


}
