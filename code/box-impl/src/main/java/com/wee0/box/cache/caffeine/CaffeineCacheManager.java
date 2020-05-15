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

import com.wee0.box.cache.ICache;
import com.wee0.box.cache.ICacheManager;

import java.io.ObjectStreamException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 8:10
 * @Description 基于Caffeine的缓存对象管理器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class CaffeineCacheManager implements ICacheManager {

    private final ConcurrentHashMap<String, ICache> DATA = new ConcurrentHashMap<>(128);

    @Override
    public ICache getCache(String id) {
        if (null == id)
            return null;
        ICache _result = this.DATA.get(id);
        if (null == _result) {
            _result = new CaffeineCache(id);
            ICache _r = this.DATA.putIfAbsent(id, _result);
            if (null != _r)
                _result = _r;
        }
        return _result;
    }

    @Override
    public ICache getDefaultCache() {
        return getCache(DEF_CACHE_NAME);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private CaffeineCacheManager() {
        if (null != CaffeineCacheManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class CaffeineCacheManagerHolder {
        private static final CaffeineCacheManager _INSTANCE = new CaffeineCacheManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return CaffeineCacheManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static CaffeineCacheManager me() {
        return CaffeineCacheManagerHolder._INSTANCE;
    }


}
