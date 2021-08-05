package com.wee0.box.data.metadata;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2021/5/27 7:16
 * @Description 索引信息
 * <pre>
 * 补充说明
 * </pre>
 **/
@EqualsAndHashCode
@ToString
public class IndexInfo {

    // 索引名称
    @Getter
    private String name;

    // 是否唯一
    @Getter
    private boolean unique;

    // 索引字段
    @Getter
    private String[] fieldNames;

    public IndexInfo(String name, String[] fieldNames, boolean unique) {
        this.name = name;
        this.fieldNames = fieldNames;
        this.unique = unique;
    }

    public IndexInfo(String name, String[] fieldNames) {
        this(name, fieldNames, false);
    }

}
