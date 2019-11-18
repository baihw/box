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

import com.wee0.box.sql.dao.IDaoHandler;
import com.wee0.box.sql.ds.DatabaseId;
import com.wee0.box.util.impl.UtilsCandidate;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 22:06
 * @Description Mysql数据库方言支持
 * <pre>
 * 补充说明
 * </pre>
 **/
final class MysqlDialect extends AbstractDialect implements IDialect {

//    @Override
//    public String queryAllByPage(Class<?> entityClass) {
//        StringBuilder _builder = new StringBuilder(32);
//        TableInfo _tableInfo = MetaDataManager.me().getTableInfo(entityClass);
//        _builder.append("select * from ").append(_tableInfo.getTableName());
//        _builder.append(" limit ${(param1-1)*param2}, #{param2}");
//        return _builder.toString();
//    }

}
