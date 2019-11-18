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

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.sql.dao.IDaoHandler;
import com.wee0.box.sql.ds.DatabaseId;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 22:02
 * @Description 默认的Dao相关处理实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class DefaultDaoHandler implements IDaoHandler {

    // 数据库方言集合
    private final Map<DatabaseId, IDialect> dialectMap = new HashMap<>(3);

    DefaultDaoHandler() {
        IDialect _defaultDialect = new MysqlDialect();
        this.dialectMap.put(DatabaseId.mysql, _defaultDialect);
        this.dialectMap.put(DatabaseId.h2, _defaultDialect);
        this.dialectMap.put(DatabaseId.oracle, new OracleDialect());
    }

    @Override
    public String generateSql(DatabaseId databaseId, Method method, Class<?> entityClass) {
        IDialect _dialect = getDialect(databaseId);
        final String _methodName = method.getName();
        String _result = null;
        switch (_methodName) {
            case "insertEntity":
                _result = _dialect.insertEntity(entityClass);
                break;
            case "updateEntity":
                _result = _dialect.updateEntity(entityClass);
                break;
            case "deleteById":
                _result = _dialect.deleteById(entityClass);
                break;
            case "deleteByIds":
                _result = _dialect.deleteByIds(entityClass);
                break;
            case "deleteAll":
                _result = _dialect.deleteAll(entityClass);
                break;
            case "countAll":
                _result = _dialect.countAll(entityClass);
                break;
            case "existsById":
                _result = _dialect.existsById(entityClass);
                break;
            case "queryById":
                _result = _dialect.queryById(entityClass);
                break;
            case "queryByIds":
                _result = _dialect.queryByIds(entityClass);
                break;
            case "queryAll":
                _result = _dialect.queryAll(entityClass);
                break;
            case "queryAllByPage":
                _result = _dialect.queryAllByPage(entityClass);
                break;
            default:
                throw new BoxRuntimeException("unsupported method: " + _methodName);
        }
        return _result;
    }

    IDialect getDialect(DatabaseId databaseId) {
        IDialect _dialect = dialectMap.get(databaseId);
        if (null == _dialect)
            throw new BoxRuntimeException("unsupported database: " + databaseId);
        return _dialect;
    }

}
