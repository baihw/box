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

import com.wee0.box.util.impl.UtilsCandidate;

import java.io.ObjectStreamException;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/22 7:02
 * @Description 元数据管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
final class MetaDataManager {

    // 实体类表信息映射缓存容器
    private final Map<Class<?>, TableInfo> CLASS_TABLE_MAP = UtilsCandidate.createLruCache(256);

    /**
     * 获取实体类对应的表映射信息
     *
     * @param entityClass 实体类
     * @return 表映射信息
     */
    TableInfo getTableInfo(Class<?> entityClass) {
        TableInfo _result = CLASS_TABLE_MAP.get(entityClass);
        if (null == _result) {
            _result = new TableInfo(entityClass);
            CLASS_TABLE_MAP.putIfAbsent(entityClass, _result);
        }
        return _result;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private MetaDataManager() {
        if (null != MetaDataManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class MetaDataManagerHolder {
        private static final MetaDataManager _INSTANCE = new MetaDataManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return MetaDataManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static MetaDataManager me() {
        return MetaDataManagerHolder._INSTANCE;
    }
}
