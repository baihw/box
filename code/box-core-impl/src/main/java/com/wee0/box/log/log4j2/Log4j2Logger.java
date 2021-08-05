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
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:52
 * @Description 基于log4j2的日志实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class Log4j2Logger implements ILogger {

    // 标记对象
    private static Marker MARKER = MarkerManager.getMarker(Log4j2LoggerFactory.MARKER);

    private Logger log;

    Log4j2Logger(Logger log) {
        this.log = log;
    }

    @Override
    public String getName() {
        return this.log.getName();
    }

    @Override
    public void trace(String msg, Object... arguments) {
        this.log.trace(MARKER, msg, arguments);
    }

    @Override
    public void trace(String msg, Throwable t) {
        this.log.trace(MARKER, msg, t);
    }

    @Override
    public void debug(String msg, Object... arguments) {
        this.log.debug(MARKER, msg, arguments);
    }

    @Override
    public void debug(String msg, Throwable t) {
        this.log.debug(MARKER, msg, t);
    }

    @Override
    public void info(String msg, Object... arguments) {
        this.log.info(MARKER, msg, arguments);
    }

    @Override
    public void info(String msg, Throwable t) {
        this.log.info(MARKER, msg, t);
    }

    @Override
    public void warn(String msg, Object... arguments) {
        this.log.warn(MARKER, msg, arguments);
    }

    @Override
    public void warn(String msg, Throwable t) {
        this.log.warn(MARKER, msg, t);
    }

    @Override
    public void error(String msg, Object... arguments) {
        this.log.error(MARKER, msg, arguments);
    }

    @Override
    public void error(String msg, Throwable t) {
        this.log.error(MARKER, msg, t);
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
}
