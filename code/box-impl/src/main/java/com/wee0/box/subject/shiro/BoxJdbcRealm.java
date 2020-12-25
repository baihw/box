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
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.sql.SqlHelper;
import com.wee0.box.subject.IPasswordToken;
import com.wee0.box.subject.SubjectContext;
import org.apache.shiro.authc.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:19
 * @Description 基于密码令牌的认证域
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxJdbcRealm extends BoxCustomRealm {

    // 日志对象
    private static ILogger _LOG = LoggerFactory.getLogger(BoxJdbcRealm.class);

    // 默认名称
    private final String DEF_NAME = "boxJdbcRealm";

    // 查询码前缀
    private final String DEF_QUERY_CODE_PREFIX = "BoxQueryCode_";

    // 验证码前缀
    private String queryCodePrefix;
    // 用户查询语句，接收2个参数: loginId, loginPwd。
    private String queryUser1;
    private String queryUser2;
    private String queryUser3;
    private String queryUser4;
    private String queryUser5;


    @Override
    public String getName() {
        return DEF_NAME;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof IPasswordToken;
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
        _LOG.trace("check passwordToken: {}", token);
        IPasswordToken _passwordToken = (IPasswordToken) token;
        String _loginId = _passwordToken.getUsername();
        String _credentials = _passwordToken.getPassword();

        // 先尝试从缓存中获取用户标识
        String _userId = _queryUserIdByCode(_loginId, _credentials);
        // 再尝试执行数据库查询获取用户标识
        if (null == _userId || 0 == (_userId = _userId.trim()).length())
            _userId = _queryUserId(new Object[]{_loginId, _credentials});

        if (null == _userId || 0 == (_userId = _userId.trim()).length())
            throw new IncorrectCredentialsException("认证失败! loginId: " + _loginId);

        // 生成token.
        String _subjectToken = this.boxTokenManager.createToken(_userId);

        ShiroSubject _subject = ((ShiroSubject) SubjectContext.getSubject());
        // 保存当前主体对象唯一标识，便于后期开发人员获取。
        _subject.setId(_userId);
        if (null != _subjectToken) {
            // 如果有新生成的令牌，设置到主体对象
            _subject.setToken(_subjectToken);
        }
        _LOG.trace("checked: {}", _subject);

        AuthenticationInfo _info = new SimpleAuthenticationInfo(_userId, _credentials, getName());
        return _info;
    }


    @Override
    public String toString() {
        return getName();
    }

    // getter and setter

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


}
