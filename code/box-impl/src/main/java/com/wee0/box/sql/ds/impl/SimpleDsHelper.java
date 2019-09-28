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

package com.wee0.box.sql.ds.impl;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.sql.ds.DatabaseId;
import com.wee0.box.sql.ds.IDsHelper;
import com.wee0.box.sql.ds.IDsProperty;
import com.wee0.box.util.shortcut.CheckUtils;

import javax.sql.DataSource;
import java.io.ObjectStreamException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.SQLException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:06
 * @Description 一个简单的数据源操作助手实现。
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleDsHelper implements IDsHelper {

    // 数据源对应的数据库标识缓存
    private static final Map<DataSource, DatabaseId> databaseIdCache = new ConcurrentHashMap<>(8);

    @Override
    public DataSource createDataSource(IDsProperty dsProperty) {
        CheckUtils.checkNotNull(dsProperty, "dsProperty can't be null!");
        return createDataSource(dsProperty.getDriverClassName(), dsProperty.getUrl(), dsProperty.getUsername(), dsProperty.getPassword());
    }

    @Override
    public DataSource createDataSource(String driverClassName, String url, String username, String password) {
        driverClassName = CheckUtils.checkNotTrimEmpty(driverClassName, "driverClassName can't be empty!");
        url = CheckUtils.checkNotTrimEmpty(url, "url can't be empty!");
        DriverManagerDataSource _ds = new DriverManagerDataSource(url, username, password, driverClassName);
        return _ds;
    }

    @Override
    public DatabaseId guessDatabaseId(String driverClassName) {
        driverClassName = CheckUtils.checkNotTrimEmpty(driverClassName, "driverClassName can't be empty!");
        driverClassName = driverClassName.toLowerCase();
        if (-1 != driverClassName.indexOf(DatabaseId.mysql.name()))
            return DatabaseId.mysql;
        if (-1 != driverClassName.indexOf(DatabaseId.postgres.name()))
            return DatabaseId.postgres;
        if (-1 != driverClassName.indexOf(DatabaseId.oracle.name()))
            return DatabaseId.oracle;
        if (-1 != driverClassName.indexOf(DatabaseId.microsoft.name()))
            return DatabaseId.microsoft;
        if (-1 != driverClassName.indexOf(DatabaseId.db2.name()))
            return DatabaseId.db2;
        if (-1 != driverClassName.indexOf(DatabaseId.h2.name()))
            return DatabaseId.h2;
        if (-1 != driverClassName.indexOf(DatabaseId.sybase.name()))
            return DatabaseId.sybase;
        return DatabaseId.unknown;
    }

    @Override
    public DatabaseId guessDatabaseId(DataSource dataSource) {
        DatabaseId _id = databaseIdCache.get(dataSource);
        if (null != _id)
            return _id;
        try (Connection _conn = dataSource.getConnection()) {
            _id = guessDatabaseId(_conn);
            databaseIdCache.putIfAbsent(dataSource, _id);
            return _id;
        } catch (BoxRuntimeException e) {
            throw e;
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public DatabaseId guessDatabaseId(Connection connection) {
        try {
            DatabaseMetaData _metaData = connection.getMetaData();
            String _databaseName = _metaData.getDatabaseProductName();
            String _driverName = _metaData.getDriverName();
//            int _majorVersion = _metaData.getDatabaseMajorVersion();
//            int _minorVersion = _metaData.getDatabaseMinorVersion();
            return guessDatabaseId(_databaseName, _driverName);
        } catch (SQLException e) {
            throw new BoxRuntimeException(e);
        }
    }

    private static DatabaseId guessDatabaseId(String databaseName, String driverName) {
        if (null != databaseName) {
            databaseName = databaseName.toLowerCase();
            if (DatabaseId.h2.name().equals(databaseName))
                return DatabaseId.h2;
            if (DatabaseId.mysql.name().equals(databaseName))
                return DatabaseId.mysql;
            if (-1 != databaseName.indexOf(DatabaseId.postgres.name()) || "EnterpriseDB".equalsIgnoreCase(databaseName))
                return DatabaseId.postgres;
            if (DatabaseId.oracle.name().equals(databaseName))
                return DatabaseId.oracle;
            if (-1 != databaseName.indexOf(DatabaseId.microsoft.name()))
                return DatabaseId.microsoft;
            if (-1 != databaseName.indexOf(DatabaseId.db2.name()))
                return DatabaseId.db2;
            if (-1 != databaseName.indexOf(DatabaseId.sybase.name()))
                return DatabaseId.sybase;
//            if(-1 != databaseName.indexOf(DatabaseId.sqlite.name()))
//                return DatabaseId.sqlite;
        }

        if (null != driverName) {
            if (-1 != driverName.indexOf("MariaDB"))
                return DatabaseId.mysql;
        }

        return DatabaseId.unknown;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleDsHelper() {
        if (null != SimpleDsHelperHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleDsHelperHolder {
        private static final SimpleDsHelper _INSTANCE = new SimpleDsHelper();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleDsHelperHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleDsHelper me() {
        return SimpleDsHelperHolder._INSTANCE;
    }

}
