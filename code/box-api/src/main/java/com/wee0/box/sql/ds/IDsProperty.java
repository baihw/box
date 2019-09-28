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
 * @CreateDate 2019/9/1 8:29
 * @Description 数据源属性信息
 * <pre>
 * 统一的标准数据源属性。
 * </pre>
 **/
public interface IDsProperty {

    /**
     * 获取唯一名称，多数据源时用来区分不同的数据源。
     *
     * @return 唯一名称
     */
    String getName();

    /**
     * 获取驱动类名称。
     *
     * @return 驱动类名称
     */
    String getDriverClassName();

    /**
     * 获取连接地址。
     *
     * @return 连接地址
     */
    String getUrl();

    /**
     * 获取连接用户名。
     *
     * @return 连接用户名
     */
    String getUsername();

    /**
     * 获取连接密码。
     *
     * @return 连接密码
     */
    String getPassword();

    /**
     * 获取最大连接数。
     *
     * @return 最大连接数
     */
    int getMaxPoolSize();

    /**
     * 获取最小连接数。
     *
     * @return 最小连接数
     */
    int getMinPoolSize();

    /**
     * 获取初始化连接数。
     *
     * @return 初始化连接数
     */
    int getInitialPoolSize();

    /**
     * 获取检测连接是否有效的查询语句，如果validationQuery为null，testOnBorrow、testOnReturn、testWhileIdle都不会起作用。
     *
     * @return 检测连接是否有效的查询语句
     */
    String getValidationQuery();

    /**
     * 是否在申请连接时执行validationQuery检测连接是否有效。
     *
     * @return 是否在申请连接时执行validationQuery检测连接是否有效
     */
    boolean isTestOnBorrow();

    /**
     * 是否在归还连接时执行validationQuery检测连接是否有效。
     *
     * @return 是否在归还连接时执行validationQuery检测连接是否有效
     */
    boolean isTestOnReturn();

    /**
     * 是否在空闲时间大于minIdleTime时执行validationQuery检测连接是否有效。建议配置为true，不影响性能，并且保证安全性。
     *
     * @return 是否在空闲时间大于minIdleTime时执行validationQuery检测连接是否有效
     */
    boolean isTestWhileIdle();

    /**
     * 获取连接的最大空闲时间(单位：毫秒)，超过最大空闲时间的连接将被关闭。
     *
     * @return 连接的最大空闲时间
     */
    long getMaxIdleTime();

    /**
     * 获取连接的最小空闲时间(单位：毫秒)，最小空闲时间的连接不会销毁也不会触发对连接有效性的检测。
     *
     * @return 连接的最小空闲时间
     */
    long getMinIdleTime();

    /**
     * 获取连接时的最大等待时间(单位：毫秒)。
     *
     * @return 超时时间
     */
    long getTimeout();

    /**
     * 获取物理连接初始化时执行的sql。
     *
     * @return 物理连接初始化时执行的sql
     */
    String getConnectionInitSqls();

}
