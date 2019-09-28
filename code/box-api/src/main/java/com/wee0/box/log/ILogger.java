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

package com.wee0.box.log;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:58
 * @Description 日志记录器
 * <pre>
 * 当前版本采用slf4j消息格式规范，消息文本中使用{}作为参数占位符。
 * 示例：log.trace("test p1:{}, p2:{}.", 1, 2 );
 * 输出：test p1:1, p2:2.
 * </pre>
 **/
public interface ILogger {

    /**
     * @return 日志记录者名称
     */
    String getName();

    /**
     * 最低级别的追踪日志
     *
     * @param msg       消息
     * @param arguments 消息参数
     */
    void trace(String msg, Object... arguments);

    /**
     * 最低级别的追踪日志
     *
     * @param msg 消息
     * @param t   异常对象
     */
    void trace(String msg, Throwable t);

    /**
     * 开发调试过程中建议使用的调试日志
     *
     * @param msg       消息
     * @param arguments 消息参数
     */
    void debug(String msg, Object... arguments);

    /**
     * 开发调试过程中建议使用的调试日志
     *
     * @param msg 消息
     * @param t   异常对象
     */
    void debug(String msg, Throwable t);

    /**
     * 需要向用户展示的日志
     *
     * @param msg       消息
     * @param arguments 消息参数
     */
    void info(String msg, Object... arguments);

    /**
     * 需要向用户展示的日志
     *
     * @param msg 消息
     * @param t   异常对象
     */
    void info(String msg, Throwable t);

    /**
     * 警告日志
     *
     * @param msg       消息
     * @param arguments 消息参数
     */
    void warn(String msg, Object... arguments);

    /**
     * 警告日志
     *
     * @param msg 消息
     * @param t   异常对象
     */
    void warn(String msg, Throwable t);

    /**
     * 错误日志
     *
     * @param msg       消息
     * @param arguments 消息参数
     */
    void error(String msg, Object... arguments);

    /**
     * 错误日志
     *
     * @param msg 消息
     * @param t   异常对象
     */
    void error(String msg, Throwable t);

    /**
     * 跟踪信息是否开启
     *
     * @return 是否开启
     */
    boolean isTraceEnabled();

    /**
     * 调试信息是否开启
     *
     * @return 是否开启
     */
    boolean isDebugEnabled();

    /**
     * 普通信息是否开启
     *
     * @return 是否开启
     */
    boolean isInfoEnabled();

    /**
     * 警告信息是否开启
     *
     * @return 是否开启
     */
    boolean isWarnEnabled();

    /**
     * 错误信息是否开启
     *
     * @return 是否开启
     */
    boolean isErrorEnabled();

}
