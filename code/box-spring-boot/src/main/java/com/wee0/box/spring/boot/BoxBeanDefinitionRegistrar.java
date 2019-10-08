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
import com.wee0.box.sql.ds.DsManager;
import com.wee0.box.sql.ds.IDsManager;
import com.wee0.box.sql.ds.impl.SimpleDsProperty;
import com.wee0.box.util.shortcut.CheckUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.couchbase.CouchbaseProperties;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.jndi.JndiObjectFactoryBean;
import org.springframework.util.StringUtils;

import javax.naming.NamingException;
import javax.sql.DataSource;
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
public class BoxBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar, BeanFactoryAware, ResourceLoaderAware, EnvironmentAware {

    private static final ILogger log = LoggerFactory.getLogger(BoxBeanDefinitionRegistrar.class);

    private BeanFactory beanFactory;
    private ResourceLoader resourceLoader;
//    private Environment environment;

    // 默认的扫描包
    private static final String DEF_SCAN_PACKAGES = "com.wee0.box";

    BoxBeanDefinitionRegistrar() {
        log.debug("BoxBeanDefinitionRegistrar...");
    }

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
    public void setEnvironment(Environment environment) {
        log.trace("environment: {}", environment);
        String _dsEnable = environment.getProperty("box.ds.enable");
        if ("false".equals(_dsEnable)) {
            return;
        }
        initDsManager(environment);
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

    /**
     * 初始化数据源管理器
     */
    private void initDsManager(Environment environment) {
        DataSource _defaultDataSource = null;
        Binder _binder = Binder.get(environment);
        BindResult<SimpleJndiProperty> _jndiBindResult = _binder.bind("box.jndi", Bindable.of(SimpleJndiProperty.class));
        if (_jndiBindResult.isBound()) {
            SimpleJndiProperty _jndiProperty = _jndiBindResult.get();
            if (_jndiProperty.isActive()) {
                _defaultDataSource = createJndiDataSource(_jndiProperty);
                DsManager.impl().addDataSource(IDsManager.DEF_DS_NAME, _defaultDataSource);
            }
        }

        if (null == _defaultDataSource) {
            BindResult<SimpleDsProperty> _dsBindResult = _binder.bind("box.ds", Bindable.of(SimpleDsProperty.class));
            if (_dsBindResult.isBound()) {
                SimpleDsProperty _dsProperty = _dsBindResult.get();
                DsManager.impl().addDataSourceByProperty(_dsProperty);
            }
        }

        DsManager.impl().init();
    }

//    /**
//     * @return 标准数据源
//     */
//    private DataSource createStandardDataSource(IDsProperty dsProperty) {
//        if (!StringUtils.hasLength(dsProperty.getUrl())) {
//            return null;
//        }
//        DsManager.impl().addDataSourceByProperty(dsProperty);
//        DsManager.impl().init();
//        DataSource _ds = DsManager.impl().getDefaultDataSource();
//        return _ds;
//    }

    /**
     * @return jndi数据源
     */
    private DataSource createJndiDataSource(SimpleJndiProperty jndiProperty) {
        // 根据配置信息，创建一个默认的数据源提供者实例。
        if (!jndiProperty.isActive()) {
            return null;
        }
        // 如果配置了jndi，并且active为true，则使用jndi数据源。
        JndiObjectFactoryBean bean = new JndiObjectFactoryBean();
        bean.setJndiName(CheckUtils.checkNotTrimEmpty(jndiProperty.getName(), "jndi.name can not be empty!"));
        bean.setProxyInterface(DataSource.class);
        bean.setLookupOnStartup(false);
        bean.setResourceRef(true);
        try {
            bean.afterPropertiesSet();
        } catch (IllegalArgumentException | NamingException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
        DataSource _ds = (DataSource) bean.getObject();
        return _ds;
    }

    // 一个简单的JNDI属性配置对象
    static final class SimpleJndiProperty {

        private String name;
        private boolean active;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }

        @Override
        public String toString() {
            final StringBuilder sb = new StringBuilder("SimpleJndiProperty{");
            sb.append("name='").append(name).append('\'');
            sb.append(", active=").append(active);
            sb.append('}');
            return sb.toString();
        }
    }

}
