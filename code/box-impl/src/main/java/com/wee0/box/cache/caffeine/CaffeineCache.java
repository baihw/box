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

package com.wee0.box.cache.caffeine;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import com.wee0.box.cache.ICache;
import com.wee0.box.exception.BoxRuntimeException;

import java.util.Collection;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 8:10
 * @Description 基于Caffeine的缓存对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class CaffeineCache implements ICache {

    // 缓存标识
    private final String ID;
    // 此缓存容器中缓存元素的默认过期时间。
    private final int EXPIRE;

    // 缓存数据存储容器
    private final Cache<String, CaffeineCacheItem> DATA;

    // 默认的最大元素数量
    private final int DEF_MAX_SIZE = 10_000;

    // 默认的过期时间:2天1夜。
    private final int DEF_EXPIRE = 129600; // 3600 * 36 ;

    // 缓存时效 1天
    private final int EXPIRE_DAY = 3600 * 24;

    /**
     * 使用指定标识创建实例
     *
     * @param id 标识
     */
    CaffeineCache(String id) {
        this.ID = id;
        this.EXPIRE = DEF_EXPIRE;
        Caffeine<Object, Object> _builder = Caffeine.newBuilder().maximumSize(DEF_MAX_SIZE);
        _builder.expireAfter(new CaffeineCacheExpiry());
//        _builder.removalListener(new RemovalListener<String, CaffeineCacheItem>() {
//            @Override
//            public void onRemoval(String key, CaffeineCacheItem value, RemovalCause cause) {
//                System.out.println("remove:" + key + ", cause:" + cause);
//            }
//        });
        this.DATA = _builder.build();
    }

    @Override
    public String getId() {
        return this.ID;
    }

    @Override
    public void put(String key, Object value) {
        this.put(key, value, this.EXPIRE);
    }

    @Override
    public void put(String key, Object value, int expire) {
        if (null == key)
            return;
        if (null == value)
            remove(key);
        CaffeineCacheItem _item = new CaffeineCacheItem(value, expire);
        this.DATA.put(key, _item);
    }

    @Override
    public Object get(String key) {
        if (null == key)
            return null;
        CaffeineCacheItem _item = this.DATA.getIfPresent(key);
        if (null == _item)
            return null;
        if (null == _item.value()) {
            this.DATA.invalidate(key);
            return null;
        }
        _item.updateExpire();
        return _item.value();
    }

    @Override
    public Object get(String key, int expire) {
        if (null == key)
            return null;
        CaffeineCacheItem _item = this.DATA.getIfPresent(key);
        if (null == _item)
            return null;
        if (null == _item.value()) {
            this.DATA.invalidate(key);
            return null;
        }
        _item.updateExpire(expire);
        return _item.value();
    }

    @Override
    public Object remove(String key) {
        if (null == key)
            return null;
        Object _result = this.DATA.getIfPresent(key);
        if (null != _result)
            this.DATA.invalidate(key);
        return _result;
    }

    @Override
    public void remove(Set<String> keys) {
        this.DATA.invalidateAll(keys);
    }

    @Override
    public void clear() {
        this.DATA.invalidateAll();
    }

    @Override
    public int size() {
        return this.DATA.asMap().size();
    }

    @Override
    public Set<String> keys() {
        return this.DATA.asMap().keySet();
    }

    @Override
    public Collection<Object> values() {
        throw new BoxRuntimeException("This method is not yet implemented.");
//        return Collections.singleton(this.DATA.asMap().values());
    }

    @Override
    public String toString() {
        StringBuilder _sb = new StringBuilder();
        _sb.append("{");
        _sb.append("type:").append(getClass().getSimpleName());
        _sb.append(",id:").append(this.ID);
        _sb.append(",size:").append(size());
        _sb.append("}");
        return _sb.toString();
    }
}
