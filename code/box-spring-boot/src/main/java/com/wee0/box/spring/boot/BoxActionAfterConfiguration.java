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

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 23:06
 * @Description 配置完成后的处理动作
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxActionAfterConfiguration implements InitializingBean {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    private BoxActionReturnValueHandler boxActionReturnValueHandler;

    @Override
    public void afterPropertiesSet() throws Exception {
        List<HandlerMethodArgumentResolver> _oldArgumentResolvers = requestMappingHandlerAdapter.getArgumentResolvers();
        List<HandlerMethodArgumentResolver> _newArgumentResolvers = new ArrayList<>(_oldArgumentResolvers.size() + 2);
        _newArgumentResolvers.add(new BoxActionMethodArgumentResolver());
        _newArgumentResolvers.addAll(_oldArgumentResolvers);
        requestMappingHandlerAdapter.setArgumentResolvers(_newArgumentResolvers);

        List<HandlerMethodReturnValueHandler> _oldReturnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> _newReturnValueHandlers = new ArrayList<>(_oldReturnValueHandlers.size());
        for (HandlerMethodReturnValueHandler _returnValueHandler : _oldReturnValueHandlers) {
            if (_returnValueHandler instanceof RequestResponseBodyMethodProcessor) {
                boxActionReturnValueHandler.setDelegate(_returnValueHandler);
                _newReturnValueHandlers.add(boxActionReturnValueHandler);
            } else {
                _newReturnValueHandlers.add(_returnValueHandler);
            }
        }
        requestMappingHandlerAdapter.setReturnValueHandlers(_newReturnValueHandlers);
    }

    public BoxActionReturnValueHandler getBoxActionReturnValueHandler() {
        return boxActionReturnValueHandler;
    }

    public void setBoxActionReturnValueHandler(BoxActionReturnValueHandler boxActionReturnValueHandler) {
        this.boxActionReturnValueHandler = boxActionReturnValueHandler;
    }

}
