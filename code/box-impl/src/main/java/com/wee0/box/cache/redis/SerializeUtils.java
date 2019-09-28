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

package com.wee0.box.cache.redis;

import com.wee0.box.exception.BoxRuntimeException;

import java.io.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 9:05
 * @Description 序列化工具类
 * <pre>
 * 补充说明
 * </pre>
 **/
final class SerializeUtils {

    /**
     * 序列化
     *
     * @param obj 对象
     * @return 序列化数据
     * @throws IOException
     */
    static byte[] serialize(Object obj) throws IOException {
        try (ByteArrayOutputStream _byteArrayOutputStream = new ByteArrayOutputStream();
             ObjectOutputStream _outputStream = new ObjectOutputStream(_byteArrayOutputStream);) {
            _outputStream.writeObject(obj);
            return _byteArrayOutputStream.toByteArray();
        }
    }

    /**
     * 反序列化
     *
     * @param bytes       序列化数据
     * @param classLoader 类加载器
     * @return 对象
     * @throws IOException
     */
    static Object deserialize(byte[] bytes, ClassLoader classLoader) throws IOException {
        if (null == bytes || 0 == bytes.length)
            return null;
        try (ByteArrayInputStream _byteArrayInputStream = new ByteArrayInputStream(bytes);
             ObjectInputStream _inputStream = new MyObjectInputStream(_byteArrayInputStream, classLoader);) {
            return _inputStream.readObject();
        } catch (ClassNotFoundException e) {
            throw new BoxRuntimeException(e);
        }
    }

    static Object deserialize(byte[] bytes) throws IOException {
        return deserialize(bytes, null);
    }

    static class MyObjectInputStream extends ObjectInputStream {

        // 使用的类加载器。
        private final ClassLoader LOADER;

        public MyObjectInputStream(InputStream in) throws IOException {
            super(in);
            this.LOADER = null;
        }

        public MyObjectInputStream(InputStream in, ClassLoader classLoader) throws IOException {
            super(in);
            this.LOADER = classLoader;
        }

        @Override
        protected Class<?> resolveClass(ObjectStreamClass desc) throws IOException, ClassNotFoundException {
            if (null != this.LOADER) {
                String _name = desc.getName();
                try {
//					byte[] codeSource = (byte[])this.readObject();
//					return this.LOADER.loadClass(_name, codeSource);
                    return Class.forName(_name, false, this.LOADER);
                } catch (ClassNotFoundException ex) {
                }
            }
            return super.resolveClass(desc);
        }

    }

}
