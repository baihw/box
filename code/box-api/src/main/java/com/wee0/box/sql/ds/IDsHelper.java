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

import javax.sql.DataSource;
import java.sql.Connection;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:32
 * @Description 数据源操作助手
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDsHelper {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.sql.ds.impl.SimpleDsHelper";

    /**
     * 根据数据源属性信息创建一个数据源对象
     *
     * @param dsProperty 数据源属性信息
     * @return 数据源对象
     */
    DataSource createDataSource(IDsProperty dsProperty);

    /**
     * 创建一个简单的数据源对象
     *
     * @param driverClassName 驱动类名称
     * @param url             连接地址
     * @param username        用户名
     * @param password        密码
     * @return 数据库连接对象
     */
    DataSource createDataSource(String driverClassName, String url, String username, String password);

    /**
     * 根据数据库驱动类名称获取数据库厂商标识
     *
     * @param driverClassName 驱动类名称
     * @return 数据库厂商标识
     */
    DatabaseId guessDatabaseId(String driverClassName);

    /**
     * 根据数据源对象获取数据库厂商标识
     *
     * @param dataSource 数据源对象
     * @return 数据库厂商标识
     */
    DatabaseId guessDatabaseId(DataSource dataSource);

    /**
     * 根据数据库连接对象获取数据库厂商标识
     *
     * @param connection 数据库连接对象
     * @return 数据库厂商标识
     */
    DatabaseId guessDatabaseId(Connection connection);

}
