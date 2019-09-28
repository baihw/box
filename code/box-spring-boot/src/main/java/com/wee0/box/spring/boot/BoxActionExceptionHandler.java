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

import com.wee0.box.exception.BizException;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 23:11
 * @Description 自定义异常处理对象
 * <pre>
 * 补充说明
 * </pre>
 **/
@ControllerAdvice
final class BoxActionExceptionHandler extends ResponseEntityExceptionHandler {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxActionExceptionHandler.class);

    // 异常格式化文本模板
    private static final String exceptionFormat = "BoxActionExceptionController: Code: %s Msg: %s";

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        log.debug("initBinder:{}.", binder);
    }

    @ModelAttribute
    public void addAttributes(Model model) {
        log.debug("model:{}.", model);
        model.addAttribute("a1", "a1v");
    }

    @ExceptionHandler({BoxRuntimeException.class})
    @ResponseBody
    public Map<String, Object> exception(BoxRuntimeException ex) {
        return exceptionFormat(ex.getCode(), ex);
    }

    @ExceptionHandler({BizException.class})
    @ResponseBody
    public Map<String, Object> exception(BizException ex) {
        return exceptionFormat(500, ex.getBizCodeInfo().formatText());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(code = HttpStatus.BAD_REQUEST)
    public Map<String, Object> exception(MethodArgumentNotValidException ex) {
        FieldError _fieldError = ex.getBindingResult().getFieldError();
        return exceptionFormat(501, _fieldError.getDefaultMessage());
    }

    @ExceptionHandler(BindException.class)
    @ResponseBody
    public Map<String, Object> exception(BindException ex) {
        FieldError _fieldError = ex.getBindingResult().getFieldError();
        return exceptionFormat(9, _fieldError.getDefaultMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseBody
    public Map<String, Object> exception(Exception ex) {
        return exceptionFormat(502, ex);
    }

    private <T extends Throwable> Map<String, Object> exceptionFormat(Integer code, T ex) {
        return exceptionFormat(code, ex.getMessage());
    }

    private <T extends Throwable> Map<String, Object> exceptionFormat(Integer code, String msg) {
        log.error(String.format(exceptionFormat, code, msg));
        Map<String, Object> map = new HashMap<>();
        map.put("code", code);
        map.put("msg", msg);
        return map;
    }

}
