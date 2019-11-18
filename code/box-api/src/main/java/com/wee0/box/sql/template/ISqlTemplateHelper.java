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

package com.wee0.box.sql.template;

import java.io.File;
import java.util.Map;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 7:02
 * @Description 数据库相关模板生成助手
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ISqlTemplateHelper {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.sql.template.impl.SimpleSqlTemplateHelper";

    /**
     * 自定义命名策略
     */
    interface INamePolicy {
        /**
         * 实体名称重命名
         *
         * @param original 原始名称
         * @param current  当前系统采用的名称
         * @return 最终使用的名称
         */
        String renameEntity(String original, String current);
    }

    /**
     * 根据当前项目数据库表信息生成所有表的对应实体类
     *
     * @param dataModel       初始模型数据，根据模板需要自行设置。
     * @param templateName    模板名称
     * @param outputDirectory 文件保存目录
     * @param excludeColumns  生成实体类时排除的列名称集合，通常是在继承的父类中已经提供，避免重复。
     * @param excludeTables   生成实体类时排除的表名称集合。
     * @param namePolicy      自定义命名策略。
     */
    void generateEntities(Map<String, Object> dataModel, String templateName, File outputDirectory, Set<String> excludeColumns, Set<String> excludeTables, INamePolicy namePolicy);

    /**
     * 根据当前项目数据库表信息生成所有表的对应实体类
     *
     * @param dataModel       初始模型数据，根据模板需要自行设置。
     * @param templateName    模板名称
     * @param outputDirectory 文件保存目录
     * @param excludeColumns  生成实体类时排除的列名称集合，通常是在继承的父类中已经提供，避免重复
     */
    void generateEntities(Map<String, Object> dataModel, String templateName, File outputDirectory, Set<String> excludeColumns);

}
