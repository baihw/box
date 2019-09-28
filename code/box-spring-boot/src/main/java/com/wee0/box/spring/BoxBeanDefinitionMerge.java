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
import com.wee0.box.beans.annotation.BoxBean;
import com.wee0.box.beans.annotation.BoxInject;
import com.wee0.box.beans.annotation.BoxPrimary;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.spi.impl.SimpleSpiManager;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.Role;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:18
 * @Description 合并修改bean定义
 * <pre>
 * 补充说明
 * </pre>
 **/
//@Role(BeanDefinition.ROLE_INFRASTRUCTURE)
//@Component
public class BoxBeanDefinitionMerge extends InstantiationAwareBeanPostProcessorAdapter implements MergedBeanDefinitionPostProcessor {

    // 日志对象
    private static final ILogger log = LoggerFactory.getLogger(BoxBeanDefinitionMerge.class);

    // 对象注入元数据集合
    private final ConcurrentMap<String, InjectionMetadata> injectionMetadataCache = new ConcurrentHashMap<String, InjectionMetadata>(256);

    @Override
    public void postProcessMergedBeanDefinition(final RootBeanDefinition beanDefinition, Class<?> beanType, String beanName) {
        if (null == beanType || null == beanDefinition)
            return;
        if (null == AnnotationUtils.findAnnotationDeclaringClassForTypes(InternalUtils.AUTO_ANNOTATIONS, beanType)) {
            log.trace("skip name:{}, type:{}.", beanName, beanType);
            return;
        }
        List<IBeanDefinitionProcessor> _processors = SimpleSpiManager.getImplLIst(IBeanDefinitionProcessor.class);
        for (IBeanDefinitionProcessor _processor : _processors) {
            _processor.beforeInitialization(beanType);
        }
        if (null != AnnotationUtils.findAnnotation(beanType, BoxPrimary.class)) {
            beanDefinition.setPrimary(true);
        }
        final BoxBean _beanAnnotation = AnnotationUtils.findAnnotation(beanType, BoxBean.class);
        if (null != _beanAnnotation) {
            if (_beanAnnotation.prototype()) {
                beanDefinition.setScope(BeanDefinition.SCOPE_PROTOTYPE);
            }
            if (StringUtils.hasLength(_beanAnnotation.initMethod())) {
                beanDefinition.setInitMethodName(_beanAnnotation.initMethod());
            }
            if (StringUtils.hasLength(_beanAnnotation.destroyMethod())) {
                beanDefinition.setDestroyMethodName(_beanAnnotation.destroyMethod());
            }
        }
        log.debug("beanDefinition:{}.", beanDefinition);
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        if (null == bean)
            return bean;
        List<IBeanDefinitionProcessor> _processors = SimpleSpiManager.getImplLIst(IBeanDefinitionProcessor.class);
        for (IBeanDefinitionProcessor _processor : _processors) {
            bean = _processor.afterInitialization(bean);
        }
//        final Class _beanCla = bean.getClass();
//        BoxBean _beanAnnotation = AnnotationUtils.findAnnotation(_beanCla, BoxBean.class);
//        if (null != _beanAnnotation) {
//            log.trace("afterBean... name:{}, class:{}.", beanName, _beanCla);
//            ReflectionUtils.doWithLocalFields(_beanCla, new ReflectionUtils.FieldCallback() {
//                @Override
//                public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
//                    Class _filedType = field.getType();
//                    if (Modifier.isStatic(field.getModifiers()) && ILogger.class.isAssignableFrom(_filedType)) {
//                        field.setAccessible(true);
//                        Object _fieldValue = field.get(null);
//                        if (null == _fieldValue) {
//                            field.set(null, LoggerFactory.getLogger(_beanCla));
//                            log.trace("inject log:{}", field.get(null));
//                        }
//                    }
////                    BoxValue _configValue = field.getAnnotation(BoxValue.class);
////                    if (null != _configValue) {
////                        //:TODO 注入配置属性值
////                    }
//                }
//            });
//        }
        return bean;
    }

    @Override
    public PropertyValues postProcessPropertyValues(PropertyValues pvs, PropertyDescriptor[] pds, Object bean, String beanName) throws BeansException {
        if (null == bean)
            return pvs;
        Class<?> _beanClass = bean.getClass();
        if (null == AnnotationUtils.findAnnotationDeclaringClassForTypes(InternalUtils.AUTO_ANNOTATIONS, _beanClass)) {
            log.trace("skip name:{}, type:{}.", beanName, _beanClass);
            return pvs;
        }
        InjectionMetadata _metadata = findReferenceMetadata(beanName, bean.getClass(), pvs);
        try {
            _metadata.inject(bean, beanName, pvs);
        } catch (Throwable throwable) {
            throw new BeanCreationException(beanName, "Injection of @BoxInject dependencies failed", throwable);
        }
        return pvs;
    }

    /**
     * 查找注入元数据对象
     *
     * @param beanName 组件标识
     * @param clazz    组件类型
     * @param pvs      属性集合
     * @return 注入元数据对象
     */
    private InjectionMetadata findReferenceMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {

        String _cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        InjectionMetadata _metadata = this.injectionMetadataCache.get(_cacheKey);
        if (InjectionMetadata.needsRefresh(_metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                _metadata = this.injectionMetadataCache.get(_cacheKey);
                if (InjectionMetadata.needsRefresh(_metadata, clazz)) {
                    if (null != _metadata) {
                        _metadata.clear(pvs);
                    }
                    try {
                        _metadata = buildReferenceMetadata(clazz);
                        this.injectionMetadataCache.put(_cacheKey, _metadata);
                    } catch (NoClassDefFoundError err) {
                        throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() + "] for reference metadata: could not find class that it depends on", err);
                    }
                }
            }
        }
        return _metadata;
    }

    /**
     * 构建注入元数据对象
     *
     * @param beanClass 组件类型
     * @return 注入元数据对象
     */
    private InjectionMetadata buildReferenceMetadata(final Class<?> beanClass) {
        final List<InjectionMetadata.InjectedElement> _elements = new LinkedList<InjectionMetadata.InjectedElement>();

        _elements.addAll(findFieldReferenceMetadata(beanClass));
//        _elements.addAll(findMethodReferenceMetadata(beanClass));

        return new InjectionMetadata(beanClass, _elements);
    }

    /**
     * 查找字段注入元数据
     *
     * @param beanClass 组件类型
     * @return 字段注入元数据
     */
    private List<InjectionMetadata.InjectedElement> findFieldReferenceMetadata(final Class<?> beanClass) {

        final List<InjectionMetadata.InjectedElement> elements = new LinkedList<InjectionMetadata.InjectedElement>();

        ReflectionUtils.doWithFields(beanClass, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {
                BoxInject _inject = field.getAnnotation(BoxInject.class);
                if (null == _inject) {
                    return;
                }
                if (Modifier.isStatic(field.getModifiers())) {
                    log.warn("@BoxInject 注解不支持静态方法: {}", field);
                    return;
                }
                elements.add(new BoxInjectElement(field, _inject.name(), _inject.required()));
            }
        });

        return elements;
    }

}
