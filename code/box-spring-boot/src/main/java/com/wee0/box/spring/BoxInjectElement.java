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

package com.wee0.box.spring;

import com.wee0.box.beans.IBeanDefinitionProcessor;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.spi.impl.SimpleSpiManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.InjectionMetadata;

import java.lang.reflect.Field;
import java.lang.reflect.Member;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:12
 * @Description 组件注入
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxInjectElement extends InjectionMetadata.InjectedElement {

    // 日志对象
    private static final ILogger log = LoggerFactory.getLogger(BoxInjectElement.class);

    // 是否必须
    private final boolean required;
    // 组件类型
    private final Class<?> requiredType;
    // 组件名称
    private final String requiredName;

    protected BoxInjectElement(Member member, String requiredName, boolean required) {
        super(member, null);
        if (this.isField) {
            Field _field = (Field) this.member;
            this.requiredType = _field.getType();
            if (null == requiredName || 0 == (requiredName = requiredName.trim()).length()) {
//                requiredName = _field.getName();
                requiredName = null;
            }
            this.requiredName = requiredName;
        } else {
            throw new IllegalStateException("expected Field actual:" + member);
        }
        this.required = required;
    }

    @Override
    protected Object getResourceToInject(Object target, String requestingBeanName) {
        log.debug("requestingBeanName:{}, target:{}, member:{}.", requestingBeanName, target, this.member);
        Object _result = null;
        try {
            if (null == this.requiredName) {
                _result = SpringBoxContext.me().getBean(this.requiredType);
            } else {
                _result = SpringBoxContext.me().getBean(this.requiredName, this.requiredType);
            }
        } catch (BeansException e) {
            List<IBeanDefinitionProcessor> _processors = SimpleSpiManager.getImplLIst(IBeanDefinitionProcessor.class);
            for (IBeanDefinitionProcessor _processor : _processors) {
                _result = _processor.getInstance(this.requiredType);
                if (null != _result)
                    break;
            }
            if (null == _result && this.required)
                throw (e);
        }
        return _result;
    }
}
