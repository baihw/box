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

import com.wee0.box.beans.IDestroyable;

import javax.sql.DataSource;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:33
 * @Description 数据源管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDsManager extends IDestroyable {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.sql.ds.druid.DruidDsManager";

    /**
     * 默认的数据源名称
     */
    String DEF_DS_NAME = "datasource";

    /**
     * 根据数据源配置信息创建一个数据源对象
     *
     * @param dsProperty 数据源配置信息
     * @return 成功返回true，失败返回false。
     */
    boolean addDataSourceByProperty(IDsProperty dsProperty);

    /**
     * 增加一个指定名称的数据源
     *
     * @param dsName     名称
     * @param dataSource 数据源
     * @return 成功返回true，失败返回false。
     */
    boolean addDataSource(String dsName, DataSource dataSource);

    /**
     * 获取默认的数据源对象
     *
     * @return 数据源对象
     */
    DataSource getDefaultDataSource();

    /**
     * 获取默认数据源的数据库厂商标识
     *
     * @return 数据库厂商标识
     */
    DatabaseId getDefaultDatabaseId();

}
