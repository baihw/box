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

import com.wee0.box.BoxConfig;
import com.wee0.box.impl.BoxConfigKeys;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.core.type.filter.TypeFilter;
import org.springframework.util.StringUtils;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:09
 * @Description 组件加载
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxBeanDefinitionPostProcessor implements BeanDefinitionRegistryPostProcessor, ApplicationContextAware {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxBeanDefinitionPostProcessor.class);

    private static final String DEF_SCAN_PACKAGES = "com.wee0.box"; // "com.wee0.box,com.wee0.test"

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        String _packages = BoxConfig.impl().get(BoxConfigKeys.scanBasePackage, DEF_SCAN_PACKAGES);
        log.debug("scan packages: {}", _packages);
        String[] _packageArr = StringUtils.tokenizeToStringArray(_packages, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);

        List<TypeFilter> _typeFilters = new ArrayList<>(3);
        for (Class<? extends Annotation> annotationClass : InternalUtils.AUTO_ANNOTATIONS) {
            _typeFilters.add(new AnnotationTypeFilter(annotationClass, false, true));
        }
        BoxBeanDefinitionScanner _scanner = new BoxBeanDefinitionScanner(registry);
        _scanner.registerFilters(_typeFilters);
        // 自定义的名称生成器
        BeanNameGenerator _beanNameGenerator = new BoxBeanNameGenerator(_scanner.getMetadataReaderFactory());
        _scanner.setBeanNameGenerator(_beanNameGenerator);
        int _count = _scanner.scan(_packageArr);
        log.debug("scan bean from {}, count:{}", _packageArr, _count);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        log.trace("beanFactory:{}", beanFactory);
        log.debug("beanDefinitionCount:{}", beanFactory.getBeanDefinitionCount());
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        log.trace("applicationContext:{}", applicationContext);
//        SpringBoxContext.me().setSpringContext(applicationContext);
//        SpringBoxContext.me().init();
//        ((ConfigurableApplicationContext) applicationContext).getBeanFactory().registerSingleton("boxContext", SpringBoxContext.me());
    }

}
