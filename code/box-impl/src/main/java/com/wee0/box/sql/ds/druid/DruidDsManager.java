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

package com.wee0.box.sql.ds.druid;

import com.alibaba.druid.pool.DruidDataSource;
import com.wee0.box.BoxConstants;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.ds.DatabaseId;
import com.wee0.box.sql.ds.DsHelper;
import com.wee0.box.sql.ds.IDsManager;
import com.wee0.box.sql.ds.IDsProperty;
import com.wee0.box.util.shortcut.CheckUtils;

import javax.sql.DataSource;
import java.io.ObjectStreamException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:52
 * @Description 基于Druid实现的数据源管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class DruidDsManager implements IDsManager {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(DruidDsManager.class);

    /**
     * 默认的驱动名称:com.mysql.jdbc.Driver。
     */
    public static final String DEF_DRIVERCLASSNAME = "com.mysql.jdbc.Driver";

    /**
     * 默认的最大连接数:20。
     */
    public static final int DEF_MAXPOOLSIZE = 20;

    /**
     * 默认的最小连接数:1,连接池中最小的空闲的连接数，低于这个数量会自动创建新的连接。。
     */
    public static final int DEF_MINPOOLSIZE = 1;

    /**
     * 默认的初始化连接数:1。
     */
    public static final int DEF_INITIALPOOLSIZE = 1;

    /**
     * 用来检测连接是否有效的sql，要求是一个查询语句。如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     */
    public static final String DEF_VALIDATIONQUERY = "select 1";

    /**
     * 申请连接时是否执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    public static final boolean DEF_TESTONBORROW = false;

    /**
     * 归还连接时是否执行validationQuery检测连接是否有效，做了这个配置会降低性能。
     */
    public static final boolean DEF_TESTONRETURN = false;

    /**
     * 申请连接的时候检测，如果空闲时间大于maxIdleTime，执行validationQuery检测连接是否有效。建议配置为true，不影响性能，并且保证安全性。
     */
    public static final boolean DEF_TESTWHILEIDLE = true;

    /**
     * 连接的最大空闲时间(单位：毫秒)，超过最大空闲时间的连接将被关闭。
     */
    public static final long DEF_MAXIDLETIME = 300000;

    /**
     * 连接的最小空闲时间(单位：毫秒)，最小空闲时间的连接不会销毁也不会进行检测。
     */
    public static final long DEF_MINIDLETIME = 60000;

    /**
     * 超时时间,获取连接时最大等待时间(单位：毫秒)
     */
    public static final long DEF_TIMEOUT = 6000;

    // 数据源容器
    private final Map<String, DataSource> DSMAP = new LinkedHashMap<>(3, 1.0f);

    // 默认数据源对象
    private DataSource defaultDs = null;
    private DatabaseId defaultDbId = null;

    @Override
    public boolean addDataSourceByProperty(IDsProperty dsProperty) {
        CheckUtils.checkNotNull(dsProperty, "dsProperty can not be null!");

        String _dsName = CheckUtils.checkTrimEmpty(dsProperty.getName(), null);
        if (null == _dsName) {
            // 如果没有配置，Druid默认生成的格式是："DataSource-" + System.identityHashCode(this)
            // 我们这里没有指定的情况，只允许出现在一个数据源上。
            _dsName = DEF_DS_NAME;
        }

        if (DSMAP.containsKey(_dsName)) {
            throw new RuntimeException("Already exists dsName: ".concat(_dsName));
        }

        String _driverClassName = CheckUtils.checkNotTrimEmpty(dsProperty.getDriverClassName(), "driverClassName can not be empty!");
        DruidDataSource _ds = new DruidDataSource();
        _ds.setName(_dsName);
        _ds.setDriverClassName(_driverClassName);
        _ds.setUrl(CheckUtils.checkNotTrimEmpty(dsProperty.getUrl(), "url can not be empty!"));
        _ds.setUsername(CheckUtils.checkNotTrimEmpty(dsProperty.getUsername(), "userName can not be empty!"));
        _ds.setPassword(CheckUtils.checkTrimEmpty(dsProperty.getPassword(), BoxConstants.EMPTY_STRING));

        _ds.setMaxActive(dsProperty.getMaxPoolSize());
        _ds.setMinIdle(dsProperty.getMinPoolSize());
        _ds.setInitialSize(dsProperty.getInitialPoolSize());
        String _validationQuery = CheckUtils.checkTrimEmpty(dsProperty.getValidationQuery(), null);
        if (null == _validationQuery)
            _validationQuery = getValidationQuerySqlByDriver(_driverClassName);
        _ds.setValidationQuery(_validationQuery);
        _ds.setTestOnBorrow(dsProperty.isTestOnBorrow());
        _ds.setTestOnReturn(dsProperty.isTestOnReturn());
        _ds.setTestWhileIdle(dsProperty.isTestWhileIdle());

        _ds.setMinEvictableIdleTimeMillis(dsProperty.getMaxIdleTime());
        _ds.setTimeBetweenEvictionRunsMillis(dsProperty.getMinIdleTime());
        _ds.setMaxWait(dsProperty.getTimeout());
//        _ds.setTimeBetweenLogStatsMillis(300000);

        String _initSqls = CheckUtils.checkTrimEmpty(dsProperty.getConnectionInitSqls(), null);
        if (null != _initSqls) {
            // SET NAMES utf8mb4 COLLATE utf8mb4_unicode_ci
            _ds.setConnectionInitSqls(Arrays.asList(_initSqls));
        }

        _ds.setUseUnfairLock(true);
        _ds.setFailFast(true);
        // _ds.setProxyFilters(Arrays.asList(new DruidLogFilter()));

        return addDataSource(_dsName, _ds);
    }

    @Override
    public boolean addDataSource(String dsName, DataSource dataSource) {
        CheckUtils.checkNotNull(dataSource, "dataSource can't be null!");
        dsName = CheckUtils.checkTrimEmpty(dsName, "dsName can't be empty!");
        if (DSMAP.containsKey(dsName)) {
            throw new RuntimeException("Already exists dsName: ".concat(dsName));
        }
        DSMAP.put(dsName, dataSource);
        log.debug("dsName: {}", dsName);
        return true;
    }

    @Override
    public DataSource getDefaultDataSource() {
        return this.defaultDs;
    }

    @Override
    public DatabaseId getDefaultDatabaseId() {
        return this.defaultDbId;
    }

    public void init() {
        if (DSMAP.isEmpty()) {
            return;
        }

        for (DataSource _ds : DSMAP.values()) {
            if (null != _ds && (_ds instanceof DruidDataSource)) {
                try {
                    ((DruidDataSource) _ds).init();
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            }
        }

        // 设置默认数据源
        DataSource dataSource = DSMAP.get(DEF_DS_NAME);
        if (DSMAP.containsKey(DEF_DS_NAME)) {
            defaultDs = DSMAP.get(DEF_DS_NAME);
        } else {
            defaultDs = DSMAP.entrySet().iterator().next().getValue();
        }

        if (defaultDs instanceof DruidDataSource) {
            defaultDbId = DsHelper.impl().guessDatabaseId(((DruidDataSource) defaultDs).getDriverClassName());
        } else {
            defaultDbId = DsHelper.impl().guessDatabaseId(defaultDs);
        }
        log.info("defaultDatabaseId: {}", defaultDbId);

    }

    @Override
    public void destroy() {
        for (DataSource _ds : DSMAP.values()) {
            if (null != _ds && (_ds instanceof DruidDataSource))
                ((DruidDataSource) _ds).close();
        }
        DSMAP.clear();
        this.defaultDs = null;
        this.defaultDbId = null;
    }

    /**
     * 根据数据库类型，获取一个默认的查询校验语句。
     *
     * @param driveClassName 驱动类名称
     * @return 查询校验语句
     */
    private String getValidationQuerySqlByDriver(String driveClassName) {
        DatabaseId _databaseId = DsHelper.impl().guessDatabaseId(driveClassName);
        switch (_databaseId) {
            case oracle:
                return "SELECT 1 FROM DUAL";
            default:
                return DEF_VALIDATIONQUERY;
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private DruidDsManager() {
        if (null != DruidDsManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class DruidDsManagerHolder {
        private static final DruidDsManager _INSTANCE = new DruidDsManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return DruidDsManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static DruidDsManager me() {
        return DruidDsManagerHolder._INSTANCE;
    }

}
