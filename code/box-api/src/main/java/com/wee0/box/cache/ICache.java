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

package com.wee0.box.cache;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 8:02
 * @Description 缓存对象规范接口
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ICache {

    /**
     * 获取缓存对象标识
     *
     * @return 标识
     */
    String getId();

    /**
     * 存储指定键名数据。
     *
     * @param key   缓存名称
     * @param value 缓存值
     */
    public void put(String key, Object value);

    /**
     * 存储指定键名数据。
     *
     * @param key    键名
     * @param value  数据,如果为null,则删除缓存数据.
     * @param expire 过期时间，单位：秒，最大不能超过30天：60 * 60 * 24 * 30
     */
    public void put(String key, Object value, int expire);

    /**
     * 获取指定名称的缓存值
     *
     * @param key 缓存名称
     * @return 缓存值
     */
    public Object get(String key);

    /**
     * 获取指定键名对应的数据，并更新过期时间。
     *
     * @param key    键名
     * @param expire 过期时间，单位：秒，最大不能超过30天：60 * 60 * 24 * 30
     * @return 缓存的数据
     */
    public Object get(String key, int expire);

    /**
     * 删除指定键名的缓存
     *
     * @param key 缓存名称
     * @return 缓存值
     */
    public Object remove(String key);

    /**
     * 删除指定键名集合中包含的键名数据
     *
     * @param keys 键名集合
     */
    public void remove(Set<String> keys);

    /**
     * 清理所有缓存数据
     */
    public void clear();

    /**
     * 缓存对象数量
     *
     * @return 数量
     */
    public int size();

    /**
     * 缓存键名集合
     *
     * @return 键名集合
     */
    public Set<String> keys();

    /**
     * 缓存值集合
     *
     * @return 值集合
     */
    public Collection<Object> values();

}
