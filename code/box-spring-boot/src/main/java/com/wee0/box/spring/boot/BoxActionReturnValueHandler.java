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
import com.wee0.box.beans.annotation.BoxIgnoreReturnValue;
import com.wee0.box.struct.CmdFactory;
import com.wee0.box.struct.IStruct;
import org.springframework.core.MethodParameter;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 22:27
 * @Description 返回值处理
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxActionReturnValueHandler implements HandlerMethodReturnValueHandler {

    private final HandlerMethodReturnValueHandler delegate;

    public BoxActionReturnValueHandler(HandlerMethodReturnValueHandler delegate) {
        this.delegate = delegate;
    }

    @Override
    public boolean supportsReturnType(MethodParameter returnType) {
        if (AnnotatedElementUtils.hasAnnotation(returnType.getContainingClass(), BoxAction.class))
            return true;
        return this.delegate.supportsReturnType(returnType);
    }

    @Override
    public void handleReturnValue(Object returnValue, MethodParameter returnType, ModelAndViewContainer mavContainer, NativeWebRequest webRequest) throws Exception {
        Object _r = null;
        if (returnValue instanceof IStruct || returnValue instanceof Map || returnValue instanceof List || null != returnType.getMethodAnnotation(BoxIgnoreReturnValue.class)) {
            _r = returnValue;
        } else {
            _r = CmdFactory.create(returnValue);
        }
        this.delegate.handleReturnValue(_r, returnType, mavContainer, webRequest);
//        this.delegate.handleReturnValue(returnValue, returnType, mavContainer, webRequest);
    }
}
