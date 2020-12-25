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
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.JsonUtils;
import com.wee0.box.web.IActionFilter;
import com.wee0.box.web.IActionRequest;
import com.wee0.box.web.IActionResponse;
import com.wee0.box.web.servlet.impl.HttpServletActionRequest;
import com.wee0.box.web.servlet.impl.HttpServletActionResponse;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/22 15:12
 * @Description 自定义过滤器拦截器
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxActionFilterInterceptor implements HandlerInterceptor {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxActionFilterInterceptor.class);

    // 过滤器集合
    private final List<IActionFilter> FILTERS;

    BoxActionFilterInterceptor(String filterNames) {
        this.FILTERS = new ArrayList<>(8);
        if (null != filterNames) {
            String[] _names = filterNames.split(",");
            for (String _name : _names) {
                if (null == _name || 0 == (_name = _name.trim()).length())
                    continue;
                IActionFilter _filter = BoxConfig.createInstance(_name, IActionFilter.class);
                this.FILTERS.add(_filter);
                log.trace("add filter, name: {}, instance: {}.", _name, _filter);
            }
        }
        log.debug("filters: {}", this.FILTERS);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return true;
        if (this.FILTERS.isEmpty())
            return true;

        IActionRequest _request = new HttpServletActionRequest(request);
        IActionResponse _response = new HttpServletActionResponse(response);

        for (IActionFilter _filter : this.FILTERS) {
            if (_filter.doFilter(_request, _response)) {
                renderJson(response, _response);
                return false;
            }
        }
        return true;
    }

    // 渲染错误信息
    private static void renderJson(HttpServletResponse response, IActionResponse result) {
        response.setStatus(result.getStatus());
        final String _ENCODING = BoxConfig.impl().getEncoding();
        response.setCharacterEncoding(_ENCODING);
        response.setContentType("application/json; charset=" + _ENCODING);
        try (PrintWriter writer = response.getWriter();) {
            writer.print(JsonUtils.writeToString(result.getData()));
        } catch (IOException e) {
            log.error("render error", e);
        }
    }
}
