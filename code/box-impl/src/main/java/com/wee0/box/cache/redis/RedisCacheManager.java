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
import com.wee0.box.cache.ICacheManager;
import com.wee0.box.impl.SimpleBoxConfig;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.ObjectUtils;
import com.wee0.box.util.shortcut.PropertiesUtils;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.util.Pool;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectStreamException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 9:18
 * @Description 基于Redis的缓存对象管理器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class RedisCacheManager implements ICacheManager {

    // 配置文件
    static final String DEF_CONFIG_FILE = "config/redis.properties";

    // 自定义配置项名称
    private static final String KEY_MODE = "redis.mode"; // basic, shard, cluster, sentinel
    private static final String KEY_HOST = "redis.host";
    private static final String KEY_PORT = "redis.port";
    private static final String KEY_PASSWORD = "redis.password";
    private static final String KEY_DATABASE = "redis.database";
    private static final String KEY_TIMEOUT = "redis.timeout";

    // 自定义配置项默认值
    private static final String DEF_MODE = "basic";
    private static final String DEF_HOST = "localhost";
    private static final int DEF_PORT = 6379;
    private static final int DEF_DATABASE = 0;
    private static final int DEF_TIMEOUT = 2000;

    // 缓存数据容器
    private final Map<String, ICache> DATA = new ConcurrentHashMap<>(128);

    // redis客户端连接池
    private Pool<Jedis> POOL;

    @Override
    public ICache getCache(String id) {
        if (null == id)
            return null;
        ICache _result = this.DATA.get(id);
        if (null == _result) {
            _result = new RedisCache(id, POOL);
            ICache _r = this.DATA.putIfAbsent(id, _result);
            if (null != _r)
                _result = _r;
        }
        return _result;
    }

    @Override
    public void init() {
        String _host = DEF_HOST;
        int _port = DEF_PORT;
        String _password = null;
        int _timeout = DEF_TIMEOUT;
        int _database = DEF_DATABASE;

        JedisPoolConfig _config = createDefaultConfig();
        try (InputStream _inStream = SimpleBoxConfig.me().getResourceAsStream(DEF_CONFIG_FILE);) {
            Map<String, String> _propertiesMap = PropertiesUtils.loadToMap(_inStream);
            ObjectUtils.impl().setProperties(_config, _propertiesMap);
            if (_propertiesMap.containsKey(KEY_HOST)) {
                _host = CheckUtils.checkNotTrimEmpty(_propertiesMap.get(KEY_HOST), "%s can't be empty!", KEY_HOST);
            }
            if (_propertiesMap.containsKey(KEY_PORT)) {
                String _portString = CheckUtils.checkTrimEmpty(_propertiesMap.get(KEY_PORT), null);
                if (null != _portString)
                    _port = Integer.parseInt(_portString);
            }
            if (_propertiesMap.containsKey(KEY_PASSWORD)) {
                _password = CheckUtils.checkTrimEmpty(_propertiesMap.get(KEY_PASSWORD), null);
            }
            if (_propertiesMap.containsKey(KEY_DATABASE)) {
                String _dbString = CheckUtils.checkTrimEmpty(_propertiesMap.get(KEY_DATABASE), null);
                if (null != _dbString)
                    _database = Integer.parseInt(_dbString);
            }
            if (_propertiesMap.containsKey(KEY_TIMEOUT)) {
                String _timeoutString = CheckUtils.checkTrimEmpty(_propertiesMap.get(KEY_TIMEOUT), null);
                if (null != _timeoutString)
                    _timeout = Integer.parseInt(_timeoutString);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.POOL = new JedisPool(_config, _host, _port, _timeout, _password, _database);
    }

    @Override
    public void destroy() {
        if (null != this.POOL)
            this.POOL.destroy();
    }

    private JedisPoolConfig createDefaultConfig() {
        JedisPoolConfig _config = new JedisPoolConfig();
        // 当连接池用尽后,调用者是否要等待，这个参数是和maxWaitMillis对应的，只有当此参数为true时，maxWaitMillis才会生效。
        _config.setBlockWhenExhausted(true);
        // 如果BlockWhenExhausted为true，获取连接时的最大等待毫秒数,如果超时就抛异常, 小于零:阻塞不确定的时间, 默认-1。
        _config.setMaxWaitMillis(3000);
        // 最大连接数, 默认8个
        _config.setMaxTotal(16);
        // 最大空闲连接数, 默认8个
        _config.setMaxIdle(8);
        // 最小空闲连接数, 默认0
        _config.setMinIdle(0);
        // 在空闲时检查有效性
        _config.setTestWhileIdle(true);
        // 在获取连接的时候检查有效性
        _config.setTestOnBorrow(true);
        // 在归还连接的时候检查有效性
        _config.setTestOnReturn(false);
        // 每次逐出检查时 逐出的最大数目 如果为负数就是 : 1/abs(n), 默认3
        _config.setNumTestsPerEvictionRun(3);
        // 逐出连接的最小空闲时间 默认1800000毫秒(30分钟)，达到此值后空闲连接将被移除
        _config.setMinEvictableIdleTimeMillis(1800000);
        // 空闲连接检测时，每次的采样数
        _config.setNumTestsPerEvictionRun(3);
        // 对象空闲多久后逐出, 当空闲时间>该值 且 空闲连接>最大空闲数 时直接逐出,不再根据MinEvictableIdleTimeMillis判断 (默认逐出策略)
        _config.setSoftMinEvictableIdleTimeMillis(1800000);
        // 逐出扫描的时间间隔(毫秒) 如果为负数,则不运行逐出线程, 默认-1
        _config.setTimeBetweenEvictionRunsMillis(-1);
        // 是否启用后进先出, 默认true
        _config.setLifo(false);
        // 是否开启jmx监控，如果应用开启了jmx端口并且jmxEnabled设置为true，就可以通过其做监控统计
        _config.setJmxEnabled(false);
        _config.setJmxNamePrefix("redis_pool");
        return _config;
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private RedisCacheManager() {
        if (null != RedisCacheManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        // 初始化方法
        init();
    }

    // 当前对象唯一实例持有者。
    private static final class RedisCacheManagerHolder {
        private static final RedisCacheManager _INSTANCE = new RedisCacheManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return RedisCacheManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static RedisCacheManager me() {
        return RedisCacheManagerHolder._INSTANCE;
    }


}
