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

import com.wee0.box.beans.ICloneable;
import com.wee0.box.util.IThreadUtils;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:25
 * @Description 一个简单的线程处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleThreadUtils implements IThreadUtils {

    @Override
    public <T> ThreadLocal<T> createThreadLocal() {
        return new InheritableThreadLocalObj<T>();
    }

    /**
     * 子线程友好的线程局部变量管理对象
     */
    private static final class InheritableThreadLocalObj<T> extends InheritableThreadLocal<T> {
        private InheritableThreadLocalObj() {
        }

        @Override
        protected T initialValue() {
            return null;
        }

        @Override
        protected T childValue(T parentValue) {
            if (null == parentValue)
                return null;
            if (parentValue instanceof ICloneable)
                return ((ICloneable<T>) parentValue).deepClone();
            return parentValue;
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleThreadUtils() {
        if (null != SimpleThreadUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleThreadUtilsHolder {
        private static final SimpleThreadUtils _INSTANCE = new SimpleThreadUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleThreadUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleThreadUtils me() {
        return SimpleThreadUtilsHolder._INSTANCE;
    }

}
