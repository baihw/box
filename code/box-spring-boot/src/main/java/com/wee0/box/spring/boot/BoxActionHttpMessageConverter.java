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

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.IoUtils;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/23 8:12
 * @Description 自定义消息转换器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxActionHttpMessageConverter extends MappingJackson2HttpMessageConverter {

    // 默认的回调参数名称
    private static final String DEF_CALLBACK_NAME = "callback";

    BoxActionHttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    protected void writeInternal(Object object, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        ServletRequestAttributes _requestAttributes = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes());
        HttpServletRequest _request = _requestAttributes.getRequest();
        String _callback = CheckUtils.checkTrimEmpty(_request.getParameter(DEF_CALLBACK_NAME), null);
        if (null == _callback) {
            super.writeInternal(object, outputMessage);
        } else {
            JsonEncoding _jsonEncoding = getJsonEncoding(outputMessage.getHeaders().getContentType());
            try {
                _requestAttributes.getResponse().setContentType("application/javascript");
//                _requestAttributes.getResponse().setContentType("application/javascript;charset=UTF-8");
                String _callbackResult = _callback + "(" + super.getObjectMapper().writeValueAsString(object) + ");";
                IoUtils.impl().write(_callbackResult, outputMessage.getBody(), _jsonEncoding.getJavaName());
            } catch (JsonProcessingException ex) {
                throw new HttpMessageNotWritableException("Could not write JSON: " + ex.getMessage(), ex);
            }
        }
//        super.writeInternal(object, outputMessage);
    }
}
