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

import com.wee0.box.log.ILogger;
import com.wee0.box.log.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.spi.LocationAwareLogger;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:32
 * @Description 基于slf4j的日志工厂实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class Slf4jLoggerFactory implements ILoggerFactory {

    static {
        try {
            // 检查 slf4j 版本必须 大于等于 1.6
            LoggerFactory.getLogger(Slf4jLoggerFactory.class).getClass().getMethod("log", Marker.class, String.class, int.class, String.class, Object[].class, Throwable.class);
        } catch (NoSuchMethodException e) {
            throw new IllegalStateException("slf4j version must greater than or equal to 1.6!");
        }
    }

    @Override
    public ILogger getLogger(String category) {
        Logger _logger = org.slf4j.LoggerFactory.getLogger(category);
        return new Slf4jLocationAwareLogger((LocationAwareLogger) _logger);
    }

    @Override
    public ILogger getLogger(Class category) {
        return getLogger(category.getName());
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private Slf4jLoggerFactory() {
        if (null != Slf4jLoggerFactoryHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class Slf4jLoggerFactoryHolder {
        private static final Slf4jLoggerFactory _INSTANCE = new Slf4jLoggerFactory();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return Slf4jLoggerFactoryHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static Slf4jLoggerFactory me() {
        return Slf4jLoggerFactoryHolder._INSTANCE;
    }


}
