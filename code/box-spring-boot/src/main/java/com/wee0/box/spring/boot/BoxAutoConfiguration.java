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

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:20
 * @Description 框架自动配置对象
 * <pre>
 * 补充说明
 * </pre>
 **/
@AutoConfigureAfter(BoxDsAutoConfiguration.class)
//@EnableConfigurationProperties(BoxProperty.class)
@org.springframework.context.annotation.Configuration
@Import(BoxBeanDefinitionRegistrar.class)
class BoxAutoConfiguration {
    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxAutoConfiguration.class);

    BoxAutoConfiguration() {
        log.debug("BoxAutoConfiguration...");
    }

}
