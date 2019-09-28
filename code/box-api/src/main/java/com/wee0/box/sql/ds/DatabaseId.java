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

package com.wee0.box.sql.ds;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:31
 * @Description 统一的数据库标识
 * <pre>
 * 补充说明
 * </pre>
 **/
public enum DatabaseId {

    /**
     * mysql数据库
     */
    mysql,

    /**
     * postgres数据库
     */
    postgres,

    /**
     * oracle数据库
     */
    oracle,

    /**
     * sybase数据库
     */
    sybase,

    /**
     * db2数据库
     */
    db2,

    /**
     * 微软数据库
     */
    microsoft,

    /**
     * h2数据库
     */
    h2,

    /**
     * 无法识别的数据库
     */
    unknown;

}
