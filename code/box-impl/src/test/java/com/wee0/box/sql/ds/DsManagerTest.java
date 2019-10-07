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

import com.wee0.box.sql.ds.impl.SimpleDsProperty;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:52
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class DsManagerTest {

    public static void initTestDs() {
        initTestDsByMysql();
//        initTestDsByH2();
    }

    protected static void initTestDsByMysql() {
        SimpleDsProperty _dsProperty = new SimpleDsProperty();
        _dsProperty.setDriverClassName("com.mysql.jdbc.Driver");
        _dsProperty.setUrl("jdbc:mysql://192.168.56.10:3306/box_test?charset=utf8mb4&useSSL=false&useAffectedRows=true");
        _dsProperty.setUsername("box");
        _dsProperty.setPassword("box");
        DsManager.impl().addDataSourceByProperty(_dsProperty);
        DsManager.impl().init();
    }

    protected static void initTestDsByH2() {
        SimpleDsProperty _dsProperty = new SimpleDsProperty();
        _dsProperty.setDriverClassName("org.h2.Driver");
        _dsProperty.setUrl("jdbc:h2:mem:box_test;MODE=MYSQL");
//        _dsProperty.setUrl("jdbc:h2:mem:box_test;MODE=MYSQL;INIT=RUNSCRIPT FROM './src/test/resources/table_init.sql'");
        _dsProperty.setUsername("box");
        _dsProperty.setPassword("box");
        DsManager.impl().addDataSourceByProperty(_dsProperty);
        DsManager.impl().init();
    }

}
