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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 13:20
 * @Description 数据库方言
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDialect {

    /**
     * 获取统计语句
     *
     * @param originalSql 原始语句
     * @return 统计语句
     */
    String getCountSql(String originalSql);

    /**
     * 获取分页语句
     *
     * @param originalSql 原始语句
     * @param pageNum     页码
     * @param pageSize    每页大小
     * @return 分页语句
     */
    String getPageSql(String originalSql, long pageNum, long pageSize);

}
