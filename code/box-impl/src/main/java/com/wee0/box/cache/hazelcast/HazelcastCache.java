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

package com.wee0.box.cache.hazelcast;

import com.hazelcast.core.IMap;
import com.hazelcast.query.Predicates;
import com.wee0.box.cache.ICache;

import java.util.Collection;
import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/5 7:16
 * @Description 基于Hazelcast的缓存对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class HazelcastCache implements ICache {

    // 缓存标识
    private final String ID;
    // 此缓存容器中缓存元素的默认过期时间。
    private final int EXPIRE;

    private final IMap DATA;

    // 默认的最大元素数量
    private final int DEF_MAX_SIZE = 10_000;

    // 默认的过期时间:2天1夜。
    private final int DEF_EXPIRE = 129600; // 3600 * 36 ;

    // 缓存时效 1天
    private final int EXPIRE_DAY = 3600 * 24;

    HazelcastCache(IMap<String, Object> data) {
        this.DATA = data;
        this.ID = this.DATA.getName();
        this.EXPIRE = DEF_EXPIRE;
    }

    @Override
    public String getId() {
        return this.ID;
    }

    @Override
    public void put(String key, Object value) {
        this.DATA.put(key, value, this.EXPIRE, TimeUnit.SECONDS);
    }

    @Override
    public void put(String key, Object value, int expire) {
        this.DATA.put(key, value, expire, TimeUnit.SECONDS);
    }

    @Override
    public boolean exists(String key) {
        if (null != key) {
            return this.DATA.containsKey(key);
        }
        return false;
    }

    @Override
    public Object get(String key) {
        return this.DATA.get(key);
    }

    @Override
    public Object get(String key, int expire) {
        this.DATA.setTtl(key, expire, TimeUnit.SECONDS);
        return this.DATA.get(key);
    }

    @Override
    public Object remove(String key) {
        return this.DATA.remove(key);
    }

    @Override
    public void remove(Set<String> keys) {
//        throw new BoxRuntimeException("This method is not yet implemented.");
        this.DATA.removeAll(Predicates.in("__key", keys.toArray(new String[]{})));
    }

    @Override
    public void clear() {
        this.DATA.clear();
//        this.DATA.removeAll(Predicates.alwaysTrue());
    }

    @Override
    public int size() {
        return this.DATA.size();
    }

    @Override
    public Set<String> keys() {
        return this.DATA.keySet();
    }

    @Override
    public Collection<Object> values() {
        return this.DATA.values();
    }
}
