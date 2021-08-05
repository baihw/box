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

package com.wee0.box.util.impl;

import com.wee0.box.util.IDigestUtils;
import com.wee0.box.util.shortcut.ByteUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/6/13 7:07
 * @Description 一个简单的摘要计算工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleDigestUtils implements IDigestUtils {

    static final String ALGORITHM_MD5 = "MD5";
    static final byte[] EMPTY_BYTES = new byte[0];

    @Override
    public byte[] md5(byte[] bytes) {
        if (null == bytes) return EMPTY_BYTES;
        MessageDigest _digest = _getDigest(ALGORITHM_MD5);
        _digest.update(bytes);
        return _digest.digest();
    }

    @Override
    public byte[] md5(InputStream inputStream) {
        if (null == inputStream) return EMPTY_BYTES;
        MessageDigest _digest = _getDigest(ALGORITHM_MD5);
        byte[] _buffer = new byte[4096];
        int _bytesRead = -1;
        try {
            while (-1 != (_bytesRead = inputStream.read(_buffer))) {
                _digest.update(_buffer, 0, _bytesRead);
            }
        } catch (IOException e) {
            return EMPTY_BYTES;
        }
        return _digest.digest();
    }

    @Override
    public String md5Hex(byte[] bytes) {
        return ByteUtils.bytesToHexString(md5(bytes));
    }

    @Override
    public String md5Hex(InputStream inputStream) {
        return ByteUtils.bytesToHexString(md5(inputStream));
    }


    // 获取指定名称的摘要算法
    private static MessageDigest _getDigest(String algorithm) {
        try {
            return MessageDigest.getInstance(algorithm);
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("Could not find MessageDigest with algorithm \"" + algorithm + "\"", e);
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleDigestUtils() {
        if (null != SimpleDigestUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleDigestUtilsHolder {
        private static final SimpleDigestUtils _INSTANCE = new SimpleDigestUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleDigestUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleDigestUtils me() {
        return SimpleDigestUtilsHolder._INSTANCE;
    }
}
