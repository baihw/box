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
import com.wee0.box.subject.IServletRequestSignChecker;
import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.SubjectContext;
import com.wee0.box.subject.annotation.*;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.DateUtils;
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

    // 默认的签名校验者对象
    private static final String DEF_SIGN_CHECKER = "com.wee0.box.subject.impl.SimpleServletRequestSignChecker";
    // 默认的签名密钥
    private static final String DEF_SIGN_SECRET_KEY = "aW8Kd5Qe56db11eIa29002D2aG12010w";

    private final String ignoreUrls;
    private final String servletRequestSignCheckerName;
    private final String signSecretKey;
//    private final String cookieDomain;

    //  签名检查者
    private final IServletRequestSignChecker signChecker;

    BoxActionHandlerInterceptor(String ignoreUrls, String signSecretKey, String servletRequestSignCheckerName) {
        ignoreUrls = CheckUtils.checkNotTrimEmpty(ignoreUrls, "ignoreUrls can't be empty!");
        ignoreUrls = StringUtils.endsWithChar(ignoreUrls, ',');
        this.ignoreUrls = ignoreUrls;
//        this.cookieDomain = CheckUtils.checkTrimEmpty(cookieDomain, null);
//        log.debug("cookieDomain: {}, ignoreUrls: {}", this.cookieDomain, this.ignoreUrls);
        log.debug("ignoreUrls: {}", this.ignoreUrls);

//        this.cookieDomain = cookieDomain;
//        log.debug("cookieDomain: {}", this.cookieDomain);

        this.signSecretKey = CheckUtils.checkTrimEmpty(signSecretKey, DEF_SIGN_SECRET_KEY);
        log.debug("signSecretKey: {}", this.signSecretKey);

        this.servletRequestSignCheckerName = CheckUtils.checkTrimEmpty(servletRequestSignCheckerName, DEF_SIGN_CHECKER);
        log.debug("servletRequestSignCheckerName: {}", this.servletRequestSignCheckerName);
        this.signChecker = BoxConfig.createInstance(this.servletRequestSignCheckerName, IServletRequestSignChecker.class);
        this.signChecker.setSecretKey(this.signSecretKey);
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof ResourceHttpRequestHandler || handler instanceof ParameterizableViewController)
            return true;

        // 使用自己的日期时间工具类返回响应头中的日期
        response.addHeader("Date", DateUtils.getCurrentDateTimeGMT());

        // 初始化当前会话主体对象
//        ISubject _subject = SubjectContext.getSubject(request, response);

        if (!(handler instanceof HandlerMethod)) {
            log.warn("unKnow method: {}", handler);
            return true;
        }
        HandlerMethod _handlerMethod = (HandlerMethod) handler;

        // 判断请求的资源是否允许匿名访问
        String _uri = request.getRequestURI();
        if (-1 != this.ignoreUrls.indexOf(_uri + ',') || _handlerMethod.hasMethodAnnotation(BoxRequireIgnore.class)) {
            // 不需要权限检查
            return true;
        }

        if (_handlerMethod.hasMethodAnnotation(BoxRequireSign.class)) {
            // 签名检查
            if (this.signChecker.check(request))
                return true;
            renderJson(response, CmdFactory.create(BoxConfig.impl().getConfigObject().getSignErrorBizCode()));
            return false;
        }

        // 以下情况都需要登陆，判断当前用户是否已经登陆。
        ISubject _subject = SubjectContext.getSubject(request, response);
        if (null == _subject || !_subject.isLogin()) {
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

//    // 检查请求参数
//    private boolean checkParameters(HandlerMethod method, HttpServletRequest request, HttpServletResponse response) {
////        LocalVariableTableParameterNameDiscoverer _nameDiscoverer = BoxContext.impl().getBean(LocalVariableTableParameterNameDiscoverer.class);
//        // 参数校验
//        MethodParameter[] _methodParameters = method.getMethodParameters();
//        if (null == _methodParameters || 0 == _methodParameters.length)
//            return true;
//        for (MethodParameter _methodParameter : _methodParameters) {
//            if (_methodParameter.hasParameterAnnotation(BoxParam.class)) {
//                BoxParam _param = _methodParameter.getParameterAnnotation(BoxParam.class);
//                String _paramName = _methodParameter.getParameterName();
//                String _paramVal = request.getParameter(_paramName);
//                boolean _validateResult = ValidateUtils.impl().validatePattern(_param.pattern(), _paramVal, _param.allowNull(), false);
//                if (_validateResult) return true;
//                int _resCode = BoxConfig.impl().getConfigObject().getParamsExceptionStatusCode();
//                renderJson(response, CmdFactory.create(String.valueOf(_resCode), _param.message()), _resCode);
//                return false;
//            }
//        }
//        return true;
//    }

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
        renderJson(response, data, BoxConfig.impl().getConfigObject().getPermissionExceptionHttpStatusCode());
    }

    // 渲染错误信息
    private static void renderJson(HttpServletResponse response, Object data, int status) {
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
