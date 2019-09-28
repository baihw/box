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

package com.wee0.box.sql.ds.druid;

import com.alibaba.druid.filter.logging.LogFilter;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:51
 * @Description 自定义的日志过滤器
 * <pre>
 * 补充说明
 * </pre>
 **/
final class DruidLogFilter extends LogFilter {

    // 日志对象
    // private ILogger dataSourceLogger = LoggerFactory.getLogger( dataSourceLoggerName );
    private ILogger connectionLogger = LoggerFactory.getLogger(connectionLoggerName);
    private ILogger statementLogger = LoggerFactory.getLogger(statementLoggerName);
    private ILogger resultSetLogger = LoggerFactory.getLogger(resultSetLoggerName);

    DruidLogFilter() {
        super.setStatementExecutableSqlLogEnable(true);
        super.setStatementCreateAfterLogEnabled(false);
        super.setStatementCloseAfterLogEnabled(false);
        super.setStatementPrepareAfterLogEnabled(false);
        super.setStatementPrepareCallAfterLogEnabled(false);
        super.setStatementExecuteQueryAfterLogEnabled(false);
        super.setStatementExecuteBatchAfterLogEnabled(false);
        super.setStatementExecuteUpdateAfterLogEnabled(false);
        super.setDataSourceLogEnabled(false);
    }

    @Override
    protected void connectionLog(String message) {
        connectionLogger.debug(formatMessage(message));
    }

    @Override
    protected void statementLog(String message) {
        statementLogger.debug(formatMessage(message));
    }

    @Override
    protected void statementLogError(String message, Throwable error) {
        statementLogger.error(formatMessage(message));
    }

    @Override
    protected void resultSetLog(String message) {
        resultSetLogger.debug(formatMessage(message));
    }

    @Override
    protected void resultSetLogError(String message, Throwable error) {
        resultSetLogger.error(formatMessage(message));
    }

    @Override
    public String getDataSourceLoggerName() {
        return dataSourceLoggerName;
    }

    @Override
    public void setDataSourceLoggerName(String loggerName) {
        this.dataSourceLoggerName = loggerName;
    }

    @Override
    public String getConnectionLoggerName() {
        return connectionLoggerName;
    }

    @Override
    public void setConnectionLoggerName(String loggerName) {
        this.connectionLoggerName = loggerName;
        this.connectionLogger = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public String getStatementLoggerName() {
        return statementLoggerName;
    }

    @Override
    public void setStatementLoggerName(String loggerName) {
        this.statementLoggerName = loggerName;
        this.statementLogger = LoggerFactory.getLogger(loggerName);
    }

    @Override
    public String getResultSetLoggerName() {
        return resultSetLoggerName;
    }

    @Override
    public void setResultSetLoggerName(String loggerName) {
        this.resultSetLoggerName = loggerName;
        this.resultSetLogger = LoggerFactory.getLogger(loggerName);
    }

    public boolean isConnectionLogErrorEnabled() {
        return true;
    }

    @Override
    public boolean isDataSourceLogEnabled() {
        return false;
    }

    @Override
    public boolean isConnectionLogEnabled() {
        return false;
    }

    @Override
    public boolean isStatementLogEnabled() {
        return true;
        // return statementLogger.isDebugEnabled() && super.isStatementLogEnabled();
    }

    @Override
    public boolean isResultSetLogEnabled() {
        return false;
    }

    @Override
    public boolean isResultSetLogErrorEnabled() {
        return true;
    }

    @Override
    public boolean isStatementLogErrorEnabled() {
        return true;
    }

    @Override
    public boolean isStatementParameterSetLogEnabled() {
        return false;
    }

    private static String formatMessage(String message) {
        return message.replace('\n', ' ');
    }
}
