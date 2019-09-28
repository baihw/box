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

package com.wee0.box.sql.impl;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.util.shortcut.StringUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:56
 * @Description Bean信息描述对象
 * <pre>
 * 补充说明
 * </pre>
 **/
final class InternalBeanInfo {

    private final Class clazz;
    private final Map<String, PropertyDescriptor> propertyDescriptorMap;

    InternalBeanInfo(Class clazz) {
        this.clazz = clazz;
        BeanInfo _beanInfo = null;
        try {
            _beanInfo = Introspector.getBeanInfo(clazz);
        } catch (IntrospectionException e) {
            throw new IllegalStateException(e);
        }
        PropertyDescriptor[] _descriptors = _beanInfo.getPropertyDescriptors();
        this.propertyDescriptorMap = new HashMap<>(_descriptors.length);
        for (PropertyDescriptor _descriptor : _descriptors) {
            Method _writeMethod = _descriptor.getWriteMethod();
            if (null == _writeMethod)
                continue;
            if (!_writeMethod.isAccessible())
                _writeMethod.setAccessible(true);

            String _lowerCaseName = lowerCaseName(_descriptor.getName());
            this.propertyDescriptorMap.put(_lowerCaseName, _descriptor);
            String _underscoreName = StringUtils.underscore(_descriptor.getName());
            if (!_lowerCaseName.equals(_underscoreName))
                this.propertyDescriptorMap.put(_underscoreName, _descriptor);
        }
    }

    void setProperty(Object bean, String name, Object value) {
        PropertyDescriptor _descriptor = this.propertyDescriptorMap.get(name);
        if (null == _descriptor)
            return;
        try {
            _descriptor.getWriteMethod().invoke(bean, value);
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    static String lowerCaseName(String name) {
        return name.toLowerCase(Locale.US);
    }

}
