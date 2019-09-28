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
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.apache.logging.log4j.message.Message;
import org.apache.logging.log4j.spi.AbstractLogger;
import org.apache.logging.log4j.spi.ExtendedLoggerWrapper;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:53
 * @Description 基于log4j2的日志实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class Log4j2AbstractLogger implements ILogger {

    private static Marker MARKER = MarkerManager.getMarker(Log4j2LoggerFactory.MARKER);

    private static final String FQCN = Log4j2AbstractLogger.class.getName();

    private ExtendedLoggerWrapper log;

    Log4j2AbstractLogger(AbstractLogger abstractLogger) {
        this.log = new ExtendedLoggerWrapper(abstractLogger, abstractLogger.getName(), abstractLogger.getMessageFactory());
    }

    @Override
    public String getName() {
        return this.log.getName();
    }

    @Override
    public void trace(String msg, Object... arguments) {
        logIfEnabled(Level.TRACE, null, msg, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        logIfEnabled(Level.TRACE, t, msg);
    }

    @Override
    public void debug(String msg, Object... arguments) {
        logIfEnabled(Level.DEBUG, null, msg, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        logIfEnabled(Level.DEBUG, t, msg);
    }

    @Override
    public void info(String msg, Object... arguments) {
        logIfEnabled(Level.INFO, null, msg, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        logIfEnabled(Level.INFO, t, msg);
    }

    @Override
    public void warn(String msg, Object... arguments) {
        logIfEnabled(Level.WARN, null, msg, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        logIfEnabled(Level.WARN, t, msg);
    }

    @Override
    public void error(String msg, Object... arguments) {
        logIfEnabled(Level.ERROR, null, msg, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        logIfEnabled(Level.ERROR, t, msg);
    }

    @Override
    public boolean isTraceEnabled() {
        return this.log.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return this.log.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return this.log.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return this.log.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return this.log.isErrorEnabled();
    }

    @Override
    public String toString() {
        return this.log.getName();
    }

    /**
     * 调用原生的日志打印方法
     *
     * @param level     日志级别
     * @param t         异常对象
     * @param msg       消息
     * @param arguments 消息参数
     */
    private void logIfEnabled(Level level, Throwable t, String msg, Object... arguments) {
        Message _msg;
        if (null == arguments || 0 == arguments.length) {
            _msg = this.log.getMessageFactory().newMessage(msg);
        } else {
            _msg = this.log.getMessageFactory().newMessage(msg, arguments);
        }
        this.log.logIfEnabled(FQCN, level, MARKER, _msg, t);
    }

}