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
import com.wee0.box.sql.SqlHelper;
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
public class BoxJdbcRealm extends AuthorizingRealm {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(BoxJdbcRealm.class);

    // 默认名称
    private final String DEF_NAME = "boxJdbcRealm";

    // 用户查询语句，接收2个参数: loginId, loginPwd。
    private String queryUser;
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
        return token instanceof IPasswordToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        IPasswordToken _token = (IPasswordToken) token;
        String _loginId = _token.getUsername();
        String _loginPwd = _token.getPassword();
        Long _result = SqlHelper.impl().queryScalar(this.queryUser, new Object[]{_loginId, _loginPwd});
        if (null == _result || 1 != _result)
            throw new IncorrectCredentialsException("认证失败! result: " + _result);
        AuthenticationInfo _info = new SimpleAuthenticationInfo(_loginId, _loginPwd, getName());
        return _info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        log.debug("queryUser: {}", this.queryUser);
        log.debug("queryRole: {}", this.queryRole);
        log.debug("queryPermission: {}", this.queryPermission);
        log.debug("principals: {}", principals);
        SimpleAuthorizationInfo _info = new SimpleAuthorizationInfo();
        _info.addRole("admin");
        _info.addStringPermission("url:/api/getUserInfo");
        return _info;
    }

    @Override
    public String toString() {
        return getName();
    }

    // getter and setter

    public String getQueryUser() {
        return queryUser;
    }

    public void setQueryUser(String queryUser) {
        this.queryUser = queryUser;
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
