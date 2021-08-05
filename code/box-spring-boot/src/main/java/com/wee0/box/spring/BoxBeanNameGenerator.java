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

import com.wee0.box.beans.annotation.BoxBean;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.AnnotationBeanNameGenerator;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;

import java.io.IOException;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 9:06
 * @Description 自定义组件名称生成器
 * <pre>
 * 与默认的生成策略不同的是，按照我们的开发规范，以I开头的接口在生成组件名称时，将去掉I然后将首字母转小写。
 * </pre>
 **/
public class BoxBeanNameGenerator extends AnnotationBeanNameGenerator implements BeanNameGenerator {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxBeanNameGenerator.class);

    // 组件标识属性名
    private static final String KEY_NAME = "name";
    // 服务接口属性名
    private static final String KEY_SERVICE_API = "serviceApi";

    private final MetadataReaderFactory metadataReaderFactory;

    public BoxBeanNameGenerator(MetadataReaderFactory metadataReaderFactory) {
        this.metadataReaderFactory = metadataReaderFactory;
    }

    @Override
    protected String buildDefaultBeanName(BeanDefinition definition) {
        String _result = null;
        final String _beanClassName = definition.getBeanClassName();
        MetadataReader _metadataReader;
        try {
            _metadataReader = this.metadataReaderFactory.getMetadataReader(_beanClassName);
        } catch (IOException ex) {
            throw new BeanDefinitionStoreException("Failed to read candidate component class: " + _beanClassName, ex);
        }
        AnnotationMetadata _annotationMetadata = _metadataReader.getAnnotationMetadata();
        Map<String, Object> _annotationAttributes = _annotationMetadata.getAnnotationAttributes(BoxBean.class.getName(), true);
        _result = getValueFromAttr(_annotationAttributes, KEY_NAME);
//        if (null == _result) {
//            _annotationAttributes = _annotationMetadata.getAnnotationAttributes(BoxService.class.getName(), true);
//            _result = getValueFromAttr(_annotationAttributes, KEY_NAME);
//            if (null == _result && null != _annotationAttributes) {
//                String _apiInterfaceName = getValueFromAttr(_annotationAttributes, KEY_SERVICE_API);
//                if (null == _apiInterfaceName) {
//                    throw new IllegalStateException(KEY_SERVICE_API + " can't be empty!");
//                }
//                _result = InternalUtils.generateBeanName(_apiInterfaceName);
//            }
//        }
        if (null == _result) {
            if (_metadataReader.getClassMetadata().isInterface()) {
                _result = InternalUtils.generateInterfaceBeanName(_beanClassName);
            }
        }
        if (null == _result) {
            _result = InternalUtils.generateBeanName(_beanClassName);
        }
        log.trace("generator {} name: {}", _beanClassName, _result);
        return _result;
    }

    /**
     * 从注解属性集合中获取指定名称的属性值，如果存在，返回其值，否则返回null。
     *
     * @param attributes 注解属性集合
     * @param key        键名
     * @return null / 值
     */
    private static String getValueFromAttr(Map<String, Object> attributes, String key) {
        if (null == attributes)
            return null;
        Object _nameObj = attributes.get(key);
        if (null == _nameObj)
            return null;
        String _name = String.valueOf(_nameObj).trim();
        if (0 == _name.length())
            return null;
        return _name;
    }

}
