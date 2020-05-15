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

package com.wee0.box.web.servlet;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 8:17
 * @Description 上传文件描述对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IUploadFile {

    /**
     * @return 文件名称
     */
    String getName();

    /**
     * @return 文件内容类型
     */
    String getContentType();

    /**
     * @return 文件大小
     */
    long getSize();

    /**
     * @return 文件输入流
     * @throws IOException 读写异常
     */
    InputStream getInputStream() throws IOException;

    /**
     * 保存到指定文件
     *
     * @param file 文件
     * @throws IOException 读写异常
     */
    void saveTo(File file) throws Exception;

}
