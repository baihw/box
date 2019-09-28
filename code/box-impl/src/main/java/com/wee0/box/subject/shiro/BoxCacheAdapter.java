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

package com.wee0.box.subject.shiro;

import com.wee0.box.cache.ICache;
import com.wee0.box.util.shortcut.CheckUtils;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;

import java.util.Collection;
import java.util.Collections;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:39
 * @Description 框架缓存对象适配器
 * <pre>
 * 补充说明
 * </pre>
 **/
final class BoxCacheAdapter<K, V> implements Cache<Object, Object> {

    // 框架缓存对象
    private final ICache CACHE;

    BoxCacheAdapter(ICache cache) {
        this.CACHE = CheckUtils.checkNotNull(cache, "cache can't be null!");
    }

    @Override
    public Object get(Object key) throws CacheException {
        return this.CACHE.get(convertKey(key));
    }

    @Override
    public Object put(Object key, Object value) throws CacheException {
        this.CACHE.put(convertKey(key), value);
        return value;
    }

    @Override
    public Object remove(Object key) throws CacheException {
        return this.CACHE.remove(convertKey(key));
    }

    @Override
    public void clear() throws CacheException {
        this.CACHE.clear();
    }

    @Override
    public int size() {
        return this.CACHE.size();
    }

    @Override
    public Set<Object> keys() {
        return Collections.unmodifiableSet(this.CACHE.keys());
    }

    @Override
    public Collection<Object> values() {
        return this.CACHE.values();
    }

    @Override
    public String toString() {
        return this.CACHE.toString();
    }

    private String convertKey(Object key) {
        if (key instanceof String)
            return (String) key;
        return key.toString();
//        return JsonUtils.writeToString(key);
    }
}
