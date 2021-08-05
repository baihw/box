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
import org.slf4j.spi.LocationAwareLogger;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:33
 * @Description 基于slf4j的日志实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class Slf4jLocationAwareLogger implements ILogger {

    private static final String FQCN = Slf4jLocationAwareLogger.class.getName();

    // 实际的日志处理对象
    private final org.slf4j.spi.LocationAwareLogger logger;

    Slf4jLocationAwareLogger(LocationAwareLogger logger) {
        this.logger = logger;
    }

    @Override
    public String getName() {
        return this.logger.getName();
    }

    @Override
    public void trace(String msg, Object... arguments) {
        this.logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, msg, arguments, null);
    }

    @Override
    public void trace(String msg, Throwable t) {
        this.logger.log(null, FQCN, LocationAwareLogger.TRACE_INT, msg, null, t);
    }

    @Override
    public void debug(String msg, Object... arguments) {
        this.logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, msg, arguments, null);
    }

    @Override
    public void debug(String msg, Throwable t) {
        this.logger.log(null, FQCN, LocationAwareLogger.DEBUG_INT, msg, null, t);
    }

    @Override
    public void info(String msg, Object... arguments) {
        this.logger.log(null, FQCN, LocationAwareLogger.INFO_INT, msg, arguments, null);
    }

    @Override
    public void info(String msg, Throwable t) {
        this.logger.log(null, FQCN, LocationAwareLogger.INFO_INT, msg, null, t);
    }

    @Override
    public void warn(String msg, Object... arguments) {
        this.logger.log(null, FQCN, LocationAwareLogger.WARN_INT, msg, arguments, null);
    }

    @Override
    public void warn(String msg, Throwable t) {
        this.logger.log(null, FQCN, LocationAwareLogger.WARN_INT, msg, null, t);
    }

    @Override
    public void error(String msg, Object... arguments) {
        this.logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, msg, arguments, null);
    }

    @Override
    public void error(String msg, Throwable t) {
        this.logger.log(null, FQCN, LocationAwareLogger.ERROR_INT, msg, null, t);
    }

    @Override
    public boolean isTraceEnabled() {
        return this.logger.isTraceEnabled();
    }

    @Override
    public boolean isDebugEnabled() {
        return this.logger.isDebugEnabled();
    }

    @Override
    public boolean isInfoEnabled() {
        return this.logger.isInfoEnabled();
    }

    @Override
    public boolean isWarnEnabled() {
        return this.logger.isWarnEnabled();
    }

    @Override
    public boolean isErrorEnabled() {
        return this.logger.isErrorEnabled();
    }

}
