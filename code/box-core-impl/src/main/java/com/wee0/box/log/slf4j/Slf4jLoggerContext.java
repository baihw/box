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

package com.wee0.box.log.slf4j;

import com.wee0.box.log.ILoggerContext;
import org.slf4j.MDC;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:16
 * @Description 基于slf4j的日志上下文对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Slf4jLoggerContext implements ILoggerContext {
    @Override
    public void put(String key, String value) {
        MDC.put(key, value);
    }

    @Override
    public void remove(String key) {
        MDC.remove(key);
    }

    @Override
    public void clear() {
        MDC.clear();
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private Slf4jLoggerContext() {
        if (null != Slf4jLoggerContextHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class Slf4jLoggerContextHolder {
        private static final Slf4jLoggerContext _INSTANCE = new Slf4jLoggerContext();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return Slf4jLoggerContextHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static Slf4jLoggerContext me() {
        return Slf4jLoggerContextHolder._INSTANCE;
    }

}
