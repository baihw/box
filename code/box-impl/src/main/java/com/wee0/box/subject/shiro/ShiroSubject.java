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
import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.IToken;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:06
 * @Description 基于shiro的当前使用者主体对象实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class ShiroSubject implements ISubject {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(ShiroSubject.class);

//    // 主体对象唯一标识关联键名
//    private static final String DEF_KEY_SUBJECT_ID = "_subjectId";

    // shiro subject
    private final Subject subject;

    // 当前主体对象唯一标识
    private String subjectId;
    private String token;

    ShiroSubject(Subject subject) {
        this.subject = subject;
    }

    // 设置当前主体对象唯一标识
    void setId(String id) {
        this.subjectId = id;
        log.trace("login id: {}", id);
    }

    @Override
    public String getId() {
        return this.subjectId;
    }

    // 设置令牌数据
    void setToken(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return this.token;
    }

//    @Override
//    public String getSessionId() {
//        return this.subject.getSession().getId().toString();
//    }
//
//    @Override
//    public ISubject sessionTouch() {
//        try {
//            this.subject.getSession().touch();
//        } catch (InvalidSessionException e) {
//            log.debug("InvalidSessionException:", e);
//        }
//        return this;
//    }

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
        log.debug("login token: {}", this.getToken());
    }

    @Override
    public void login(IToken token, HttpServletRequest request, HttpServletResponse response) {
        if (null == token)
            throw new IllegalArgumentException("token can't be null!");
        if (token instanceof AuthenticationToken) {
            this.subject.login((AuthenticationToken) token);
            final String _LOGIN_TOKEN = this.getToken();
            request.setAttribute(ShiroSubjectContext.KEY_BOX_TOKEN, _LOGIN_TOKEN);
            response.addCookie(ShiroSubjectContext.createIdCookie(_LOGIN_TOKEN, -1));
            log.debug("web login token: {}", _LOGIN_TOKEN);
        } else {
            throw new IllegalStateException("token must be a AuthenticationToken!");
        }
    }

    @Override
    public void logout() {
        try {
            log.debug("logout token: {}", this.getToken());
            this.subject.logout();
        } catch (Exception e) {
            log.error("logout error.", e);
        }
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response) {
        final String _LOGIN_TOKEN = this.getToken();
        try {
            log.debug("web logout token: {}", _LOGIN_TOKEN);
            this.subject.logout();
        } catch (Exception e) {
            log.error("web logout error.", e);
        }
        response.addCookie(ShiroSubjectContext.createIdCookie(_LOGIN_TOKEN, 0));
    }

    @Override
    public boolean hasRole(String role) {
        return this.subject.hasRole(role);
    }

    @Override
    public boolean hasPermission(String permission) {
        return this.subject.isPermitted(permission);
    }

//    @Override
//    public void setAttribute(String key, Object value) {
//        this.subject.getSession().setAttribute(key, value);
//    }
//
//    @Override
//    public Object getAttribute(String key) {
//        return this.subject.getSession().getAttribute(key);
//    }
//
//    @Override
//    public void removeAttribute(String key) {
//        this.subject.getSession().removeAttribute(key);
//    }
//
//    @Override
//    public Set<String> getAttributeKeys() {
//        Collection<Object> _keys = this.subject.getSession().getAttributeKeys();
//        return Collections.unmodifiableSet(_keys.stream().map(String::valueOf).collect(Collectors.toSet()));
//    }

    @Override
    public String toString() {
        StringBuilder _builder = new StringBuilder();
        _builder.append("subject{");
        _builder.append("id:").append(getId());
        _builder.append(",token:").append(getToken());
        _builder.append(",isLogin:").append(isLogin());
        _builder.append(",instance:").append(super.toString());
        _builder.append("}");
        return _builder.toString();
    }
}
