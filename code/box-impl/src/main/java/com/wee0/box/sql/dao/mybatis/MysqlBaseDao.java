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

package com.wee0.box.sql.dao.mybatis;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 10:36
 * @Description Mysql数据库环境下的Dao对象基础方法实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class MysqlBaseDao implements InvocationHandler {

    MysqlBaseDao() {

    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        try {
            if (Object.class.equals(method.getDeclaringClass()))
                return method.invoke(this, args);
            if (method.isDefault())
                return invokeDefaultMethod(proxy, method, args);
        } catch (Throwable t) {
            throw unwrapThrowable(t);
        }
        //:TODO 动态处理
        return null;
    }

    // 调用接口默认方法
    private static Object invokeDefaultMethod(Object proxy, Method method, Object[] args) throws Throwable {
        final Constructor<MethodHandles.Lookup> _constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, int.class);
        if (!_constructor.isAccessible())
            _constructor.setAccessible(true);
        final Class<?> _declaringClass = method.getDeclaringClass();
        final MethodHandles.Lookup _lookup = _constructor.newInstance(_declaringClass, MethodHandles.Lookup.PRIVATE | MethodHandles.Lookup.PROTECTED | MethodHandles.Lookup.PACKAGE | MethodHandles.Lookup.PUBLIC);
        final MethodHandle _handle = _lookup.unreflectSpecial(method, _declaringClass);
        return _handle.bindTo(proxy).invokeWithArguments(args);
    }

    // 获取原始异常信息
    private static Throwable unwrapThrowable(Throwable wrapped) {
        Throwable _result = wrapped;
        while (true) {
            if (_result instanceof InvocationTargetException) {
                _result = ((InvocationTargetException) _result).getTargetException();
            } else if (_result instanceof UndeclaredThrowableException) {
                _result = ((UndeclaredThrowableException) _result).getUndeclaredThrowable();
            } else {
                return _result;
            }
        }
    }

}
