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

import com.wee0.box.BoxConfig;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:36
 * @Description 日志上下文对象入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class LoggerContext {

    // 实现类实例
    private static final ILoggerContext IMPL = BoxConfig.impl().getInterfaceImpl(ILoggerContext.class);

    /**
     * 设置键值对数据
     *
     * @param key   键名
     * @param value 键值
     */
    public static void put(String key, String value) {
        IMPL.put(key, value);
    }

    /**
     * 删除指定键名数据
     *
     * @param key 键名
     */
    public static void remove(String key) {
        IMPL.remove(key);
    }

    /**
     * 清空键值对数据
     */
    public static void clear() {
        IMPL.clear();
    }

}
