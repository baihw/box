/*
 * *
 *  * Copyright (c) 2019-present, wee0.com.
 *  *
 *  * Licensed under the Apache License, Version 2.0 (the "License");
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  *      http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *  *
 */

package com.wee0.box.spring.boot;

import com.wee0.box.BoxConfig;
import com.wee0.box.IBoxConfig;
import com.wee0.box.IBoxContext;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.spring.BoxBeanDefinitionMerge;
import com.wee0.box.spring.SpringBoxContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.PropertySource;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:18
 * @Description 框架初始化入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxInitializer implements ApplicationContextInitializer<ConfigurableApplicationContext> {

    static {
        System.setProperty(IBoxConfig.KEY_MANUAL_LOAD_CONFIG_DATA, "true");
    }

    @Override
    public void initialize(ConfigurableApplicationContext configurableApplicationContext) {
        System.out.println("context: " + configurableApplicationContext);

        // 获取spring配置数据
        Map<String, String> _springConfigData = loadSpringConfigData((AbstractEnvironment) configurableApplicationContext.getEnvironment());
        _springConfigData.put(IBoxContext.class.getName(), "com.wee0.box.spring.SpringBoxContext");

        // 加载框架配置数据
        BoxConfig.impl().loadData(_springConfigData);

        final ILogger _log = LoggerFactory.getLogger(BoxInitializer.class);

        SpringBoxContext.me().setSpringContext(configurableApplicationContext);
        SpringBoxContext.me().init();
        configurableApplicationContext.addApplicationListener(new ApplicationListener<ContextClosedEvent>() {
            @Override
            public void onApplicationEvent(ContextClosedEvent event) {
                _log.info("application closing...", event.getApplicationContext());
                if (event.getApplicationContext().getDisplayName().equals("Root WebApplicationContext")) {
                    _log.info("Root WebApplicationContext...");
                }
                SpringBoxContext.me().destroy();
            }
        });

        DefaultListableBeanFactory _beanFactory = (DefaultListableBeanFactory) configurableApplicationContext.getBeanFactory();
        _beanFactory.addBeanPostProcessor(new BoxBeanDefinitionMerge());
        _beanFactory.registerSingleton("boxContext", SpringBoxContext.me());

////        BeanDefinitionBuilder _builder1 = BeanDefinitionBuilder.genericBeanDefinition(BoxActionErrorController.class);
////        _beanFactory.registerBeanDefinition("boxActionErrorController", _builder1.getRawBeanDefinition());
//        BeanDefinitionBuilder _builder2 = BeanDefinitionBuilder.genericBeanDefinition(BoxActionExceptionHandler.class);
//        _beanFactory.registerBeanDefinition("boxActionExceptionHandler", _builder2.getRawBeanDefinition());
    }

    // 加载spring环境配置数据
    Map<String, String> loadSpringConfigData(AbstractEnvironment environment) {
        if (null == environment)
            return null;
        Map<String, String> _result = new LinkedHashMap<>(128, 1.0f);
        Iterator<PropertySource<?>> _sourceIterator = environment.getPropertySources().stream().iterator();
        while (_sourceIterator.hasNext()) {
            PropertySource _source = _sourceIterator.next();
            if (_source instanceof EnumerablePropertySource) {
                String[] _names = ((EnumerablePropertySource<?>) _source).getPropertyNames();
                for (String _name : _names) {
                    _result.put(_name, String.valueOf(_source.getProperty(_name)));
                }
            }
        }
        return _result;
    }

}
