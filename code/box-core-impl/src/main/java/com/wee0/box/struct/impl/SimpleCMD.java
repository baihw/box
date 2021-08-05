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

package com.wee0.box.struct.impl;

import com.wee0.box.struct.CMD;

import java.util.HashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:05
 * @Description 一个简单的编码-消息-数据类型实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleCMD extends HashMap<String, Object> implements CMD<String> {

    private static final long serialVersionUID = 1L;

    private static final String CODE_OK = "200";
    private static final String MSG_OK = "ok";

    private static final String KEY_CODE = "code";
    private static final String KEY_MESSAGE = "message";
    private static final String KEY_DATA = "data";

    public SimpleCMD(String code, String message, Object data) {
        this.setCode(code);
        this.setMessage(message);
        this.setData(data);
    }

    public SimpleCMD(String code, String message) {
        this(code, message, null);
    }

    public SimpleCMD(String message) {
        this(CODE_OK, message, null);
    }

    public SimpleCMD(Object data) {
        this(CODE_OK, MSG_OK, data);
    }

    public SimpleCMD() {
        this(CODE_OK, MSG_OK, null);
    }

    @Override
    public CMD<String> setCode(String code) {
        if (null == code)
            throw new IllegalArgumentException("status can't be null!");
        this.put(KEY_CODE, code);
        return this;
    }

    @Override
    public String getCode() {
        return String.valueOf(this.get(KEY_CODE));
    }

    @Override
    public CMD<String> setMessage(String message) {
        this.put(KEY_MESSAGE, message);
        return this;
    }

    @Override
    public String getMessage() {
        return (String) this.get(KEY_MESSAGE);
    }

    @Override
    public CMD<String> setData(Object data) {
        this.put(KEY_DATA, data);
        return this;
    }

    @Override
    public Object getData() {
        return this.get(KEY_DATA);
    }

    @Override
    public boolean isOK() {
        return CODE_OK.equals(getCode());
    }

}
