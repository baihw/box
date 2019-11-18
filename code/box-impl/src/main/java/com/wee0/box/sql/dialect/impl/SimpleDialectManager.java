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

package com.wee0.box.sql.dialect.impl;

import com.wee0.box.sql.dialect.IDialect;
import com.wee0.box.sql.dialect.IDialectManager;
import com.wee0.box.sql.ds.DatabaseId;

import java.io.ObjectStreamException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 13:25
 * @Description 一个简单的数据库方言管理器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleDialectManager implements IDialectManager {

    // 数据库方言集合
    private final Map<DatabaseId, IDialect> dialectMap = new HashMap<>(3);

    private final IDialect defaultDialect;

    @Override
    public IDialect getDialect(DatabaseId databaseId) {
        return dialectMap.get(databaseId);
    }

    @Override
    public IDialect getDialect() {
        return this.defaultDialect;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleDialectManager() {
        if (null != SimpleDialectManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
        // :TODO 获取当前数据库连接信息，设置默认方言。
        this.defaultDialect = new MysqlDialect();
        this.dialectMap.put(DatabaseId.mysql, this.defaultDialect);
        this.dialectMap.put(DatabaseId.h2, this.defaultDialect);
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleDialectManagerHolder {
        private static final SimpleDialectManager _INSTANCE = new SimpleDialectManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleDialectManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleDialectManager me() {
        return SimpleDialectManagerHolder._INSTANCE;
    }

}
