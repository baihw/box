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

package com.wee0.box.sql.dao;

import com.wee0.box.sql.ds.DatabaseId;

import java.lang.reflect.Method;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 10:52
 * @Description Dao相关处理
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDaoHandler {

    /**
     * 为指定方法生成sql语句
     *
     * @param databaseId  数据库标识
     * @param method      方法对象
     * @param entityClass 实体类型
     * @return sql语句
     */
    String generateSql(DatabaseId databaseId, Method method, Class<?> entityClass);


}
