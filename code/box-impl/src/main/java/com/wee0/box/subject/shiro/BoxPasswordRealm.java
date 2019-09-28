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

import com.wee0.box.subject.IPasswordToken;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:19
 * @Description 基于密码令牌的认证域
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxPasswordRealm extends AuthorizingRealm {

    // 默认名称
    private final String DEF_NAME = "boxPasswordRealm";

    @Override
    public String getName() {
        return DEF_NAME;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof IPasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        System.out.println("getAuthenticationInfo...");
        IPasswordToken _token = (IPasswordToken) token;
        String _userName = _token.getUsername();
        String _password = _token.getPassword();
        if ("admin".equals(_userName) && "123".equals(_password)) {
            AuthenticationInfo _result = new SimpleAuthenticationInfo(_userName, _password, getName());
            return _result;
        }
        throw new IncorrectCredentialsException("认证失败!");
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo _info = new SimpleAuthorizationInfo();
        _info.addRole("admin");
        _info.addStringPermission("url:/api/getUserInfo");
        return _info;
    }

    @Override
    public String toString() {
        return getName();
    }
}
