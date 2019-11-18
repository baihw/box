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

package com.wee0.box.sql.template.impl;

import com.wee0.box.sql.ds.DsManagerTest;
import com.wee0.box.sql.template.ISqlTemplateHelper;
import com.wee0.box.util.shortcut.DateUtils;
import org.junit.BeforeClass;
import org.junit.Test;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 7:22
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleSqlTemplateHelperTest {

    private ISqlTemplateHelper impl = SimpleSqlTemplateHelper.me();

    @BeforeClass
    public static void setup() {
        DsManagerTest.initTestDs();
    }

    @Test
    public void generateEntities() {
        Map<String, Object> _dataModel = new HashMap<>();
        // 实体类所属包名
        _dataModel.put("entityPackage", "com.wee0.box.examples.multiModule.module1.entity");
        // 实体类继承的公共基类
        _dataModel.put("entityBase", "com.wee0.box.examples.multiModule.module1.entity.BaseEntity");
        // 作者名称
        _dataModel.put("author", "baihw");
        // 文件创建时间
        _dataModel.put("createDate", DateUtils.getCurrentDate());
        // 生成的实体类保存文件夹位置
        File _entityDir = new File("D:\\test");
        // 不需要在实体类中包含的列，通常是因为在公共基类中已经统一定义了。
        Set<String> _excludeColumns = new HashSet<>(12);
        _excludeColumns.add("ID");
        _excludeColumns.add("CREATE_TIME");
        _excludeColumns.add("CREATE_USER");
        _excludeColumns.add("UPDATE_TIME");
        _excludeColumns.add("UPDATE_USER");
        _excludeColumns.add("IS_DELETED");

        // 不需要在实体类中包含的表，这里排除关系表的生成。
        Set<String> _excludeTables = new HashSet<>(12);
        _excludeTables.add("sys_user_role_rel");
        _excludeTables.add("sys_role_permission_rel");

        // 自定义命名策略
        ISqlTemplateHelper.INamePolicy _namePolicy = new ISqlTemplateHelper.INamePolicy() {
            @Override
            public String renameEntity(String original, String current) {
                // 统一加上Entity后缀。
                return current + "Entity";
            }
        };

        // 调用数据库模板助手类实例生成实体类的方法
        impl.generateEntities(_dataModel, "entity.ftl", _entityDir, _excludeColumns, _excludeTables, _namePolicy);
    }

}
