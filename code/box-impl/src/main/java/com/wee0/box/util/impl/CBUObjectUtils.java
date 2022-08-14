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
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.util.IObjectUtils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.ObjectStreamException;
import java.lang.reflect.*;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 9:26
 * @Description 基于Commons-BeanUtils实现的组件处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public class CBUObjectUtils implements IObjectUtils {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(CBUObjectUtils.class);

    // 缓存容器
    private final Map<Class, Map<String, PropertyDescriptor>> CACHE = UtilsCandidate.createLruCache(256);

    @Override
    public void setProperty(Object bean, String name, Object value) {
        try {
            PropertyUtils.setProperty(bean, name, value);
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public void setProperties(Object bean, Map<String, ?> properties) {
        try {
            BeanUtils.populate(bean, properties);
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Map<String, Object> toMap(Object bean) {
        if (null == bean)
            throw new BoxRuntimeException("bean can not be null!");
        Map<String, Object> _result = new HashMap<>(32);
        Class<?> _cla = bean.getClass();
        visitFields(_cla, bean, (_fieldName, _fieldValue) -> _result.put(_fieldName, _fieldValue));
        while (null != _cla.getSuperclass() && Object.class != _cla) {
            _cla = _cla.getSuperclass();
            visitFields(_cla, bean, (_fieldName, _fieldValue) -> _result.put(_fieldName, _fieldValue));
        }
        return _result;
    }

    @Override
    public String reflectionToString(Object bean) {
        if (null == bean)
            throw new BoxRuntimeException("bean can not be null!");
        Class<?> _cla = bean.getClass();
        StringBuilder _sb = new StringBuilder(256);
        _sb.append(_cla.getName()).append('@').append(Integer.toHexString(System.identityHashCode(bean)));
        _sb.append('[');
        visitFields(_cla, bean, (_fieldName, _fieldValue) -> _sb.append(_fieldName).append('=').append(_fieldValue).append(','));
        while (null != _cla.getSuperclass() && Object.class != _cla) {
            _cla = _cla.getSuperclass();
            visitFields(_cla, bean, (_fieldName, _fieldValue) -> _sb.append(_fieldName).append('=').append(_fieldValue).append(','));
        }
        if (',' == _sb.charAt(_sb.length() - 1))
            _sb.deleteCharAt(_sb.length() - 1);
        return _sb.append(']').toString();
    }

    static void visitFields(Class<?> clazz, Object bean, BiConsumer<String, Object> consumer) {
        Field[] _fields = clazz.getDeclaredFields();
        if (null == _fields || 0 == _fields.length)
            return;
        AccessibleObject.setAccessible(_fields, true);
        for (final Field _field : _fields) {
            final String _fieldName = _field.getName();
            if (-1 != _fieldName.indexOf('$'))
                continue;
            if (Modifier.isTransient(_field.getModifiers()))
                continue;
            if (Modifier.isStatic(_field.getModifiers()))
                continue;
            try {
                final Object _fieldValue = _field.get(bean);
                consumer.accept(_fieldName, _fieldValue);
//                builder.append(_fieldName).append('=').append(_fieldValue).append(',');
            } catch (final IllegalAccessException ex) {
                throw new InternalError("Unexpected IllegalAccessException: " + ex.getMessage());
            }
        }
    }

    String toStringByPropertyDescriptor(Object bean) {
        Class<?> _cla = bean.getClass();
        Map<String, PropertyDescriptor> _properties = CACHE.get(_cla);
        if (null == _properties) {
            _properties = getBeanPropertyDescriptors(_cla);
            CACHE.putIfAbsent(_cla, _properties);
        }

        StringBuilder _sb = new StringBuilder(256);
        _sb.append(_cla.getName()).append('@').append(Integer.toHexString(System.identityHashCode(bean)));
        _sb.append('[');
        _properties.forEach((k, v) -> {
            Method _readMethod = v.getReadMethod();
            if (null == _readMethod)
                return;
            if (!_readMethod.isAccessible())
                _readMethod.setAccessible(true);
            try {
                Object _value = _readMethod.invoke(bean, (Object[]) null);
                _sb.append(k).append('=').append(_value).append(',');
            } catch (IllegalAccessException e) {
                log.debug(e.getMessage());
            } catch (InvocationTargetException e) {
                log.debug(e.getMessage());
            }
        });
        if (',' == _sb.charAt(_sb.length() - 1))
            _sb.deleteCharAt(_sb.length() - 1);
        return _sb.append(']').toString();
    }

    /**
     * 获取对象属性名称与描述信息映射数据
     *
     * @param beanClass 对象类型
     * @return 映射数据
     */
    static Map<String, PropertyDescriptor> getBeanPropertyDescriptors(Class<?> beanClass) {
        try {
            BeanInfo _beanInfo = Introspector.getBeanInfo(beanClass, Object.class);
            PropertyDescriptor[] _propertyDescriptors = _beanInfo.getPropertyDescriptors();
            Map<String, PropertyDescriptor> _result = new HashMap<>(_propertyDescriptors.length);
            for (PropertyDescriptor _descriptor : _propertyDescriptors) {
                _result.put(_descriptor.getName(), _descriptor);
            }
            return _result;
        } catch (IntrospectionException e) {
            throw new BoxRuntimeException(e);
        }
    }

//    static void doSetProperty(Object bean, String name, Object value) throws IntrospectionException, InvocationTargetException, IllegalAccessException {
//        PropertyDescriptor _namePd = new PropertyDescriptor(name, bean.getClass());
//        _namePd.getWriteMethod().invoke(bean, value);
//    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private CBUObjectUtils() {
        if (null != CBUObjectUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class CBUObjectUtilsHolder {
        private static final CBUObjectUtils _INSTANCE = new CBUObjectUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return CBUObjectUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static CBUObjectUtils me() {
        return CBUObjectUtilsHolder._INSTANCE;
    }


}
