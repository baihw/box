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
import com.wee0.box.cache.ICache;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.SqlHelper;
import com.wee0.box.subject.IBoxToken;
import com.wee0.box.subject.IPasswordToken;
import com.wee0.box.subject.SubjectContext;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:19
 * @Description 基于密码令牌的认证域
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxJdbcRealm extends AuthorizingRealm {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxJdbcRealm.class);

    // 默认名称
    private final String DEF_NAME = "boxJdbcRealm";
    // 默认令牌加密算法：DES，DESede，AES，Blowfish，RC2，RC4。
    private final String DEF_TOKEN_ALGORITHM = "DES";
    // 默认令牌密钥
    private final String DEF_TOKEN_SECRET = "1a3c5e7g";
    // 默认令牌相关数据缓存键名前缀
    private final String DEF_TOKEN_KEY_PREFIX = "BoxToken_";
    private final String DEF_QUERY_CODE_PREFIX = "BoxQueryCode_";

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

    // 验证码前缀
    private String queryCodePrefix;
    // 用户查询语句，接收2个参数: loginId, loginPwd。
    private String queryUser1;
    private String queryUser2;
    private String queryUser3;
    private String queryUser4;
    private String queryUser5;
    // 角色查询语句，接收1个参数：loginId。
    private String queryRole;
    // 权限查询语句，接收1个参数：loginId。
    private String queryPermission;

    @Override
    public String getName() {
        return DEF_NAME;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
//        return token instanceof IPasswordToken;
        return token instanceof IPasswordToken || token instanceof IBoxToken;
    }

    // 编码令牌
    private String tokenEncode(String userId) {
        String _tokenData = userId + "," + (System.currentTimeMillis() + this.tokenMaxLifeTime);
        Key _secretKey = new SecretKeySpec(this.tokenSecret.getBytes(), this.tokenAlgorithm);
        try {
            Cipher cipher = Cipher.getInstance(this.tokenAlgorithm);
            cipher.init(Cipher.ENCRYPT_MODE, _secretKey);
            byte[] _finalData = cipher.doFinal(_tokenData.getBytes());
            return DatatypeConverter.printHexBinary(_finalData);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            log.warn("tokenEncode ERROR!", e);
            return null;
        }
    }

    // 解码令牌
    private String tokenDecode(String token) {
        Key _secretKey = new SecretKeySpec(this.tokenSecret.getBytes(), this.tokenAlgorithm);
        try {
            Cipher cipher = Cipher.getInstance(this.tokenAlgorithm);
            cipher.init(Cipher.DECRYPT_MODE, _secretKey);
            byte[] _finalData = cipher.doFinal(DatatypeConverter.parseHexBinary(token));
            return new String(_finalData);
        } catch (NoSuchAlgorithmException | InvalidKeyException | NoSuchPaddingException | BadPaddingException | IllegalBlockSizeException e) {
            log.warn("tokenDecode ERROR!", e);
            return null;
        }
    }

    // 根据配置的用户标识查询语句，逐个查找匹配的用户标识
    private String _queryUserId(final Object[] loginParams) {
        String _userId = SqlHelper.impl().queryScalar(this.queryUser1, loginParams, String.class);
        if (null != _userId && 0 != (_userId = _userId.trim()).length())
            return _userId;
        if (null == this.queryUser2)
            return null;
        _userId = SqlHelper.impl().queryScalar(this.queryUser2, loginParams, String.class);
        if (null != _userId && 0 != (_userId = _userId.trim()).length())
            return _userId;
        if (null == this.queryUser3)
            return null;
        _userId = SqlHelper.impl().queryScalar(this.queryUser3, loginParams, String.class);
        if (null != _userId && 0 != (_userId = _userId.trim()).length())
            return _userId;
        if (null == this.queryUser4)
            return null;
        _userId = SqlHelper.impl().queryScalar(this.queryUser4, loginParams, String.class);
        if (null != _userId && 0 != (_userId = _userId.trim()).length())
            return _userId;
        if (null == this.queryUser5)
            return null;
        _userId = SqlHelper.impl().queryScalar(this.queryUser5, loginParams, String.class);
        if (null != _userId && 0 != (_userId = _userId.trim()).length())
            return _userId;
        return null;
    }

    // 根据登陆账号与验证码信息，从缓存中取用户标识。
    private String _queryUserIdByCode(String loginId, String loginPwd) {
        if (null == this.queryCodePrefix) return null;
        String _cacheKey = this.queryCodePrefix + "_" + loginId + "_" + loginPwd;
        Object _cacheVal = CacheManager.impl().getDefaultCache().get(_cacheKey);
        if (null == _cacheVal) return null;
        return _cacheVal.toString();
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String _userId = null;
        String _credentials = null;
        String _subjectToken = null;
        if (token instanceof IBoxToken) {
            log.trace("login by boxToken: {}", token);
            _credentials = ((IBoxToken) token).getToken();
            // 解析token，取出 userId
            String _tokenData = tokenDecode(_credentials);
            if (null == _tokenData || 0 == (_tokenData = _tokenData.trim()).length())
                throw new IncorrectCredentialsException("认证失败! 无效的令牌: " + _credentials);
            int _ndx = _tokenData.indexOf(',');
            _userId = _tokenData.substring(0, _ndx);
            if (null == _userId || 0 == (_userId = _userId.trim()).length())
                throw new IncorrectCredentialsException("认证失败! 无效的用户标识令牌: " + _credentials);

            final String _tokenCacheKey = this.tokenKeyPrefix + _credentials;
            ICache _cache = CacheManager.impl().getDefaultCache();
            if (!_cache.exists(_tokenCacheKey)) {
                // 判断是否不活跃，且超过最大允许使用时长。
                long _expireTime = Long.parseLong(_tokenData.substring(_ndx + 1));
                if (System.currentTimeMillis() > _expireTime) {
                    throw new IncorrectCredentialsException("认证失败! 过期的用户标识令牌: " + _credentials);
                }
            }
            // 记录当前访问时间，更新缓存有效时间。
            _cache.put(_tokenCacheKey, System.currentTimeMillis(), this.tokenMaxIdleTime);

        } else {
            log.trace("login by passwordToken: {}", token);
            IPasswordToken _passwordToken = (IPasswordToken) token;
            String _loginId = _passwordToken.getUsername();
            _credentials = _passwordToken.getPassword();

            // 先尝试从缓存中获取用户标识
            _userId = _queryUserIdByCode(_loginId, _credentials);
            // 再尝试执行数据库查询获取用户标识
            if (null == _userId || 0 == (_userId = _userId.trim()).length())
                _userId = _queryUserId(new Object[]{_loginId, _credentials});

            if (null == _userId || 0 == (_userId = _userId.trim()).length())
                throw new IncorrectCredentialsException("认证失败! loginId: " + _loginId);

            // 生成token.
            _subjectToken = tokenEncode(_userId);
        }

        ShiroSubject _subject = ((ShiroSubject) SubjectContext.getSubject());
        // 保存当前主体对象唯一标识，便于后期开发人员获取。
        _subject.setId(_userId);
        if (null != _subjectToken) {
            // 如果有新生成的令牌，设置到主体对象
            _subject.setToken(_subjectToken);
        }
        log.trace("after login: {}", _subject);

        AuthenticationInfo _info = new SimpleAuthenticationInfo(_userId, _credentials, getName());
        return _info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        String _userId = String.valueOf(principals.getPrimaryPrincipal());
        log.debug("load authorizationInfo for user: {}", _userId);

        SimpleAuthorizationInfo _info = new SimpleAuthorizationInfo();
        List<Map<String, Object>> _roles = SqlHelper.impl().queryMapList(this.queryRole, new Object[]{_userId});
        if (null == _roles || _roles.isEmpty()) {
            log.warn("roles is empty!");
        } else {
            _roles.forEach(roleMap -> {
                _info.addRole(String.valueOf(roleMap.values().iterator().next()));
            });
        }

        List<Map<String, Object>> _permissions = SqlHelper.impl().queryMapList(this.queryPermission, new Object[]{_userId});
        if (null == _permissions || _permissions.isEmpty()) {
            log.warn("permission is empty!");
        } else {
            _permissions.forEach(permissionMap -> {
                _info.addStringPermission(String.valueOf(permissionMap.values().iterator().next()));
            });
        }
        return _info;
    }

    @Override
    public String toString() {
        return getName();
    }

    // getter and setter

    public String getTokenSecret() {
        return this.tokenSecret;
    }

    public void setTokenSecret(String tokenSecret) {
        if (null == tokenSecret || 0 == (tokenSecret = tokenSecret.trim()).length())
            tokenSecret = DEF_TOKEN_SECRET;
        if (tokenSecret.length() < 8)
            throw new IllegalArgumentException("tokenSecret length cannot be less than 8.");
        this.tokenSecret = tokenSecret;
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

    public String getQueryCodePrefix() {
        return this.queryCodePrefix;
    }

    public void setQueryCodePrefix(String queryCodePrefix) {
        if (null == queryCodePrefix || 0 == (queryCodePrefix = queryCodePrefix.trim()).length())
            queryCodePrefix = DEF_QUERY_CODE_PREFIX;
        this.queryCodePrefix = queryCodePrefix;
    }

    public String getQueryUser1() {
        return queryUser1;
    }

    public void setQueryUser1(String queryUser1) {
        this.queryUser1 = queryUser1;
    }

    public String getQueryUser2() {
        return queryUser2;
    }

    public void setQueryUser2(String queryUser2) {
        this.queryUser2 = queryUser2;
    }

    public String getQueryUser3() {
        return queryUser3;
    }

    public void setQueryUser3(String queryUser3) {
        this.queryUser3 = queryUser3;
    }

    public String getQueryUser4() {
        return queryUser4;
    }

    public void setQueryUser4(String queryUser4) {
        this.queryUser4 = queryUser4;
    }

    public String getQueryUser5() {
        return queryUser5;
    }

    public void setQueryUser5(String queryUser5) {
        this.queryUser5 = queryUser5;
    }


    public String getQueryRole() {
        return queryRole;
    }

    public void setQueryRole(String queryRole) {
        this.queryRole = queryRole;
    }

    public String getQueryPermission() {
        return queryPermission;
    }

    public void setQueryPermission(String queryPermission) {
        this.queryPermission = queryPermission;
    }

}
