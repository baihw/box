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
import com.wee0.box.BoxConstants;
import com.wee0.box.exception.BizException;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.struct.CMD;
import com.wee0.box.struct.CmdFactory;
import com.wee0.box.util.shortcut.JsonUtils;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.MediaType;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 23:17
 * @Description 自定义异常处理对象
 * <pre>
 * 补充说明
 * </pre>
 **/
@Order(Ordered.HIGHEST_PRECEDENCE)
final class BoxActionHandlerExceptionResolver implements HandlerExceptionResolver {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxActionHandlerExceptionResolver.class);

    private static final ModelAndView EMPTY_VIEW = new ModelAndView();

    @Override
    public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
        StringBuilder _sb = new StringBuilder();
        if (ex instanceof BizException) {
            _sb.append(((BizException) ex).getBizCodeInfo().formatText());
        } else if (ex instanceof BindException) {
            List<FieldError> _errorList = ((BindException) ex).getBindingResult().getFieldErrors();
            for (FieldError _error : _errorList) {
                _sb.append(_error.getObjectName());
                _sb.append("对象的");
                _sb.append(_error.getField());
                _sb.append("字段");
                _sb.append(_error.getDefaultMessage());
            }
        } else {
            _sb.append(ex.getMessage());
        }

        CMD _result = CmdFactory.create(BoxConfig.impl().getConfigObject().getSystemErrorInfoBizCode(), _sb.toString());
        request.setAttribute("javax.servlet.error.status_code", BoxConfig.impl().getConfigObject().getBizExceptionHttpStatusCode());

        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding(BoxConstants.UTF8);
        response.setHeader("Cache-Control", "no-cache, must-revalidate");
        try {
            response.getWriter().write(JsonUtils.writeToString(_result));
        } catch (IOException e) {
            log.error("response write error：{}", e.getMessage(), e);
        }
        return EMPTY_VIEW;
    }

}
