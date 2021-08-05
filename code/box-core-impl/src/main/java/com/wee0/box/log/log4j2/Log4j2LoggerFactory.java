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

import com.wee0.box.log.ILogger;
import com.wee0.box.log.ILoggerFactory;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.spi.AbstractLogger;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:51
 * @Description 基于log4j2的日志工厂实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Log4j2LoggerFactory implements ILoggerFactory {

    // 一些支持标记的日志组件用来做标记
    public static final String MARKER = "BOX";

    @Override
    public ILogger getLogger(String category) {
        Logger _logger = LogManager.getLogger(category);
        if (_logger instanceof AbstractLogger) {
            return new Log4j2AbstractLogger((AbstractLogger) _logger);
        }
        return new Log4j2Logger(_logger);
    }

    @Override
    public ILogger getLogger(Class category) {
        return getLogger(category.getName());
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private Log4j2LoggerFactory() {
        if (null != Log4j2LoggerFactoryHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class Log4j2LoggerFactoryHolder {
        private static final Log4j2LoggerFactory _INSTANCE = new Log4j2LoggerFactory();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return Log4j2LoggerFactoryHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static Log4j2LoggerFactory me() {
        return Log4j2LoggerFactoryHolder._INSTANCE;
    }

}
