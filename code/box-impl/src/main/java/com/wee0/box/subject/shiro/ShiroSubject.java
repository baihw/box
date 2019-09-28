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

import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.IToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:06
 * @Description 基于shiro的当前使用者主体对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class ShiroSubject implements ISubject {

    // shiro subject
    private final Subject subject;

    ShiroSubject(Subject subject) {
        this.subject = subject;
    }

    @Override
    public String getId() {
        return this.subject.getSession().getId().toString();
    }

    @Override
    public boolean isLogin() {
        return this.subject.isAuthenticated();
    }

    @Override
    public void login(IToken token) {
        if (null == token)
            throw new IllegalArgumentException("token can't be null!");
        if (token instanceof AuthenticationToken)
            this.subject.login((AuthenticationToken) token);
        else
            throw new IllegalStateException("token must be a AuthenticationToken!");
    }

    @Override
    public boolean hasRole(String role) {
        return this.subject.hasRole(role);
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.subject.isPermitted(permission);
    }

}
