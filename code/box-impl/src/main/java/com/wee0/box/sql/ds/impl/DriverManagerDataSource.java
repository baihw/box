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

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:05
 * @Description 基于DriverManager的简单数据源实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class DriverManagerDataSource implements DataSource {

    // 线程安全的数据库连接对象。
    private static final ThreadLocal<Connection> _connTL = new ThreadLocal<Connection>();

    // 数据库连接地址。
    private String _jdbcUrl;
    // 数据库用户名。
    private String _user;
    // 数据库密码。
    private String _pwd;
    // 驱动名称。
    private String _driverClass = "com.mysql.jdbc.Driver";

    /**
     * 构造函数。其它参数使用默认值。建议加上 autoReconnect=true，避免连接经常断掉的问题。
     *
     * @param jdbcUrl  数据库连接地址。
     * @param user     数据库用户名。
     * @param password 数据库密码。
     */
    public DriverManagerDataSource(String jdbcUrl, String user, String password) {
        initProperties(jdbcUrl, user, password, null);
    }

    /**
     * 构造函数。其它参数使用默认值。建议加上 autoReconnect=true，避免连接经常断掉的问题。
     *
     * @param jdbcUrl  数据库连接地址。
     * @param user     数据库用户名。
     * @param password 数据库密码。
     */
    public DriverManagerDataSource(String jdbcUrl, String user, String password, String driverClass) {
        initProperties(jdbcUrl, user, password, driverClass);
    }

    /**
     * 初始化配置属性值。
     *
     * @param jdbcUrl     数据库连接地址。
     * @param user        数据库用户名。
     * @param password    数据库密码。
     * @param driverClass 驱动名称。
     */
    private void initProperties(String jdbcUrl, String user, String password, String driverClass) {
        this._jdbcUrl = jdbcUrl;
        this._user = user;
        this._pwd = password;
        this._driverClass = null == driverClass ? "com.mysql.jdbc.Driver" : driverClass;
    }

    /**
     * 从数据源中获取一个数据库连接对象。
     *
     * @return 数据库连接
     */
    public Connection getConn() {
        try {
            return getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 获取数据源对象。
     *
     * @return
     */
    public DataSource getDataSource() {
        return this;
    }

    /**
     * 启动并初始化数据库连接池。
     *
     * @return 是否成功。
     */
    public boolean start() {
        try {
            Class.forName(_driverClass);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return true;
    }

    /**
     * 关闭数据源。
     *
     * @return 是否成功
     */
    public boolean stop() {
        return true;
    }

    /***********************************************************************************
     ******************* 实现 DataSource 接口中的方法 ************************
     ***********************************************************************************/

    public Connection getConnection() throws SQLException {
//			return DriverManager.getConnection( _jdbcUrl, _user, _pwd ) ;
        Connection conn = _connTL.get();
        if (null == conn || conn.isClosed()) {
            conn = DriverManager.getConnection(_jdbcUrl, _user, _pwd);
            _connTL.set(conn);
        }
        return _connTL.get();
    }

    public Connection getConnection(String arg0, String arg1) throws SQLException {
        return getConnection();
    }

    public PrintWriter getLogWriter() throws SQLException {
        return null;
    }

    public int getLoginTimeout() throws SQLException {
        return 0;
    }

    public void setLogWriter(PrintWriter arg0) throws SQLException {
    }

    public void setLoginTimeout(int arg0) throws SQLException {
    }

    public boolean isWrapperFor(Class<?> arg0) throws SQLException {
        return false;
    }

    public <T> T unwrap(Class<T> arg0) throws SQLException {
        return null;
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }

}
