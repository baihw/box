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

package com.wee0.box.testObjects;

import com.wee0.box.beans.IBeanDefinitionProcessor;
import com.wee0.box.beans.annotation.BoxServiceApi;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.springframework.core.annotation.AnnotationUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:13
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class TestBeanDefinitionProcessor implements IBeanDefinitionProcessor {

    private static final ILogger log = LoggerFactory.getLogger(TestBeanDefinitionProcessor.class);

    private static final String NAME_API_FACTORY_BEAN = TestServiceApiFactoryBean.class.getName();

    @Override
    public String getFactoryBeanClassName(Class clazz) {
        if (clazz.isInterface() && null != AnnotationUtils.findAnnotation(clazz, BoxServiceApi.class))
            return NAME_API_FACTORY_BEAN;
        return null;
    }

    @Override
    public Object afterInitialization(Object obj) {
        log.debug("obj:{}.", obj);
        return obj;
    }

    @Override
    public Object getInstance(Class clazz) {
        return null;
    }

}
