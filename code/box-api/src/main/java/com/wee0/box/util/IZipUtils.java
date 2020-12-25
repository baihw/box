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

package com.wee0.box.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 22:02
 * @Description Zip处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IZipUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleZipUtils";

    /**
     * 压缩zip文件
     *
     * @param source  待压缩的源文件或者目录
     * @param zipFile 目标文件
     */
    void compress(File source, File zipFile);

    /**
     * 解压缩Zip文件
     *
     * @param zipStream     zip数据流
     * @param destDirectory 解压目标目录
     */
    void decompress(InputStream zipStream, File destDirectory);

    /**
     * 解压缩Zip文件
     *
     * @param zipFile       zip文件
     * @param destDirectory 解压目标目录
     */
    default void decompress(File zipFile, File destDirectory) {
        if (null == zipFile || !zipFile.exists() || !zipFile.isFile())
            throw new IllegalArgumentException("Invalid zipFile: " + zipFile);
        try (FileInputStream _zipStream = new FileInputStream(zipFile)) {
            decompress(_zipStream, destDirectory);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


}
