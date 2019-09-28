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

package com.wee0.box.log.log4j2;

import com.wee0.box.log.ILoggerContext;
import org.apache.logging.log4j.ThreadContext;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:18
 * @Description 基于log4j2的日志上下文对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Log4j2LoggerContext implements ILoggerContext {
    @Override
    public void put(String key, String value) {
        ThreadContext.put(key, value);
    }

    @Override
    public void remove(String key) {
        ThreadContext.remove(key);
    }

    @Override
    public void clear() {
        ThreadContext.clearMap();
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private Log4j2LoggerContext() {
        if (null != Log4j2LoggerContextHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class Log4j2LoggerContextHolder {
        private static final Log4j2LoggerContext _INSTANCE = new Log4j2LoggerContext();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return Log4j2LoggerContextHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static Log4j2LoggerContext me() {
        return Log4j2LoggerContextHolder._INSTANCE;
    }

}
