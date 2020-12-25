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

package com.wee0.box.util.shortcut;

import com.wee0.box.BoxConfig;
import com.wee0.box.util.IByteUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 22:13
 * @Description 字节处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class ByteUtils {

    // 实现类实例
    private static final IByteUtils IMPL = BoxConfig.impl().getInterfaceImpl(IByteUtils.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static IByteUtils impl() {
        return IMPL;
    }

    /**
     * 字节数组转换为十六进制字符串
     *
     * @param bytes 字节数组
     * @return 十六进制字符串
     */
    public static String bytesToHexString(byte[] bytes) {
        return IMPL.bytesToHexString(bytes);
    }

    /**
     * 十六进制字符串转换为字节数组
     *
     * @param hexString 十六进制字符串
     * @return 字节数组
     */
    public static byte[] hexStringToBytes(String hexString) {
        return IMPL.hexStringToBytes(hexString);
    }
}
