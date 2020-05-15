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

import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.StringUtils;
import com.wee0.box.web.IActionRequest;
import com.wee0.box.web.IActionResponse;
import com.wee0.box.web.annotation.BoxAction;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.shortcut.JsonUtils;
import com.wee0.box.web.servlet.impl.HttpServletActionRequest;
import com.wee0.box.web.servlet.impl.HttpServletActionResponse;
import org.springframework.beans.BeanUtils;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.text.NumberFormat;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:26
 * @Description 参数处理
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxActionMethodArgumentResolver implements HandlerMethodArgumentResolver {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxActionMethodArgumentResolver.class);

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        if (null == parameter)
            return false;
        if (null != AnnotationUtils.findAnnotation(parameter.getDeclaringClass(), BoxAction.class)) {
            return true;
        }
        return false;
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        if (null == parameter)
            return null;
//        BoxActionParam _param = parameter.getParameterAnnotation(BoxActionParam.class);
//        final String _parameterName = parameter.getParameterName();
        Class<?> _parameterType = parameter.getParameterType();
//        Class<?> _parameterType1 = parameter.getNestedParameterType();
//        final String _parameterName = InternalUtils.convertParameterName(_parameterType, parameter.getParameterIndex());
        if (HttpServletRequest.class.isAssignableFrom(_parameterType)) {
            return webRequest.getNativeRequest(HttpServletRequest.class);
        }
        if (HttpServletResponse.class.isAssignableFrom(_parameterType)) {
            return webRequest.getNativeResponse(HttpServletResponse.class);
        }
        if (IActionRequest.class.isAssignableFrom(_parameterType)) {
            HttpServletRequest _request = webRequest.getNativeRequest(HttpServletRequest.class);
            return new HttpServletActionRequest(_request);
        }
        if (IActionResponse.class.isAssignableFrom(_parameterType)) {
            HttpServletResponse _response = webRequest.getNativeResponse(HttpServletResponse.class);
            return new HttpServletActionResponse(_response);
        }

        // 参数值获取
        final String _parameterName = parameter.getParameterName();
        if (null == _parameterName)
            return null;
        String _parameterValue = CheckUtils.checkTrimEmpty(webRequest.getParameter(_parameterName), null);
        if (null == _parameterValue)
            return null;
        if (BeanUtils.isSimpleProperty(_parameterType)) {
            return convertStringPrimitive(_parameterValue, _parameterType);
        }
        return JsonUtils.readToObject(_parameterValue, _parameterType);

//        if (Map.class.isAssignableFrom(_parameterType)) {
//            Map<String, Object> _resultMap = new HashMap<>();
//            Iterator<String> _names = webRequest.getParameterNames();
//            while (_names.hasNext()) {
//                String _name = _names.next();
//                String[] _values = webRequest.getParameterValues(_name);
//                if (null == _values)
//                    continue;
//                if (1 == _values.length) {
//                    String _value = _values[0];
//                    if (null == _value)
//                        continue;
//                    _resultMap.put(_name, _value);
//                } else {
//                    _resultMap.put(_name, _values);
//                }
//            }
//            return _resultMap;
//        }

    }

    // 转换字符串为基础数据类型
    static Object convertStringPrimitive(String str, Class primitiveType) {
        if (null == str)
            return null;
        if (String.class == primitiveType)
            return str;
        if (Boolean.class == primitiveType || boolean.class == primitiveType)
            return Boolean.valueOf(str);
        if (Short.class == primitiveType || short.class == primitiveType)
            return Short.valueOf(str);
        if (Integer.class == primitiveType || int.class == primitiveType)
            return Integer.valueOf(str);
        if (Long.class == primitiveType || long.class == primitiveType)
            return Long.valueOf(str);
        if (Float.class == primitiveType || float.class == primitiveType)
            return Float.valueOf(str);
        if (Double.class == primitiveType || double.class == primitiveType || Number.class == primitiveType)
            return Double.valueOf(str);
        if (Byte.class == primitiveType || byte.class == primitiveType)
            return Byte.valueOf(str);
//        throw new IllegalStateException("unSupport type:" + primitiveType);
        return str;
    }
}
