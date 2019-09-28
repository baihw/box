/*
 *   Copyright (c) 2019-present, wee0.com.
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 */

package com.wee0.box.log;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:28
 * @Description 日志上下文对象
 * <pre>
 * 用来存储日志信息中需要扩展的字段数据
 * </pre>
 **/
public interface ILoggerContext {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.log.slf4j.Slf4jLoggerContext";

    /**
     * 设置键值对数据
     *
     * @param key   键名
     * @param value 键值
     */
    void put(String key, String value);

    /**
     * 删除指定键名数据
     *
     * @param key 键名
     */
    void remove(String key);

    /**
     * 清空键值对数据
     */
    void clear();

}
