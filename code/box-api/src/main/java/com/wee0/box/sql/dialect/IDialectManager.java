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

package com.wee0.box.sql.dialect;

import com.wee0.box.sql.ds.DatabaseId;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 13:22
 * @Description 数据库方言管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDialectManager {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.sql.dialect.impl.SimpleDialectManager";

    /**
     * 获取指定标识的数据库方言
     *
     * @param databaseId 数据库标识
     * @return 数据库方言
     */
    IDialect getDialect(DatabaseId databaseId);

    /**
     * 获取当前环境中使用的默认数据库方言
     *
     * @return 默认数据库方言
     */
    IDialect getDialect();


}
