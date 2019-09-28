/*
 * Copyright (c) 2019-present, wee0.com.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wee0.box.sql.dao.mybatis;

import com.wee0.box.sql.annotation.BoxColumn;
import com.wee0.box.sql.annotation.BoxId;
import com.wee0.box.sql.annotation.BoxTable;
import com.wee0.box.util.shortcut.ObjectUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/21 7:10
 * @Description 表信息
 * <pre>
 * 补充说明
 * </pre>
 **/
final class TableInfo {

    // 关联实体对象
    private final Class<?> entityClass;
    // 关联表名
    private final String tableName;

    // 字段关联列名数据集合
    private final Map<Field, String> fieldColumns = new LinkedHashMap<>();
    private final Map<String, Field> columnFields = new LinkedHashMap<>();

    // 无法识别主键时，默认使用的主键列名称。
    private static final String DEF_ID_NAME = "id";
    // 主键字段
    private Field idField;

    TableInfo(Class<?> entityClass) {
        this.entityClass = entityClass;

        BoxTable _table = entityClass.getAnnotation(BoxTable.class);
        if (null == _table || 0 == _table.name().length()) {
            this.tableName = entityClass.getSimpleName();
        } else {
            this.tableName = _table.name();
//            this.tableName = CheckUtils.checkNotTrimEmpty(_table.name(), "%s BoxTable.name cannot be empty!", entityClass.getName());
        }

        appendFields(entityClass, fieldColumns);
        while (null != entityClass.getSuperclass() && Object.class != entityClass) {
            entityClass = entityClass.getSuperclass();
            appendFields(entityClass, fieldColumns);
        }

        if (null == idField) {
            idField = columnFields.get(DEF_ID_NAME);
        }

        if (null == idField)
            throw new IllegalStateException("id field not found!");
    }

    /**
     * @return 表名
     */
    String getTableName() {
        return this.tableName;
    }

    /**
     * @return 主键字段对象
     */
    Field getIdField() {
        return this.idField;
    }

    /**
     * @return 主键列名称
     */
    String getIdColumn() {
        return this.fieldColumns.get(this.idField);
    }

    /**
     * 访问所有字段关联列名数据
     *
     * @param consumer 数据消费方法
     */
    void visitFieldColumns(BiConsumer<Field, String> consumer) {
        this.fieldColumns.forEach(consumer);
    }

    @Override
    public String toString() {
        return ObjectUtils.impl().reflectionToString(this);
    }

    // 追加字段映射信息
    void appendFields(Class<?> clazz, Map<Field, String> fieldMap) {
        Field[] _fields = clazz.getDeclaredFields();
        if (null == _fields || 0 == _fields.length)
            return;
        for (final Field _field : _fields) {
            final String _fieldName = _field.getName();
            if (-1 != _fieldName.indexOf('$'))
                continue;
            if (Modifier.isTransient(_field.getModifiers()))
                continue;
            if (Modifier.isStatic(_field.getModifiers()))
                continue;

            BoxId _boxId = _field.getAnnotation(BoxId.class);
            if (null != _boxId) {
                if (null != idField)
                    throw new IllegalStateException("BoxId already exist on " + idField);
                this.idField = _field;
            }

            BoxColumn _boxColumn = _field.getAnnotation(BoxColumn.class);
            if (null == _boxColumn || 0 == _boxColumn.name().length()) {
                putFieldColumnRelation(_field, _fieldName);
                continue;
            }
//            if (0 == _boxColumn.name().length())
//                throw new IllegalStateException(_fieldName + " BoxColumn.name cannot be empty!");
            putFieldColumnRelation(_field, _boxColumn.name());
        }
    }

    void putFieldColumnRelation(Field field, String column) {
        this.fieldColumns.put(field, column);
        this.columnFields.put(column, field);
    }


}
