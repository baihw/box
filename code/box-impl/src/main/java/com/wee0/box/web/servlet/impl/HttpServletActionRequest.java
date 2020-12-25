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

package com.wee0.box.web.servlet.impl;

import com.wee0.box.BoxConstants;
import com.wee0.box.web.IActionRequest;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/22 15:18
 * @Description 基于HttpServlet的请求对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class HttpServletActionRequest implements IActionRequest {

    private final HttpServletRequest rawObject;

    // 参数集合
    private Map<String, String> parameters = new HashMap<String, String>(16, 1.0f);

    // Cookie集合
    private Map<String, Cookie> cookies = new HashMap<String, Cookie>(8, 1.0f);

    public HttpServletActionRequest(HttpServletRequest rawObject) {
        this.rawObject = rawObject;
    }

    public HttpServletActionRequest() {
        this(null);
    }

    @Override
    public String getParameter(String name) {
        return this.rawObject.getParameter(name);
    }

    @Override
    public String getParameter(String name, String defaultValue) {
        String _value = this.rawObject.getParameter(name);
        return null == _value || 0 == (_value = _value.trim()).length() ? defaultValue : _value;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return this.rawObject.getParameterMap();
    }

    @Override
    public Map<String, String> getParameters() {
        if (this.parameters.isEmpty()) {
            synchronized (this.parameters) {
                if (this.parameters.isEmpty()) {
                    Map<String, String[]> _source = this.rawObject.getParameterMap();
                    Map<String, String> _parameters = new HashMap<String, String>(_source.size());
                    for (Map.Entry<String, String[]> _entry : _source.entrySet()) {
                        String _key = _entry.getKey();
                        String _value0 = null == _entry.getValue() || 0 == _entry.getValue().length ? BoxConstants.EMPTY_STRING : _entry.getValue()[0];
                        _parameters.put(_key, _value0);
                    }
                    this.parameters.putAll(_parameters);
                }
            }
        }
        return this.parameters;
    }

    @Override
    public IActionRequest setAttr(String name, Object value) {
        this.rawObject.setAttribute(name, value);
        return this;
    }

    @Override
    public <T> T getAttr(String name) {
        Object _result = this.rawObject.getAttribute(name);
        return null == _result ? null : (T) _result;
    }

    @Override
    public <T> T getAttr(String name, T defaultValue) {
        Object _result = this.rawObject.getAttribute(name);
        return null == _result ? defaultValue : (T) _result;
    }

    @Override
    public String getHeader(String name) {
        return this.rawObject.getHeader(name);
    }

    @Override
    public String getCookie(String name, String defValue) {
        if (this.cookies.isEmpty()) {
            synchronized (this.cookies) {
                if (this.cookies.isEmpty()) {
                    Cookie[] _source = this.rawObject.getCookies();
                    Map<String, Cookie> _cookies = new HashMap<String, Cookie>(_source.length);
                    for (Cookie _item : _source) {
                        _cookies.put(_item.getName(), _item);
                    }
                    this.cookies.putAll(_cookies);
                }
            }
        }
        Cookie _cookie = this.cookies.get(name);
        if (null == _cookie)
            return defValue;
        String _cookieValue = _cookie.getValue();
        return null == _cookieValue || 0 == (_cookieValue = _cookieValue.trim()).length() ? defValue : _cookieValue;
    }

    @Override
    public String getContextPath() {
        return this.rawObject.getContextPath();
    }

    @Override
    public String getURI() {
        return this.rawObject.getRequestURI();
    }

    @Override
    public String getFullURI() {
        String _uri = this.rawObject.getRequestURI();
        String _queryString = this.rawObject.getQueryString();
        return null == _queryString ? _uri : _uri + "?" + _queryString;
    }

    @Override
    public String getRequestIP() {
        String _requestIP = this.rawObject.getHeader("x-forwarded-for");
        if (null == _requestIP || 0 == _requestIP.length() || "unknown".equalsIgnoreCase(_requestIP)) {
            _requestIP = this.rawObject.getHeader("Proxy-Client-IP");
        }
        if (null == _requestIP || 0 == _requestIP.length() || "unknown".equalsIgnoreCase(_requestIP)) {
            _requestIP = this.rawObject.getHeader("WL-Proxy-Client-IP");
        }
        if (null == _requestIP || 0 == _requestIP.length() || "unknown".equalsIgnoreCase(_requestIP)) {
            _requestIP = this.rawObject.getRemoteAddr();
        }
        if (null == _requestIP || 0 == _requestIP.length()) {
            _requestIP = "000.000.000.000";
        }
        return _requestIP;
    }

    @Override
    public Object getRaw() {
        return this.rawObject;
    }
}
