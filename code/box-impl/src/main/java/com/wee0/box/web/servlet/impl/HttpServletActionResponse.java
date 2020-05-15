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
import com.wee0.box.web.IActionResponse;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/22 15:28
 * @Description 基于HttpServlet的响应对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class HttpServletActionResponse implements IActionResponse {

    private final HttpServletResponse rawObject;

    // 响应数据
    private Object data;


    public HttpServletActionResponse(HttpServletResponse rawObject) {
        this.rawObject = rawObject;
    }

    public HttpServletActionResponse() {
        this(null);
    }

    @Override
    public IActionResponse setStatus(int status) {
        this.rawObject.setStatus(status);
        return this;
    }

    @Override
    public int getStatus() {
        return this.rawObject.getStatus();
    }

    @Override
    public IActionResponse setData(Object data) {
        this.data = data;
        return this;
    }

    @Override
    public Object getData() {
        return this.data;
    }

    @Override
    public Object getRaw() {
        return this.rawObject;
    }
}
