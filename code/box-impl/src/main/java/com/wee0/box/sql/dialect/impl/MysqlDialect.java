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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/11/10 13:39
 * @Description Mysql数据库方言
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MysqlDialect implements IDialect {

    @Override
    public String getCountSql(String originalSql) {
        // :TODO 执行统计时如果能够识别sql语句并去除掉排序部分，在不影响准确性的前提下效率会更高。
        StringBuilder _builder = new StringBuilder(originalSql.length() + 32);
        _builder.append("SELECT COUNT(*) FROM (");
        _builder.append(originalSql);
        _builder.append(") _COUNT;");
        return _builder.toString();
    }

    @Override
    public String getPageSql(String originalSql, long pageNum, long pageSize) {
        if (pageNum < 1L)
            pageNum = 1L;
        if (pageSize < 1L)
            pageSize = 10L;
        StringBuilder _builder = new StringBuilder(originalSql.length() + 16);
        _builder.append(originalSql);
        _builder.append(" limit ");
        _builder.append((pageNum - 1L) * pageSize);
        _builder.append(", ");
        _builder.append(pageSize);
        _builder.append(";");
        return _builder.toString();
    }

}
