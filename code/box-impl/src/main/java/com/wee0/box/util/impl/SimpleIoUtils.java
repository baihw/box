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

import com.wee0.box.BoxConfig;
import com.wee0.box.util.IIoUtils;

import java.io.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/26 7:18
 * @Description 一个简单的输入输出处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleIoUtils implements IIoUtils {

    // 结束标志
    private static final int EOF = -1;

    // 默认的缓冲大小 1024K。
    private static final int DEF_BUFFER_SIZE = 1024 * 1024;

    // 默认的编码
    private static final String DEF_ENCODING = BoxConfig.impl().getEncoding();

    @Override
    public long copy(InputStream input, OutputStream output) throws IOException {
        byte[] _buffer = new byte[DEF_BUFFER_SIZE];
        long _count = 0;
        int _len = 0;
        while (EOF != (_len = input.read(_buffer))) {
            output.write(_buffer, 0, _len);
            _count += _len;
        }
        output.flush();
        return _count;
    }

    @Override
    public byte[] readBytes(InputStream input) throws IOException {
        if (null == input)
            return null;
        ByteArrayOutputStream _output = new ByteArrayOutputStream();
        byte[] _buf = new byte[DEF_BUFFER_SIZE];
        for (int _len = input.read(_buf); _len != EOF; _len = input.read(_buf)) {
            _output.write(_buf, 0, _len);
        }
        _output.flush();
        return _output.toByteArray();
    }

    @Override
    public void write(String data, OutputStream output, String encoding) throws IOException {
        if (null == data || null == output)
            return;
        if (null == encoding)
            encoding = DEF_ENCODING;
        output.write(data.getBytes(encoding));
    }

    @Override
    public void write(String data, OutputStream output) throws IOException {
        write(data, output, DEF_ENCODING);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleIoUtils() {
        if (null != SimpleIoUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleIoUtilsHolder {
        private static final SimpleIoUtils _INSTANCE = new SimpleIoUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleIoUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleIoUtils me() {
        return SimpleIoUtilsHolder._INSTANCE;
    }
}
