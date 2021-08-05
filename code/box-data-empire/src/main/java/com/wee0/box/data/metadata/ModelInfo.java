package com.wee0.box.data.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:12
 * @Description 模型信息
 * <pre>
 * 补充说明
 * </pre>
 **/
@ToString
@EqualsAndHashCode
public class ModelInfo {

    // 模型名称
    @Getter
    private String name;

    // 模型提示，如：NOSql。
    private String hint;

//    @Getter
//    private String primaryKeyName;

    // 字段信息
    @Getter
    private Map<String, FieldInfo> fields = new LinkedHashMap<>(16, 1.0f);

    // 主键字段集合，可以支持联合主键。
    @Getter
    @Setter
    private String[] primaryKeys;

    // 索引集合
    @Getter
    private Set<IndexInfo> indexes = new HashSet<>(6, 1.0f);

    public ModelInfo(String name) {
        this.name = name;
//        this.primaryKeyName = name.toUpperCase().concat("_PK");
    }

    /**
     * 增加一个字段
     *
     * @param fieldInfo 字段信息
     * @return 链式调用对象
     */
    public ModelInfo addField(FieldInfo fieldInfo) {
        String _fieldName = fieldInfo.getFieldName();
        if (null == _fieldName)
            throw new IllegalStateException("fieldName can't be Null!");
        this.fields.put(_fieldName, fieldInfo);
        return this;
    }

    /**
     * 增加一个索引信息
     *
     * @param indexInfo 索引信息
     * @return 链式调用对象
     */
    public ModelInfo addIndex(IndexInfo indexInfo) {
        if (null == indexInfo) return this;
//        String _indexName = indexInfo.getName();
//        if (null == _indexName)
//            _indexName = MetadataUtil.generateIndexName(indexInfo.isUnique(), this.getName(), indexInfo.getFieldNames());
        this.indexes.add(indexInfo);
        return this;
    }

    /**
     * 获取指定名称的字段
     *
     * @param fieldName 字段名称
     * @return 字段对象
     */
    public FieldInfo getField(String fieldName) {
        return this.fields.get(fieldName);
    }

}
