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
import com.wee0.box.subject.IAuthorizationInfoProvider;
import com.wee0.box.subject.ICustomToken;
import com.wee0.box.subject.SubjectContext;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/21 9:19
 * @Description 开发人员自定义认证域
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxCustomRealm extends AuthorizingRealm {

    // 日志对象
    private static ILogger _LOG = LoggerFactory.getLogger(BoxCustomRealm.class);

    // 默认名称
    private final String DEF_NAME = "boxCustomRealm";

    // boxToken管理对象
    protected BoxTokenManager boxTokenManager;

    // 授权信息数据提供者实例
    protected IAuthorizationInfoProvider authorizationInfoProvider;

    @Override
    public String getName() {
        return DEF_NAME;
    }

    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof ICustomToken;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        _LOG.trace("doGetAuthenticationInfo...");
        ICustomToken _token = (ICustomToken) token;
        String _userId = _token.getUserId();
        String _userCode = _token.getUserCode();
        if (null == _userId || 0 == (_userId = _userId.trim()).length())
            throw new IncorrectCredentialsException("认证失败! userId不能为空！");
        if (null == _userCode || 0 == (_userCode = _userCode.trim()).length())
            throw new IncorrectCredentialsException("认证失败! userCode不能为空！");

        String _subjectToken = this.boxTokenManager.createToken(_userId);
        ShiroSubject _subject = ((ShiroSubject) SubjectContext.getSubject());
        // 保存当前主体对象唯一标识，便于后期开发人员获取。
        _subject.setId(_userId);
        if (null != _subjectToken) {
            // 如果有新生成的令牌，设置到主体对象
            _subject.setToken(_subjectToken);
        }
        _LOG.trace("custom realm checked: {}", _subject);
        AuthenticationInfo _info = new SimpleAuthenticationInfo(_userId, _userCode, getName());
        return _info;
    }

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
//        if (null == this._authorizationInfoProvider) {
//            this._authorizationInfoProvider = BoxContext.impl().getBean(this.dataProviderName, IAuthorizationInfoProvider.class);
//            if (null == this._authorizationInfoProvider) {
//                String _errorMsg = "not found data provider by: " + this.dataProviderName;
//                _LOG.error(_errorMsg);
//                throw new IllegalStateException(_errorMsg);
//            }
//        }

        String _userId = String.valueOf(principals.getPrimaryPrincipal());
        _LOG.debug("load authorizationInfo for user: {}", _userId);

        Set<String> _roles = this.authorizationInfoProvider.getRoles(_userId);
        Set<String> _permissions = this.authorizationInfoProvider.getPermissions(_userId);

        SimpleAuthorizationInfo _info = new SimpleAuthorizationInfo();
//        _info.addRole("admin");
//        _info.addStringPermission("url:/api/getUserInfo");
        if (null != _roles && !_roles.isEmpty()) {
            _info.addRoles(_roles);
            _LOG.debug("user: {} add Roles: {}", _userId, _roles);
        }
        if (null != _roles && !_roles.isEmpty()) {
            _info.addStringPermissions(_permissions);
            _LOG.debug("user: {} add Permissions: {}", _userId, _permissions);
        }
        return _info;
    }


    // getter and setter

    public BoxTokenManager getBoxTokenManager() {
        return this.boxTokenManager;
    }

    public void setBoxTokenManager(BoxTokenManager boxTokenManager) {
        this.boxTokenManager = boxTokenManager;
    }

    public IAuthorizationInfoProvider getAuthorizationInfoProvider() {
        return this.authorizationInfoProvider;
    }

    public void setAuthorizationInfoProvider(IAuthorizationInfoProvider authorizationInfoProvider) {
        this.authorizationInfoProvider = authorizationInfoProvider;
    }

    @Override
    public String toString() {
        return getName();
    }
}
