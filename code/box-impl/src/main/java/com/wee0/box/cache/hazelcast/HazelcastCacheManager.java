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

import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.wee0.box.cache.ICache;
import com.wee0.box.cache.ICacheManager;

import java.io.ObjectStreamException;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/1/5 7:12
 * @Description 基于Hazelcast的缓存对象管理器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class HazelcastCacheManager implements ICacheManager {

    private final ConcurrentHashMap<String, ICache> DATA = new ConcurrentHashMap<>(128);

    // Hazelcast Cluster Member.
    private HazelcastInstance hazelcast;

    @Override
    public ICache getCache(String id) {
        if (null == id)
            return null;
        ICache _result = this.DATA.get(id);
        if (null == _result) {
            IMap<String, Object> _map = this.hazelcast.getMap(id);
            _result = new HazelcastCache(_map);
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

    @Override
    public void init() {
        this.hazelcast = Hazelcast.newHazelcastInstance();
    }

    @Override
    public void destroy() {
        this.hazelcast.shutdown();
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private HazelcastCacheManager() {
        if (null != HazelcastCacheManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class HazelcastCacheManagerHolder {
        private static final HazelcastCacheManager _INSTANCE = new HazelcastCacheManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return HazelcastCacheManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static HazelcastCacheManager me() {
        return HazelcastCacheManagerHolder._INSTANCE;
    }

}
