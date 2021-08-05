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

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 7:12
 * @Description 输入输出处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IIoUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleIoUtils";

    /**
     * 将指定的输入流拷贝到指定的输出流中。
     *
     * @param input  输入流
     * @param output 输出流
     * @return 拷贝数据大小
     * @throws IOException 数据操作异常
     */
    long copy(InputStream input, OutputStream output) throws IOException;

    /**
     * 将指定的输入流数据读取到字节数组中。
     *
     * @param input 输入流
     * @return 字节数组
     * @throws IOException 数据操作异常
     */
    byte[] readBytes(InputStream input) throws IOException;

    /**
     * 将指定的输入流数据读取为指定编码的字符串。
     *
     * @param input    输入流
     * @param encoding 字符串编码
     * @return 字符串
     * @throws IOException 数据操作异常
     */
    String readString(InputStream input, String encoding) throws IOException;

    /**
     * 将指定的输入流数据读取为默认编码（UTF-8）的字符串。
     *
     * @param input 输入流
     * @return 字符串
     * @throws IOException 数据操作异常
     */
    default String readString(InputStream input) throws IOException {
        return readString(input, "UTF-8");
    }

    /**
     * 将指定数据写入输出流
     *
     * @param data     数据
     * @param output   输出流
     * @param encoding 数据编码
     * @throws IOException 数据操作异常
     */
    void write(String data, OutputStream output, String encoding) throws IOException;

    /**
     * 将指定数据写入输出流
     *
     * @param data   数据
     * @param output 输出流
     * @throws IOException 数据操作异常
     */
    void write(String data, OutputStream output) throws IOException;

}
