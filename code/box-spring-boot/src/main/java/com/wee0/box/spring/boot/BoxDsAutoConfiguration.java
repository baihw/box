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

import com.alibaba.druid.pool.DruidDataSource;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.ds.DsManager;
import com.wee0.box.sql.ds.IDsManager;
import com.wee0.box.sql.ds.impl.SimpleDsProperty;
import com.wee0.box.util.shortcut.CheckUtils;
import org.apache.ibatis.builder.xml.XMLConfigBuilder;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.bind.BindResult;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.core.env.Environment;
import org.springframework.jndi.JndiObjectFactoryBean;

import javax.naming.NamingException;
import javax.sql.DataSource;
import java.io.InputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:21
 * @Description 数据源自动配置对象
 * <pre>
 * 补充说明
 * </pre>
 **/
@Order(Ordered.HIGHEST_PRECEDENCE)
@ConditionalOnClass(DruidDataSource.class)
@org.springframework.context.annotation.Configuration
class BoxDsAutoConfiguration {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxDsAutoConfiguration.class);

//    @Autowired
//    private Environment environment;

    BoxDsAutoConfiguration(){
        log.debug("BoxDsAutoConfiguration...");
    }

    @Bean(name = "datasource")
    @ConditionalOnProperty(name = "box.ds.enable", havingValue = "true", matchIfMissing = true)
    @ConditionalOnMissingBean(DataSource.class)
    public DataSource dataSource() {
        return DsManager.impl().getDefaultDataSource();
    }

//    @Bean
//    @ConditionalOnProperty(name = "box.mybatis.enable", havingValue = "true", matchIfMissing = true)
//    @ConditionalOnClass(SqlSessionFactory.class)
//    public SqlSessionFactory sqlSessionFactory() {
//        InputStream _inputStream = BoxDsAutoConfiguration.class.getResourceAsStream("/mybatis/mybatis-config.xml");
//        XMLConfigBuilder _configBuilder = new XMLConfigBuilder(_inputStream);
//        Configuration _configuration = _configBuilder.getConfiguration();
//        SqlSessionFactory _sqlSessionFactory = new SqlSessionFactoryBuilder().build(_configuration);
//        return _sqlSessionFactory;
//    }

//    @Override
//    public void setEnvironment(Environment environment) {
//        Binder _binder = Binder.get(environment);
//        SimpleJndiProperty _jndiProperty = _binder.bind("box.jndi", Bindable.of(SimpleJndiProperty.class)).get();
//        SimpleDsProperty _dsProperty = _binder.bind("box.ds", Bindable.of(SimpleDsProperty.class)).get();
//        if (StringUtils.hasLength(_dsProperty.getUrl()) || _jndiProperty.isActive()) {
//            System.setProperty("box.ds.enable", "true");
//            log.info("box.ds.enable={}", System.getProperty("box.ds.enable"));
//        }
//    }



}
