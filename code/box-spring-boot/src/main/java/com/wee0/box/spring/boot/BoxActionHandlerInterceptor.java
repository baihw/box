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
import com.wee0.box.struct.CmdFactory;
import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.SubjectContext;
import com.wee0.box.subject.annotation.BoxRequireIgnore;
import com.wee0.box.subject.annotation.BoxRequireLogical;
import com.wee0.box.subject.annotation.BoxRequirePermissions;
import com.wee0.box.subject.annotation.BoxRequireRoles;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.JsonUtils;
import com.wee0.box.util.shortcut.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.ParameterizableViewController;
import org.springframework.web.servlet.resource.ResourceHttpRequestHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

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
//    private final String cookieDomain;

    BoxActionHandlerInterceptor(String ignoreUrls) {
        ignoreUrls = CheckUtils.checkNotTrimEmpty(ignoreUrls, "ignoreUrls can't be empty!");
        ignoreUrls = StringUtils.endsWithChar(ignoreUrls, ',');
        this.ignoreUrls = ignoreUrls;
//        this.cookieDomain = CheckUtils.checkTrimEmpty(cookieDomain, null);
//        log.debug("cookieDomain: {}, ignoreUrls: {}", this.cookieDomain, this.ignoreUrls);
        log.debug("ignoreUrls: {}", this.ignoreUrls);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return true;

        // 初始化当前会话主体对象
        ISubject _subject = SubjectContext.getSubject(request, response);

        // 判断请求的资源是否允许匿名访问
        String _uri = request.getRequestURI();
        if (-1 != this.ignoreUrls.indexOf(_uri + ',')) {
            return true;
        }

        if (handler instanceof HandlerMethod) {
            HandlerMethod _handlerMethod = (HandlerMethod) handler;
            if (_handlerMethod.hasMethodAnnotation(BoxRequireIgnore.class)) {
                // 不需要权限检查
                return true;
            }

            // 判断当前用户是否已经登陆
            if (!_subject.isLogin()) {
                log.debug("session {} is not login.", _subject.getSessionId());
                renderJson(response, CmdFactory.create(BoxConfig.impl().getConfigObject().getNeedLoginBizCode()));
                return false;
            }

            // 角色检查
            BoxRequireRoles _requireRoles = _handlerMethod.getMethodAnnotation(BoxRequireRoles.class);
            if (null != _requireRoles) {
                String[] _roles = _requireRoles.value();
                BoxRequireLogical _logical = _requireRoles.logical();
                if (!checkRoles(_subject, _roles, _logical)) {
                    renderJson(response, CmdFactory.create(BoxConfig.impl().getConfigObject().getUnauthorizedBizCode()));
                    return false;
                }
            }

            // 权限检查
            BoxRequirePermissions _requirePermissions = _handlerMethod.getMethodAnnotation(BoxRequirePermissions.class);
            if (null != _requirePermissions) {
                String[] _permissions = _requirePermissions.value();
                BoxRequireLogical _logical = _requirePermissions.logical();
                if (!checkPermissions(_subject, _permissions, _logical)) {
                    renderJson(response, CmdFactory.create(BoxConfig.impl().getConfigObject().getUnauthorizedBizCode()));
                    return false;
                }
            }

        } else {
            log.warn("unKnow method: {}", handler);
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return;
//        log.trace("handler: {}, modelAndView: {}", handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return;
//        log.trace("handler: {}", handler);
    }

    // 检查指定角色的逻辑关系是否成立
    private static boolean checkRoles(ISubject subject, String[] roles, BoxRequireLogical logical) {
        if (null == roles || 0 == roles.length)
            return true;
        if (BoxRequireLogical.OR == logical) {
            for (String _role : roles) {
                if (subject.hasRole(_role))
                    return true;
            }
            return false;
        }
        // 默认为 AND
        for (String _role : roles) {
            if (!subject.hasRole(_role)) {
                log.debug("fail on role: {}", _role);
                return false;
            }
        }
        return true;
    }

    // 检查指定权限的逻辑关系是否成立
    private static boolean checkPermissions(ISubject subject, String[] permissions, BoxRequireLogical logical) {
        if (null == permissions || 0 == permissions.length)
            return true;
        if (BoxRequireLogical.OR == logical) {
            for (String _permission : permissions) {
                if (subject.hasPermission(_permission))
                    return true;
            }
            return false;
        }
        // 默认为 AND
        for (String _permission : permissions) {
            if (!subject.hasPermission(_permission)) {
                log.debug("fail on permission: {}", _permission);
                return false;
            }
        }
        return true;
    }

    // 渲染错误信息
    private static void renderJson(HttpServletResponse response, Object data) {
//        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.setStatus(BoxConfig.impl().getConfigObject().getPermissionExceptionHttpStatusCode());
        response.setCharacterEncoding(BoxConfig.impl().getEncoding());
        response.setContentType("application/json; charset=" + BoxConfig.impl().getEncoding());
        try (PrintWriter writer = response.getWriter();) {
            writer.print(JsonUtils.writeToString(data));
        } catch (IOException e) {
            log.error("render error", e);
        }
    }
}
