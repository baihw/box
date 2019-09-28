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

import com.wee0.box.beans.annotation.BoxAction;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:25
 * @Description 请求映射处理对象
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxActionRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxActionRequestMappingHandlerMapping.class);

    // 映射构建配置对象
    private RequestMappingInfo.BuilderConfiguration builderConfiguration = new RequestMappingInfo.BuilderConfiguration();

    // 是否开启自动映射
    private boolean isEnable = true;

    // 默认支持的请求方法
    private static final RequestMethod[] DEF_METHODS = {RequestMethod.POST, RequestMethod.GET};
    // 默认的请求前缀
    static final String DEF_PREFIX = "/api";

    @Override
    public void afterPropertiesSet() {
        super.afterPropertiesSet();

        this.builderConfiguration = new RequestMappingInfo.BuilderConfiguration();
        this.builderConfiguration.setUrlPathHelper(getUrlPathHelper());
        this.builderConfiguration.setPathMatcher(getPathMatcher());
        this.builderConfiguration.setSuffixPatternMatch(useSuffixPatternMatch());
        this.builderConfiguration.setTrailingSlashMatch(useTrailingSlashMatch());
        this.builderConfiguration.setRegisteredSuffixPatternMatch(useRegisteredSuffixPatternMatch());
        this.builderConfiguration.setContentNegotiationManager(getContentNegotiationManager());
    }

    @Override
    protected boolean isHandler(Class<?> beanType) {
        if (isEnable && AnnotatedElementUtils.hasAnnotation(beanType, BoxAction.class))
            return true;
        return super.isHandler(beanType);
    }

//    @Override
//    protected RequestCondition<?> getCustomMethodCondition(Method method) {
//        return super.getCustomMethodCondition(method);
//    }

//    @Override
//    protected void detectHandlerMethods(Object handler) {
//        Class<?> _handlerType = (handler instanceof String ? getApplicationContext().getType((String) handler) : handler.getClass());
//        final Class<?> _handlerClass = ClassUtils.getUserClass(_handlerType);
//        BoxAction _boxAction = AnnotatedElementUtils.findMergedAnnotation(_handlerClass, BoxAction.class);
//        if (null == _boxAction) {
//            super.detectHandlerMethods(handler);
//            return;
//        }
//
//        final Map<Method, RequestMappingInfo> _methodMap = new LinkedHashMap<Method, RequestMappingInfo>();
//        ReflectionUtils.doWithMethods(_handlerClass, new ReflectionUtils.MethodCallback() {
//            @Override
//            public void doWith(Method method) throws IllegalArgumentException, IllegalAccessException {
//                Method _specificMethod = ClassUtils.getMostSpecificMethod(method, _handlerClass);
//                if (Modifier.isPublic(_specificMethod.getModifiers())) {
//                    RequestMappingInfo _mappingInfo = createRequestMappingInfo(_handlerClass, _specificMethod);
//                    if (null != _mappingInfo) {
//                        Method _bridgedMethod = BridgeMethodResolver.findBridgedMethod(_specificMethod);
//                        if (_bridgedMethod == _specificMethod || null == createRequestMappingInfo(_handlerClass, _bridgedMethod)) {
//                            _methodMap.put(_specificMethod, _mappingInfo);
//                        }
//                    }
//                }
//            }
//        }, ReflectionUtils.USER_DECLARED_METHODS);
//
//        log.debug("{} request handler methods found on {}: {}", _methodMap.size(), _handlerClass, _methodMap);
//        for (Map.Entry<Method, RequestMappingInfo> _entry : _methodMap.entrySet()) {
//            Method _method = AopUtils.selectInvocableMethod(_entry.getKey(), _handlerClass);
//            RequestMappingInfo _mapping = _entry.getValue();
//            registerHandlerMethod(handler, _method, _mapping);
//        }
//    }


    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        if (AnnotatedElementUtils.hasAnnotation(handlerType, BoxAction.class)) {
            if (Modifier.isPublic(method.getModifiers()))
                return createRequestMappingInfo(handlerType, method);
        }
        return super.getMappingForMethod(method, handlerType);
    }

    /**
     * 根据类型与方法生成请求映射信息对象
     *
     * @param type   服务类型
     * @param method 服务方法
     * @return 映射信息对象
     */
    private RequestMappingInfo createRequestMappingInfo(Class<?> type, Method method) {
        String _mappingPath = generateUri(type, method);
        RequestMappingInfo.Builder _builder = RequestMappingInfo.paths(_mappingPath);
        _builder.methods(DEF_METHODS);
//        _builder.produces("application/json;charset=UTF-8");
        _builder.options(builderConfiguration);
//        _builder.mappingName("mappingName1");
        return _builder.build();
    }

    /**
     * 根据类型方法生成URI路径
     *
     * @param type   类型
     * @param method 方法
     * @return 生成的URI路径
     */
    static String generateUri(Class<?> type, Method method) {
        StringBuilder _sb = new StringBuilder(128);
        _sb.append(DEF_PREFIX);
        BoxAction _boxAction = AnnotatedElementUtils.findMergedAnnotation(type, BoxAction.class);
        String _pathPart = null;
        if (null != _boxAction && 0 != (_pathPart = _boxAction.value().trim()).length()) {
            if ('/' != _pathPart.charAt(0))
                _sb.append('/');
            _sb.append(_pathPart);
            if ('/' == _pathPart.charAt(_pathPart.length() - 1))
                _sb.deleteCharAt(_sb.length() - 1);
        } else {
            String _typeName = type.getName();
            int _ndx = _typeName.indexOf(".action.");
            if (-1 != _ndx) {
                _typeName = _typeName.substring(_ndx + 7);
            }
            char[] _typeNameChars = _typeName.toCharArray();
            for (int _i = 0, _iLen = _typeNameChars.length; _i < _iLen; _i++) {
                if ('.' == _typeNameChars[_i]) {
                    _sb.append('/');
                    if (_i + 1 < _iLen && Character.isUpperCase(_typeNameChars[_i + 1])) {
                        _typeNameChars[_i + 1] = Character.toLowerCase(_typeNameChars[_i + 1]);
                    }
                } else {
                    _sb.append(_typeNameChars[_i]);
                }
            }
        }

        _boxAction = AnnotatedElementUtils.findMergedAnnotation(method, BoxAction.class);
        if (null != _boxAction && 0 != (_pathPart = _boxAction.value().trim()).length()) {
            if ('/' != _pathPart.charAt(0))
                _sb.append('/');
            _sb.append(_pathPart);
            if ('/' == _pathPart.charAt(_pathPart.length() - 1))
                _sb.deleteCharAt(_sb.length() - 1);
        } else {
            _sb.append('/').append(method.getName());
        }
        return _sb.toString();
    }

}
