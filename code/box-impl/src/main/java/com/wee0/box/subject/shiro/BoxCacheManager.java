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
import org.apache.shiro.cache.Cache;
import org.apache.shiro.cache.CacheException;
import org.apache.shiro.cache.CacheManager;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:36
 * @Description 框架缓存管理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxCacheManager implements CacheManager {

    private final Map<String, BoxCacheAdapter> DATA = new HashMap<>(128);

    @Override
    public <K, V> Cache<K, V> getCache(String name) throws CacheException {
        if (null == name)
            return null;
        BoxCacheAdapter _cacheAdapter = this.DATA.get(name);
        if (null == _cacheAdapter) {
            synchronized (this.DATA) {
                _cacheAdapter = this.DATA.get(name);
                if (null == _cacheAdapter) {
                    ICache _cache = com.wee0.box.cache.CacheManager.impl().getCache(name);
                    _cacheAdapter = new BoxCacheAdapter(_cache);
                }
            }
        }
        return _cacheAdapter;
    }

}
