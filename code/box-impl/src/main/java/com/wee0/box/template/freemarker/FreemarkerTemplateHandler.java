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

package com.wee0.box.template.freemarker;

import com.wee0.box.BoxConfig;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.template.ITemplateHandler;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;

import java.io.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/19 15:12
 * @Description 基于Freemarker组件实现的模板操作工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public class FreemarkerTemplateHandler implements ITemplateHandler {

    // 默认的模板文件存放目录名称
    private static final String DEF_DIR_NAME = "templates";
    // 默认编码使用框架全局编码设置
    private static final String DEF_ENCODING = BoxConfig.impl().getEncoding();

    // 全局共享配置对象
    private final Configuration configuration;

    @Override
    public String process(String templateName, Object dataModel) {
        Template _template = null;
        try {
            _template = configuration.getTemplate(templateName);
            StringWriter _writer = new StringWriter();
            _template.process(dataModel, _writer);
            return _writer.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public String processText(String templateText, Object dataModel) {
        try {
            Template _template = new Template("_tempTemplate", new StringReader(templateText), this.configuration);
            StringWriter _writer = new StringWriter();
            _template.process(dataModel, _writer);
            return _writer.toString();
        } catch (IOException | TemplateException e) {
            throw new IllegalStateException(e);
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private FreemarkerTemplateHandler() {
        if (null != FreemarkerTemplateHandlerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        // 初始化全局共享配置对象
//        configuration = new Configuration(Configuration.VERSION_2_3_29);
        configuration = new Configuration(Configuration.getVersion());
        File _templateDirectory = new File(BoxConfig.impl().getResourceDir(), DEF_DIR_NAME);
        try {
            configuration.setDirectoryForTemplateLoading(_templateDirectory);
        } catch (IOException e) {
            throw new BoxRuntimeException(e);
        }
        configuration.setDefaultEncoding(DEF_ENCODING);
        configuration.setClassicCompatible(true); // 处理空值为空字符串
        configuration.setNumberFormat("#");
        configuration.setTemplateExceptionHandler(TemplateExceptionHandler.RETHROW_HANDLER);
//        configuration.clearTemplateCache();
    }

    // 当前对象唯一实例持有者。
    private static final class FreemarkerTemplateHandlerHolder {
        private static final FreemarkerTemplateHandler _INSTANCE = new FreemarkerTemplateHandler();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return FreemarkerTemplateHandlerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static FreemarkerTemplateHandler me() {
        return FreemarkerTemplateHandlerHolder._INSTANCE;
    }
}
