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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 22:12
 * @Description 数据库方言支持
 * <pre>
 * 补充说明
 * </pre>
 **/
interface IDialect {

    String insertEntity(Class<?> entityClass);

    String updateEntity(Class<?> entityClass);

    String deleteById(Class<?> entityClass);

    String deleteByIds(Class<?> entityClass);

    String deleteAll(Class<?> entityClass);

    String countAll(Class<?> entityClass);

    String existsById(Class<?> entityClass);

    String queryById(Class<?> entityClass);

    String queryByIds(Class<?> entityClass);

    String queryAll(Class<?> entityClass);

    String queryAllByPage(Class<?> entityClass);

}
