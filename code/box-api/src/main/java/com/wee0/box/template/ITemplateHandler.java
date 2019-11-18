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

package com.wee0.box.template;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/19 16:09
 * @Description 模板操作工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ITemplateHandler {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.template.freemarker.FreemarkerTemplateHandler";

    /**
     * 使用指定的数据模型处理指定名称的模板，得到处理后的数据
     *
     * @param templateName 模板名称
     * @param dataModel    数据模型
     * @return 处理后的文本内容
     */
    String process(String templateName, Object dataModel);


    /**
     * 使用指定的数据模型处理临时提供的模板内容，得到处理后的数据
     *
     * @param templateText 模板内容
     * @param dataModel    数据模型
     * @return 处理后的文本内容
     */
    String processText(String templateText, Object dataModel);

}
