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

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.util.IPropertiesUtils;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:19
 * @Description 一个简单的Properties配置文件处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimplePropertiesUtils implements IPropertiesUtils {

    @Override
    public Properties load(InputStream inputStream) {
        if (null == inputStream)
            return null;
        Properties _props = new Properties();
        try {
            _props.load(inputStream);
        } catch (IOException e) {
            throw new BoxRuntimeException(e);
        }
        return _props;
    }

    @Override
    public Properties load(File file) {
        if (null == file || !file.exists())
            throw new IllegalArgumentException("invalid file:" + file);
        Properties _props = new Properties();
        try (FileInputStream _inStream = new FileInputStream(file)) {
            _props.load(_inStream);
        } catch (IOException e) {
            throw new BoxRuntimeException(e);
        }
        return _props;
    }

    /**
     * Map转换，排除空键名。
     *
     * @param properties 配置对象
     * @return Map对象
     */
    @Override
    public Map<String, String> toMap(Properties properties) {
        Map<String, String> _result = new HashMap<>();
        if (null == properties || properties.isEmpty())
            return _result;
        for (Map.Entry<Object, Object> _entry : properties.entrySet()) {
            Object _keyObj = _entry.getKey();
            if (null == _keyObj)
                continue;
            String _key = _keyObj.toString().trim();
            if (0 == _key.length())
                continue;
            Object _valueObj = _entry.getValue();
            String _value = null;
            if (null != _valueObj)
                _value = _valueObj.toString().trim();
            _result.put(_key, _value);
        }
        return _result;
    }

    @Override
    public Map<String, String> loadToMap(InputStream inputStream) {
        return toMap(load(inputStream));
    }

    @Override
    public Map<String, String> loadToMap(File file) {
        return toMap(load(file));
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimplePropertiesUtils() {
        if (null != SimplePropertiesUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimplePropertiesUtilsHolder {
        private static final SimplePropertiesUtils _INSTANCE = new SimplePropertiesUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimplePropertiesUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimplePropertiesUtils me() {
        return SimplePropertiesUtilsHolder._INSTANCE;
    }


}
