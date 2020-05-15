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

import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.subject.IBoxToken;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/2 7:19
 * @Description 基于令牌的认证域
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxTokenRealm extends AuthorizingRealm {

    // 日志对象
    private static final ILogger _LOG = LoggerFactory.getLogger(BoxJdbcRealm.class);

    // 默认名称
    private final String DEF_NAME = "boxTokenRealm";

    @Override
    public String getName() {
        return DEF_NAME;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof IBoxToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("getAuthenticationInfo...");
        IBoxToken _token = (IBoxToken) token;

//        // 获取用户标识
//        ((ShiroSubject) SubjectContext.getSubject()).setId(_userId);
//        AuthenticationInfo _info = new SimpleAuthenticationInfo(_userId, _loginPwd, getName());
//
//        if ("admin".equals(_userName) && "123".equals(_password)) {
//            AuthenticationInfo _result = new SimpleAuthenticationInfo(_userName, _password, getName());
//            return _result;
//        }
//        throw new IncorrectCredentialsException("认证失败!");
        AuthenticationInfo _info = new SimpleAuthenticationInfo("0a19ba58e8ab11e9b3700242ac12010a", _token.getToken(), getName());
        return _info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    @Override
    public String toString() {
        return getName();
    }
}
