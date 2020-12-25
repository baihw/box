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

import com.wee0.box.util.IByteUtils;
import com.wee0.box.util.shortcut.CheckUtils;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/17 22:12
 * @Description 一个简单的字节处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleByteUtils implements IByteUtils {

    private static final char[] _HEX_CODE = "0123456789ABCDEF".toCharArray();

    @Override
    public String bytesToHexString(byte[] bytes) {
        CheckUtils.checkNotNull(bytes, "bytes cannot be null!");
        StringBuilder _builder = new StringBuilder(bytes.length * 2);
        for (byte _byte : bytes) {
            _builder.append(_HEX_CODE[(_byte >> 4) & 0xF]);
            _builder.append(_HEX_CODE[(_byte & 0xF)]);
        }
        return _builder.toString();
    }

    @Override
    public byte[] hexStringToBytes(String hexString) {
        CheckUtils.checkNotNull(hexString, "hexString cannot be null!");
        final int _LEN = hexString.length();
        if (_LEN % 2 != 0)
            throw new IllegalArgumentException("hexString needs to be even-length: " + hexString);
        byte[] _result = new byte[_LEN / 2];
        for (int _i = 0; _i < _LEN; _i += 2) {
            int _h1 = _hexToBin(hexString.charAt(_i));
            int _h2 = _hexToBin(hexString.charAt(_i + 1));
            if (_h1 == -1 || _h2 == -1)
                throw new IllegalArgumentException("contains illegal character for hexString: " + hexString);
            _result[_i / 2] = (byte) (_h1 * 16 + _h2);
        }
        return _result;
    }

    private static int _hexToBin(char ch) {
        if ('0' <= ch && ch <= '9')
            return ch - '0';
        if ('A' <= ch && ch <= 'F')
            return ch - 'A' + 10;
        if ('a' <= ch && ch <= 'f')
            return ch - 'a' + 10;
        return -1;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleByteUtils() {
        if (null != SimpleByteUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleByteUtilsHolder {
        private static final SimpleByteUtils _INSTANCE = new SimpleByteUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleByteUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleByteUtils me() {
        return SimpleByteUtilsHolder._INSTANCE;
    }
}
