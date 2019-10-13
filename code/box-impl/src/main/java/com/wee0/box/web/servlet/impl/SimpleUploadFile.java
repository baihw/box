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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 8:52
 * @Description 一个简单的上传文件描述对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class SimpleUploadFile implements IUploadFile {

    @Override
    public String getName() {
        return null;
    }

    @Override
    public String getContentType() {
        return null;
    }

    @Override
    public long getSize() {
        return 0;
    }

    @Override
    public InputStream getInputStream() throws IOException {
        return null;
    }

    @Override
    public void saveTo(File file) throws Exception {

    }
}
