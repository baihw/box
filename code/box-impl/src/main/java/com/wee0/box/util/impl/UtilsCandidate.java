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

package com.wee0.box.util.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.UndeclaredThrowableException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/15 8:19
 * @Description 尚末进入API中标准化推广的候选工具类方法
 * <pre>
 * 仅试验性使用，不提供任何兼容性或稳定性保证，项目开发人员请尽量不要使用此类。
 * </pre>
 **/
public final class UtilsCandidate {

    /**
     * 创建一个简单的本地缓存容器。替换策略为：先进先出。
     *
     * @param limit 容量
     * @param <K>   键类型
     * @param <V>   值类型
     * @return 缓存容器
     */
    public static <K, V> Map<K, V> createFifoCache(int limit) {
        return new LinkedHashMap<K, V>(limit, 0.75F) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > limit;
            }
        };
    }

    /**
     * 创建一个简单的本地缓存容器。替换策略为：最近最少使用。
     *
     * @param limit 容量
     * @param <K>   键类型
     * @param <V>   值类型
     * @return 缓存容器
     */
    public static <K, V> Map<K, V> createLruCache(int limit) {
        return new LinkedHashMap<K, V>(limit, 0.75F, true) {
            @Override
            protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
                return this.size() > limit;
            }
        };
    }

}
