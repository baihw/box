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

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:31
 * @Description action组件注册对象
 * <pre>
 * 补充说明
 * </pre>
 **/
//@Configuration
//@AutoConfigureAfter(BoxActionAutoConfiguration.class)
class BoxActionRegistrationsAdapter implements WebMvcRegistrations {

    // 自定义映射处理器
    private RequestMappingHandlerMapping handlerMapping = new BoxActionRequestMappingHandlerMapping();

    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return this.handlerMapping;
    }

}
