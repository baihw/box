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
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.struct.CMD;
import com.wee0.box.struct.CmdFactory;
import com.wee0.box.util.shortcut.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.ServletWebRequest;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.View;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 23:10
 * @Description 自定义错误处理对象
 * <pre>
 * 补充说明
 * </pre>
 **/
@Controller
@RequestMapping({"${server.error.path:${error.path:/error}}"})
final class BoxActionErrorController implements ErrorController {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxActionErrorController.class);

    @Autowired
    private ErrorAttributes errorAttributes;

    @Autowired
    private ServerProperties serverProperties;

    public BoxActionErrorController() {
    }

    @Override
    public String getErrorPath() {
        return this.serverProperties.getError().getPath();
    }

    @RequestMapping(produces = "text/html")
    public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
        CMD _result = parseError(request);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("error");
        modelAndView.addObject("code", _result.getCode());
        modelAndView.addObject("msg", _result.getMessage());
        // modelAndView.addObject("data", _result.getData());
        return modelAndView;
    }

    @RequestMapping
    @ResponseBody
    public CMD error(HttpServletRequest request, HttpServletResponse response) {
        return parseError(request);
    }

    private CMD parseError(HttpServletRequest request) {
        // getErrorAttributes(request, isIncludeStackTrace(request, MediaType.ALL));
        Map<String, Object> _errorMap = getErrorAttributes(request, false);
        // errorMap.get("path"); // timestamp, status, error, message, trace, path, _throwable

        if (_errorMap.containsKey("_throwable")) {
            Throwable _throwable = (Throwable) _errorMap.get("_throwable");
            if (_throwable instanceof BizException) {
                BizException _bizException = (BizException) _throwable;
                String _bizCode = _bizException.getBizCodeInfo().getCode();
                return CmdFactory.create(_bizCode, _bizException.getMessage());
            }
        }

        String _code = String.valueOf(_errorMap.get("status"));
//        String _msg = String.format("%s - %s", _errorMap.get("error"), _errorMap.get("message"));
        String _msg = StringUtils.parseString(_errorMap.get("message"), null);
        if (null == _msg)
            _msg = String.valueOf(_errorMap.get("error"));
        return CmdFactory.create(_code, _msg);
//        return CmdFactory.create(_code, _msg, _errorMap);
    }


    protected Map<String, Object> getErrorAttributes(HttpServletRequest request, boolean includeStackTrace) {
        WebRequest _webRequest = new ServletWebRequest(request);
        Map<String, Object> _result = this.errorAttributes.getErrorAttributes(_webRequest, includeStackTrace);
        Throwable _throwable = this.errorAttributes.getError(_webRequest);
        if (null != _throwable)
            _result.put("_throwable", _throwable);
        return _result;
    }

    // protected boolean isIncludeStackTrace(HttpServletRequest request, MediaType produces) {
    // IncludeStacktrace include = this.errorProperties.getIncludeStacktrace();
    // if (include == IncludeStacktrace.ALWAYS) {
    // return true;
    // }
    // if (include == IncludeStacktrace.ON_TRACE_PARAM) {
    // return getTraceParameter(request);
    // }
    // return false;
    // }


    @Bean(name = "error")
    @ConditionalOnMissingBean(name = "error")
    public View defaultErrorView() {
        return new BoxActionErrorView();
    }

//    @Bean(name = "index")
//    @ConditionalOnMissingBean(name = "index")
//    public View defaultIndexView() {
//        return new BoxActionIndexView();
//    }

}
