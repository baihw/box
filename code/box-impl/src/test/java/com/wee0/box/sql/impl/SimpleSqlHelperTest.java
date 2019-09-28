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

package com.wee0.box.sql.impl;

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.ds.DsManagerTest;
import com.wee0.box.testEntities.SysUserEntity;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:50
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleSqlHelperTest {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(SimpleSqlHelperTest.class);

    @BeforeClass
    public static void setup() {
        DsManagerTest.initTestDs();
    }

    @Test
    public void queryScalar() {
        Long _result = SimpleSqlHelper.me().queryScalar("select 1 from sys_user");
        log.debug("select 1 from sys_user => {}", _result);
    }

    @Test
    public void queryMap() {
        Map<String, Object> _result = SimpleSqlHelper.me().queryMap("select * from sys_user", null);
        log.debug("select * from sys_user => {}", _result);
    }

    @Test
    public void queryMapList() {
        List<Map<String, Object>> _result = SimpleSqlHelper.me().queryMapList("select * from sys_user", null);
        log.debug("select * from sys_user => {}", _result);
    }

    @Test
    public void queryBean() {
        SysUserEntity _entity = SimpleSqlHelper.me().queryBean("select * from sys_user", null, SysUserEntity.class);
        log.debug("entity: {}", _entity);
    }

    @Test
    public void queryBeanList() {
        List<SysUserEntity> _entities = SimpleSqlHelper.me().queryBeanList("select * from sys_user", null, SysUserEntity.class);
        log.debug("entities: {}", _entities);
    }

}
