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

import java.io.ObjectStreamException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:52
 * @Description Bean处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
final class InternalBeanHelper {

    // 缓存容器
    private static final Map<Class, InternalBeanInfo> CACHE = new ConcurrentHashMap<>(256);

    /**
     * 创建指定类型实例
     *
     * @param clazz 类型
     * @param <T>   类型限制
     * @return 实例
     * @throws NoSuchMethodException     方法找不到
     * @throws IllegalAccessException    无法访问
     * @throws InvocationTargetException 无法调用
     * @throws InstantiationException    实例化失败
     */
    <T> T newInstance(Class<T> clazz) throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (clazz.isPrimitive())
            throw new IllegalArgumentException("Primitive types can't be instantiated in Java");
        Constructor<T> _constructor = clazz.getDeclaredConstructor((Class[]) null);
        if (null == _constructor)
            throw new IllegalStateException("default no parameters constructor not found");
        if (!_constructor.isAccessible())
            _constructor.setAccessible(true);
        return _constructor.newInstance((Object[]) null);
    }

    /**
     * 设置对象属性
     *
     * @param obj   对象
     * @param name  属性名称
     * @param value 属性值
     */
    static void setProperty(Object obj, String name, Object value) {
        Class _class = obj.getClass();
        InternalBeanInfo _beanInfo = CACHE.get(_class);
        if (null == _beanInfo) {
            _beanInfo = new InternalBeanInfo(_class);
            if (null != CACHE.putIfAbsent(_class, _beanInfo))
                _beanInfo = CACHE.get(_class);
        }
        _beanInfo.setProperty(obj, name, value);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private InternalBeanHelper() {
        if (null != InternalBeanHelperHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class InternalBeanHelperHolder {
        private static final InternalBeanHelper _INSTANCE = new InternalBeanHelper();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return InternalBeanHelperHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static InternalBeanHelper me() {
        return InternalBeanHelperHolder._INSTANCE;
    }

}
