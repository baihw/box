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

package com.wee0.box.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.URL;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 7:05
 * @Description 资源
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IResource {

    /**
     * @return 是否存在
     */
    boolean exists();

    /**
     * 获取资源定位地址
     *
     * @return 资源定位地址
     * @throws IOException 无法确定资源时抛出
     */
    URL getUrl() throws IOException;

    /**
     * 获取资源定位标识
     *
     * @return 资源定位标识
     * @throws IOException 无法确定资源时抛出
     */
    URI getUri() throws IOException;

    /**
     * 获取资源输入流
     *
     * @return 资源输入流
     * @throws IOException 无法确定资源时抛出
     */
    InputStream getInputStream() throws IOException;

    /**
     * 获取资源文件
     *
     * @return 资源文件
     * @throws IOException 无法确定资源时抛出
     */
    File getFile() throws IOException;

    /**
     * 获取文件名称
     *
     * @return 文件名称
     */
    String getFileName();

}
