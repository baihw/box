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
import com.wee0.box.subject.SubjectContext;
import org.apache.shiro.authc.*;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/2 7:19
 * @Description 基于令牌的认证域
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BoxTokenRealm extends BoxCustomRealm {

    // 日志对象
    private static final ILogger _LOG = LoggerFactory.getLogger(BoxTokenRealm.class);

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
        IBoxToken _token = (IBoxToken) token;
        _LOG.trace("boxToken check: {}", token);
        String _credentials = ((IBoxToken) token).getToken();
        try {
            String _userId = this.boxTokenManager.checkTokenUserId(_credentials);
            ShiroSubject _subject = ((ShiroSubject) SubjectContext.getSubject());
            // 保存当前主体对象唯一标识，便于后期开发人员获取。
            _subject.setId(_userId);
            _LOG.trace("boxToken checked: {}", _subject);
            AuthenticationInfo _info = new SimpleAuthenticationInfo(_userId, _credentials, getName());
            return _info;
        } catch (Exception e) {
            throw new IncorrectCredentialsException("认证失败! " + e.getMessage());
        }
    }

    @Override
    public String toString() {
        return getName();
    }
}
