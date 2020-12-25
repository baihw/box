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

package com.wee0.box.web.impl;

import com.wee0.box.web.IActionRequest;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/22 15:16
 * @Description 一个简单的请求对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleActionRequest implements IActionRequest {

    private final Object rawObject;

    public SimpleActionRequest(Object rawObject) {
        this.rawObject = rawObject;
    }

    public SimpleActionRequest() {
        this(null);
    }

    @Override
    public String getParameter(String name) {
        return null;
    }

    @Override
    public String getParameter(String name, String defaultValue) {
        return null;
    }

    @Override
    public Map<String, String[]> getParameterMap() {
        return null;
    }

    @Override
    public Map<String, String> getParameters() {
        return null;
    }

    @Override
    public IActionRequest setAttr(String name, Object value) {
        return null;
    }

    @Override
    public <T> T getAttr(String attrName) {
        return null;
    }

    @Override
    public <T> T getAttr(String attrName, T defaultValue) {
        return null;
    }

    @Override
    public String getHeader(String name) {
        return null;
    }

    @Override
    public String getCookie(String name, String defValue) {
        return null;
    }

    @Override
    public String getContextPath() {
        return null;
    }

    @Override
    public String getURI() {
        return null;
    }

    @Override
    public String getFullURI() {
        return null;
    }

    @Override
    public String getRequestIP() {
        return null;
    }

    @Override
    public Object getRaw() {
        return this.rawObject;
    }
}
