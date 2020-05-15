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
import org.flywaydb.core.Flyway;
import org.flywaydb.core.api.configuration.ClassicConfiguration;

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
//        initTestDsByMysql();
        initTestDsByH2();

//        _migration();
    }

    protected static void initTestDsByMysql() {
        SimpleDsProperty _dsProperty = new SimpleDsProperty();
        _dsProperty.setDriverClassName("com.mysql.jdbc.Driver");
        _dsProperty.setUrl("jdbc:mysql://testHost:3306/box_example?charset=utf8mb4&useSSL=false&useAffectedRows=true&useUnicode=true&useOldAliasMetadataBehavior=true");
        _dsProperty.setUsername("box");
        _dsProperty.setPassword("box");
        DsManager.impl().addDataSourceByProperty(_dsProperty);
        DsManager.impl().init();
    }

    protected static void initTestDsByH2() {
        SimpleDsProperty _dsProperty = new SimpleDsProperty();
        _dsProperty.setDriverClassName("org.h2.Driver");
//        _dsProperty.setUrl("jdbc:h2:mem:box_test;MODE=MYSQL");
        _dsProperty.setUrl("jdbc:h2:mem:box_test;MODE=MYSQL;INIT=RUNSCRIPT FROM './src/test/resources/db/migration/h2/V1__init.sql'");
        _dsProperty.setUsername("box");
        _dsProperty.setPassword("box");
        DsManager.impl().addDataSourceByProperty(_dsProperty);
        DsManager.impl().init();
    }

    // 数据库初始化
    static void _migration() {
        ClassicConfiguration _config = new ClassicConfiguration();
        _config.setDataSource(DsManager.impl().getDefaultDataSource());
        // 如果是已经开发一段时间的项目需要开启 baselineOnMigrate 否则抛出异常
//        _config.setBaselineOnMigrate(true);
        //设置存放flyway metadata数据的表名，默认"schema_version"，可不写
//        _config.setTable("SCHEMA_VERSION");
        //设置flyway扫描sql升级脚本、java升级脚本的目录路径或包路径，默认"db/migration"，可不写
//        _config.setLocations(new Location("db/migration/h2"));
        //设置sql脚本文件的编码，默认"UTF-8"，可不写
//        _config.setEncoding(Charset.forName("UTF-8"));
        Flyway _flyway = new Flyway(_config);
        _flyway.migrate();
    }

}
