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

import com.wee0.box.cache.CacheManager;
import com.wee0.box.subject.ITokenHelper;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/23 9:12
 * @Description boxToken管理对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxTokenManager {

    // 默认令牌加密算法：DES。
    private final String DEF_TOKEN_ALGORITHM = ITokenHelper.ALGORITHM_DES;
    // 默认令牌密钥
    private final String DEF_TOKEN_SECRET = "1a3c5e7g";
    // 默认令牌相关数据缓存键名前缀
    private final String DEF_TOKEN_KEY_PREFIX = "BoxToken_";

    // 加解密令牌使用的算法
    private String tokenAlgorithm = DEF_TOKEN_ALGORITHM;
    // 加解密令牌使用的密钥
    private String tokenSecret = DEF_TOKEN_SECRET;
    // 令牌相关数据缓存键名前缀
    private String tokenKeyPrefix = DEF_TOKEN_KEY_PREFIX;

    // token最大生存时间，单位：毫秒。默认：2,592,000,000 milliseconds = 30 day
    private long tokenMaxLifeTime = 2592000000L;
    // token最大空闲时间，单位：秒。默认：3,600 seconds = 1 hour
    // 当token超过最大生存时间之后，如果处于活跃状态，则可以继续使用到满足最大空闲时间之后失效。
    private int tokenMaxIdleTime = 3600;

    /**
     * 是否是不活跃的令牌
     *
     * @param token 令牌
     * @return true / false
     */
    boolean isInactive(String token) {
        final String _tokenCacheKey = this.tokenKeyPrefix + token;
        return !CacheManager.impl().getDefaultCache().exists(_tokenCacheKey);
    }

    /**
     * 令牌保活
     *
     * @param token 令牌
     */
    void keepAlive(String token) {
        final String _tokenCacheKey = this.tokenKeyPrefix + token;
        // 记录当前访问时间，更新缓存有效时间。
        CacheManager.impl().getDefaultCache().put(_tokenCacheKey, System.currentTimeMillis(), this.tokenMaxIdleTime);
    }

    String createToken(String userId) {
        // 生成token.
        Map<String, Object> _tokenData = new HashMap<>(2);
        _tokenData.put("userId", userId);
        _tokenData.put("expireTime", System.currentTimeMillis() + this.tokenMaxLifeTime);
        return getTokenHelper().encode(_tokenData, this.tokenSecret);
    }

    /**
     * 检查令牌对应的用户标识是否有效，有效则返回正常的用户标识。
     *
     * @param token 令牌
     * @return 用户标识
     */
    String checkTokenUserId(String token) {
        // 解析token，取出 userId
        Map<String, Object> _tokenData = getTokenHelper().decode(token, this.tokenSecret);
        if (null == _tokenData || _tokenData.isEmpty())
            throw new RuntimeException("无效的令牌: " + token);
        String _userId = (String) _tokenData.get("userId");
        if (null == _userId || 0 == (_userId = _userId.trim()).length())
            throw new RuntimeException("无效的用户标识令牌: " + token);

        if (isInactive(token)) {
            // 判断是否不活跃，且超过最大允许使用时长。
            long _expireTime = Long.parseLong(_tokenData.get("expireTime").toString());
            if (System.currentTimeMillis() > _expireTime) {
                throw new RuntimeException("过期的用户标识令牌: " + token);
            }
        }
        // 记录当前访问时间，更新缓存有效时间。
        keepAlive(token);
        return _userId;
    }

    // 获取使用的令牌操作助手对象
    ITokenHelper getTokenHelper() {
        return ShiroSubjectContext.me().getTokenHelper();
    }

    // getter and setter

    public String getTokenAlgorithm() {
        return tokenAlgorithm;
    }

    public void setTokenAlgorithm(String tokenAlgorithm) {
        this.tokenAlgorithm = tokenAlgorithm;
    }

    public String getTokenSecret() {
        return tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        if (null == tokenSecret || 0 == (tokenSecret = tokenSecret.trim()).length())
            tokenSecret = DEF_TOKEN_SECRET;
        if (tokenSecret.length() < 8)
            throw new IllegalArgumentException("tokenSecret length cannot be less than 8.");
        this.tokenSecret = tokenSecret;
    }

    public String getTokenKeyPrefix() {
        return tokenKeyPrefix;
    }

    public void setTokenKeyPrefix(String tokenKeyPrefix) {
        this.tokenKeyPrefix = tokenKeyPrefix;
    }

    public long getTokenMaxLifeTime() {
        return tokenMaxLifeTime;
    }

    public void setTokenMaxLifeTime(long tokenMaxLifeTime) {
        this.tokenMaxLifeTime = tokenMaxLifeTime;
    }

    public int getTokenMaxIdleTime() {
        return tokenMaxIdleTime;
    }

    public void setTokenMaxIdleTime(int tokenMaxIdleTime) {
        if (tokenMaxIdleTime < 1000) return;
        this.tokenMaxIdleTime = tokenMaxIdleTime / 1000;
    }

}
