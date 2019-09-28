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

package com.wee0.box.cache.redis;

import com.wee0.box.cache.ICache;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.util.shortcut.CheckUtils;
import org.springframework.util.SerializationUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.util.Pool;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 9:02
 * @Description 基于Redis的缓存对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class RedisCache implements ICache {

    // 0表示永不过期。
    private static final int EXPIRE_NEVER = 0;
    // 默认的过期时间
    private static final int DEF_EXPIRE = EXPIRE_NEVER;

    // 缓存标识
    private final String ID;
    // 键名匹配模式
    private final String KEYS_PATTERN;
    // redis客户端连接池
    private final Pool<Jedis> POOL;

    RedisCache(String id, Pool pool) {
        this.ID = CheckUtils.checkNotTrimEmpty(id, "id can't be empty!");
        this.POOL = CheckUtils.checkNotNull(pool, "pool can't be null!");
        this.KEYS_PATTERN = this.ID + ":*";
    }

    @Override
    public String getId() {
        return this.ID;
    }

    @Override
    public void put(String key, Object value) {
        put(key, value, DEF_EXPIRE);
    }

    @Override
    public void put(String key, Object value, int expire) {
        if (null == key)
            return;
        if (null == value) {
            remove(key);
            return;
        }
        try (Jedis _client = POOL.getResource();) {
            final String _keyName = convertKeyName(key);
            final byte[] _data = SerializationUtils.serialize(value);
            if (EXPIRE_NEVER == expire) {
                _client.set(_keyName.getBytes(), _data);
            } else {
                _client.setex(_keyName.getBytes(), expire, _data);
            }
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Object get(String key) {
        return get(key, DEF_EXPIRE);
    }

    @Override
    public Object get(String key, int expire) {
        if (null == key)
            return null;
        Object _result = null;
        try (Jedis _client = POOL.getResource();) {
            final byte[] _keyName = convertKeyName(key).getBytes();
            byte[] _data = _client.get(_keyName);
            // 如果有设置过期时间,则更新过期时间
            if (EXPIRE_NEVER != expire)
                _client.expire(_keyName, expire);
            if (null == _data)
                return null;
//			System.out.println( "ClassLoader:" + RedisCache.class.getClassLoader() );
//			System.out.println( "threadClassLoader:" + Thread.currentThread().getContextClassLoader() );
//			System.out.println( "systemClassLoader:" + ClassLoader.getSystemClassLoader() );
            _result = SerializeUtils.deserialize(_data);
        } catch (Exception e) {
            if (e instanceof IOException || e instanceof NullPointerException)
                remove(key);
            throw new BoxRuntimeException(e);
        }
        return null == _result ? null : _result;
    }

    @Override
    public Object remove(String key) {
        Object _result = null;
        if (null != key) {
            try (Jedis _client = POOL.getResource();) {
                final String _realKey = convertKeyName(key);
                if (_client.exists(_realKey)) {
                    byte[] _data = _client.get(_realKey.getBytes());
                    _result = SerializeUtils.deserialize(_data);
                    _client.del(_realKey);
                }
            } catch (Exception e) {
                throw new BoxRuntimeException(e);
            }
        }
        return _result;

    }

    @Override
    public void remove(Set<String> keys) {
        if (null == keys || 1 > keys.size())
            return;
        try (Jedis _client = POOL.getResource();) {
            String[] _delKeys = new String[keys.size()];
            int _i = 0;
            for (String _key : keys) {
                _delKeys[_i++] = convertKeyName(_key);
            }
            _client.del(_delKeys);
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public void clear() {
        try (Jedis _client = POOL.getResource();) {
//            _client.del(this.ID + ":*");
            String[] _keys = _client.keys(this.KEYS_PATTERN).toArray(new String[]{});
            _client.del(_keys);
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public int size() {
        try (Jedis _client = POOL.getResource();) {
            return _client.keys(this.KEYS_PATTERN).size();
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Set<String> keys() {
        try (Jedis _client = POOL.getResource();) {
            Set<String> _keys = _client.keys(this.KEYS_PATTERN);
            Set<String> _result = new HashSet<String>(_keys.size());
            final int _prefixLen = this.ID.length() + 3;
            for (String _key : _keys) {
                _result.add(_key.substring(_prefixLen));
            }
            return _result;
        } catch (Exception e) {
            throw new BoxRuntimeException(e);
        }
    }

    @Override
    public Collection<Object> values() {
        throw new BoxRuntimeException("This method is not yet implemented.");
    }

    @Override
    public String toString() {
        StringBuilder _sb = new StringBuilder();
        _sb.append("{");
        _sb.append("type:").append(getClass().getSimpleName());
        _sb.append(",id:").append(this.ID);
        _sb.append(",pool:").append(this.POOL);
        _sb.append("}");
        return _sb.toString();
    }

    /**
     * 转换键名
     *
     * @param name 原始键名
     * @return 新键名
     */
    private String convertKeyName(String name) {
        return this.ID + ":S:" + name;
    }

    /**
     * 解析键名称，获取到对应的region与key。
     *
     * @param key 通过getKeyName获取到的键名
     * @return region与key
     */
    static String[] parseKey(String key) {
        int _ndx = key.indexOf(":S:");
        String _region = key.substring(0, _ndx);
        String _key = key.substring(_ndx + 3);
        return new String[]{_region, key};
    }

}
