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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/6/13 7:05
 * @Description 摘要计算工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDigestUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleDigestUtils";

    /**
     * 计算md5值
     *
     * @param bytes 字节数组
     * @return md5值
     */
    byte[] md5(byte[] bytes);

    /**
     * 计算md5值
     *
     * @param inputStream 输入流
     * @return md5值
     */
    byte[] md5(InputStream inputStream) throws IOException;

    /**
     * 计算md5值，转换为16进制字符串。
     *
     * @param bytes 字节数组
     * @return md5值16进制字符串
     */
    String md5Hex(byte[] bytes);

    /**
     * 计算md5值，转换为16进制字符串。
     *
     * @param inputStream 输入流
     * @return md5值16进制字符串
     */
    String md5Hex(InputStream inputStream);

}
