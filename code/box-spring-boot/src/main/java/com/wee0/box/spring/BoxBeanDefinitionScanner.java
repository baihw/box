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
import com.wee0.box.beans.annotation.BoxIgnore;
import com.wee0.box.spi.impl.SimpleSpiManager;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionHolder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ClassPathBeanDefinitionScanner;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.ClassUtils;

import java.lang.annotation.Annotation;
import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:08
 * @Description 自定义组件扫描器
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class BoxBeanDefinitionScanner extends ClassPathBeanDefinitionScanner {

    public BoxBeanDefinitionScanner(BeanDefinitionRegistry registry) {
        super(registry, false);
    }

    /**
     * 注册内置过滤器
     */
    public void registerFilters() {
        for (Class<? extends Annotation> annotationClass : InternalUtils.AUTO_ANNOTATIONS) {
            addIncludeFilter(new AnnotationTypeFilter(annotationClass, false, true));
        }
    }

    /**
     * 注册过滤器
     *
     * @param filters 过滤器集合
     */
    void registerFilters(List<TypeFilter> filters) {
        if (null == filters || filters.isEmpty())
            return;
        for (TypeFilter _filter : filters) {
            if (null == _filter)
                continue;
            addIncludeFilter(_filter);
        }
    }

    @Override
    protected void registerDefaultFilters() {
        super.registerDefaultFilters();
    }

    @Override
    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
        AnnotationMetadata _metadata = beanDefinition.getMetadata();
        if (_metadata.hasAnnotation(BoxIgnore.class.getName()))
            return false;
        return _metadata.isIndependent();
    }

    @Override
    protected Set<BeanDefinitionHolder> doScan(String... basePackages) {
        Set<BeanDefinitionHolder> _beanDefinitionHolders = super.doScan(basePackages);
        if (_beanDefinitionHolders.isEmpty())
            return _beanDefinitionHolders;

        List<IBeanDefinitionProcessor> _beanProcessors = SimpleSpiManager.getImplLIst(IBeanDefinitionProcessor.class);

        Set<String> _interfaceBeanNames = new HashSet<>();
        Map<String, BeanDefinitionHolder> _beanDefinitionHolderMap = new HashMap<>(_beanDefinitionHolders.size());
        Map<String, Class<?>> _beanClassMap = new HashMap<>(_beanDefinitionHolders.size());
        for (BeanDefinitionHolder _beanDefinitionHolder : _beanDefinitionHolders) {
            final String _beanName = _beanDefinitionHolder.getBeanName();
            final String _beanClassName = _beanDefinitionHolder.getBeanDefinition().getBeanClassName();
            Class<?> _beanClass = ClassUtils.resolveClassName(_beanClassName, ClassUtils.getDefaultClassLoader());
            _beanClassMap.put(_beanClassName, _beanClass);

            if (InternalUtils.isInterfaceBeanName(_beanName)) {
                _interfaceBeanNames.add(_beanName);
            }
            _beanDefinitionHolderMap.put(_beanName, _beanDefinitionHolder);

            for (IBeanDefinitionProcessor _processor : _beanProcessors) {
                _processor.foundBean(_beanClass);
            }
        }
        if (_interfaceBeanNames.isEmpty()) {
            return _beanDefinitionHolders;
        }

        for (String _interfaceBeanName : _interfaceBeanNames) {
            BeanDefinitionRegistry _registry = this.getRegistry();
            _registry.removeBeanDefinition(_interfaceBeanName);
            BeanDefinitionHolder _beanDefinitionHolder = _beanDefinitionHolderMap.get(_interfaceBeanName);
            _beanDefinitionHolders.remove(_beanDefinitionHolder);

            String _beanName = InternalUtils.interfaceBeanNameToBeanName(_interfaceBeanName);
            if (!_beanDefinitionHolderMap.containsKey(_beanName)) {
                BeanDefinition _beanDefinition = _beanDefinitionHolder.getBeanDefinition();
                final String _beanClassName = _beanDefinition.getBeanClassName();
                final Class<?> _beanClass = _beanClassMap.get(_beanClassName);
                String _factoryBeanClassName = null;
                for (IBeanDefinitionProcessor _processor : _beanProcessors) {
                    _factoryBeanClassName = _processor.getFactoryBeanClassName(_beanClass);
                    if (null != _factoryBeanClassName)
                        break;
                }
                if (null == _factoryBeanClassName)
                    throw new IllegalStateException(_beanClassName + " unsupported!");
                _beanDefinition.setBeanClassName(_factoryBeanClassName);
                _beanDefinition.getPropertyValues().add("interfaceClass", _beanClassName);
                BeanDefinitionHolder _newDefinitionHolder = new BeanDefinitionHolder(_beanDefinition, _beanName, _beanDefinitionHolder.getAliases());
                _beanDefinitionHolders.add(_newDefinitionHolder);
                registerBeanDefinition(_newDefinitionHolder, _registry);
                if (logger.isDebugEnabled()) {
                    logger.debug(_beanClassName + " adapter " + _factoryBeanClassName);
                }
            }
        }

        return _beanDefinitionHolders;
    }
}
