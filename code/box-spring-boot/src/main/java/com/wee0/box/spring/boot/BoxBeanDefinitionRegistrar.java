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

package com.wee0.box.spring.boot;

import com.wee0.box.BoxConfig;
import com.wee0.box.impl.BoxConfigKeys;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.spring.BoxBeanDefinitionScanner;
import com.wee0.box.spring.BoxBeanNameGenerator;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:22
 * @Description 自定义组件注册
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware, ResourceLoaderAware {

    private static final ILogger log = LoggerFactory.getLogger(BoxBeanDefinitionRegistrar.class);

    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;

    // 默认的扫描包
    private static final String DEF_SCAN_PACKAGES = "com.wee0.box";

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        log.trace("beanFactory:{}.", beanFactory);
        this.beanFactory = beanFactory;
    }

    @Override
    public void setResourceLoader(ResourceLoader resourceLoader) {
        log.trace("resourceLoader:{}.", resourceLoader);
        this.resourceLoader = resourceLoader;
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        log.trace("importingClassMetadata:{}, registry:{}", importingClassMetadata, registry);
        String[] _packages = null;
        if (AutoConfigurationPackages.has(this.beanFactory)) {
            List<String> _packageList = AutoConfigurationPackages.get(this.beanFactory);
            _packages = StringUtils.toStringArray(_packageList);
        } else {
//            log.debug("Could not determine auto-configuration package, automatic scanning disabled.");
            String _packagesString = BoxConfig.impl().get(BoxConfigKeys.scanBasePackage, DEF_SCAN_PACKAGES);
            _packages = StringUtils.tokenizeToStringArray(_packagesString, ConfigurableApplicationContext.CONFIG_LOCATION_DELIMITERS);
        }
        log.trace("scan base packages: {}", Arrays.toString(_packages));


        BoxBeanDefinitionScanner _scanner = new BoxBeanDefinitionScanner(registry);
        // 自定义的名称生成器
        BeanNameGenerator _beanNameGenerator = new BoxBeanNameGenerator(_scanner.getMetadataReaderFactory());
        _scanner.setBeanNameGenerator(_beanNameGenerator);
        _scanner.registerFilters();
        int _count = _scanner.scan(_packages);
        log.debug("scan beans from {}, count:{}", _packages, _count);
    }

}
