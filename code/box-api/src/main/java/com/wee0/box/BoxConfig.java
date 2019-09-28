/*
 *
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
 *
 */

package com.wee0.box;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:18
 * @Description 框架配置对象快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class BoxConfig {

    /**
     * 调用getSingleInstance方法获取经典单例对象时统一使用的静态方法名。
     */
    public static final String DEF_SINGLE_METHOD_NAME = "me";

    /**
     * 未指定实现类名称时使用的默认实现类名称。
     */
    public static final String DEF_IMPL_CLASS_NAME = "com.wee0.box.impl.SimpleBoxConfig";

    // 实现类实例
    private static final IBoxConfig IMPL;

    static {
        String _implName = System.getProperty(IBoxConfig.class.getName(), DEF_IMPL_CLASS_NAME);
        if (null == _implName || 0 == (_implName = _implName.trim()).length())
            throw new IllegalArgumentException("boxConfig implementor can't be empty!");
        IMPL = getSingleInstance(_implName, IBoxConfig.class);
    }

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static IBoxConfig impl() {
        return IMPL;
    }

    /**
     * 获取指定类的经典单例对象实例。
     *
     * @param className 类名称
     * @param classType 类型
     * @param <T>       类型接口
     * @return 经典单例对象实例
     */
    public static final <T> T getSingleInstance(String className, Class<T> classType) {
        if (null == className || 0 == (className = className.trim()).length())
            throw new IllegalArgumentException("className can't be empty!");
        if (null == classType)
            throw new IllegalArgumentException("classType can't be null!");
        try {
            Class<?> _implCla = Class.forName(className);
            if (null == _implCla)
                throw new IllegalStateException("invalid class name:" + className);
            if (!classType.isAssignableFrom(_implCla))
                throw new IllegalStateException(className + " is not " + classType);
            Method meMethod = _implCla.getDeclaredMethod(DEF_SINGLE_METHOD_NAME);
            // 通过静态方法"me"获取实现类实例单例对象。方法签名为：public static T me(){} ;
            Object _result = meMethod.invoke(null);
            return (T) _result;
        } catch (ClassNotFoundException | SecurityException | IllegalAccessException | IllegalArgumentException | NoSuchMethodException | InvocationTargetException e) {
            throw new RuntimeException(e);
        }
    }

}
