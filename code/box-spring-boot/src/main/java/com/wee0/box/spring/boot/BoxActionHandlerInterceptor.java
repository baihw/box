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
import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.SubjectContext;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Proxy;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:11
 * @Description 自定义请求拦截器
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxActionHandlerInterceptor implements HandlerInterceptor {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxActionHandlerInterceptor.class);

    private final String ignoreUrls;
    private final String cookieDomain;

    BoxActionHandlerInterceptor(String ignoreUrls, String cookieDomain) {
        ignoreUrls = CheckUtils.checkNotTrimEmpty(ignoreUrls, "ignoreUrls can't be empty!");
        ignoreUrls = StringUtils.endsWithChar(ignoreUrls, ',');
        this.ignoreUrls = ignoreUrls;
        this.cookieDomain = CheckUtils.checkTrimEmpty(cookieDomain, null);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return true;

        // 判断请求的资源是否允许匿名访问
        String _uri = request.getRequestURI();
        if (-1 != this.ignoreUrls.indexOf(_uri + ',')) {
            return true;
        }

        // 判断当前用户是否已经登陆
        String _boxId = findId(request);
        ISubject _subject = SubjectContext.getSubject(_boxId);
        if (!_subject.isLogin()) {
            log.error("please login...");
            if (!_subject.getId().equals(_boxId)) {
                response.addCookie(createIdCookie(_subject.getId(), cookieDomain));
            }
            return false;
        }

//        //:TODO 判断当前用户是否具备资源的访问权限
//        log.debug("handler: {}", handler.getClass());
//        try {
//            response.getOutputStream().write("unAuth".getBytes());
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return;
        log.debug("handler: {}, modelAndView: {}", handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return;
        log.debug("handler: {}", handler);
    }

    // 默认的客户端标识关联键名
    private static final String KEY_BOX_ID = "boxId";

    // 获取标识
    private static String findId(HttpServletRequest request) {
        String _result = CheckUtils.checkTrimEmpty(request.getParameter(KEY_BOX_ID), null);
        if (null == _result)
            _result = CheckUtils.checkTrimEmpty(request.getHeader(KEY_BOX_ID), null);
        if (null == _result) {
            for (Cookie _cookie : request.getCookies()) {
                if (KEY_BOX_ID.equals(_cookie.getName())) {
                    _result = CheckUtils.checkTrimEmpty(_cookie.getValue(), null);
                }
            }
        }
        return _result;
    }

    private static Cookie createIdCookie(String value, String domain) {
        Cookie _cookie = new Cookie(KEY_BOX_ID, value);
        if (null != domain) {
            _cookie.setDomain(domain);
        }
        _cookie.setPath("/");
        _cookie.setMaxAge(-1);
        _cookie.setHttpOnly(true);
//        _cookie.setSecure(true);
        return _cookie;
    }
}
