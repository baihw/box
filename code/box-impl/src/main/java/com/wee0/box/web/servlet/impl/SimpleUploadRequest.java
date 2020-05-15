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

import com.wee0.box.web.servlet.IUploadFile;
import com.wee0.box.web.servlet.IUploadRequest;

import java.util.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 8:20
 * @Description 一个简单的文件上传请求对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleUploadRequest extends HttpServletActionRequest implements IUploadRequest {

    private final Map<String, String> parameters;
    private final Map<String, IUploadFile> files;

    public SimpleUploadRequest(Map<String, String> parameters, Map<String, IUploadFile> uploadFiles) {
        this.parameters = new HashMap<>(parameters.size());
        this.files = new HashMap<>(uploadFiles.size());
        this.parameters.putAll(parameters);
        this.files.putAll(uploadFiles);
    }

    @Override
    public String getParameter(String name) {
        return this.parameters.get(name);
    }

    @Override
    public Map<String, String> getParameters() {
        return Collections.unmodifiableMap(parameters);
    }

    @Override
    public IUploadFile getUploadFile(String name) {
        return this.files.get(name);
    }

    @Override
    public Map<String, IUploadFile> getUploadFiles() {
        return Collections.unmodifiableMap(this.files);
    }

}
