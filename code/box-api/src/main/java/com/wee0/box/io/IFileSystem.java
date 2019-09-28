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

import java.io.IOException;
import java.util.List;
import java.util.function.Function;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 7:02
 * @Description 文件系统管理对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IFileSystem {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.io.impl.SimpleFileSystem";

    /**
     * 列出指定目录下的所有文件相对路径
     *
     * @param directory  目录名称
     * @param nameFilter 名称过滤器，返回false的文件将被忽略
     * @return 目录下的所有文件相对路径集合
     * @throws IOException 无法确定资源时抛出
     */
    List<String> list(String directory, Function<String, Boolean> nameFilter) throws IOException;

    /**
     * 列出指定目录下的所有文件相对路径
     *
     * @param directory 目录名称
     * @return 目录下的所有文件相对路径集合
     * @throws IOException 无法确定资源时抛出
     */
    List<String> list(String directory) throws IOException;

}
