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

import com.wee0.box.BoxConfig;
import com.wee0.box.impl.BoxConfigKeys;
import com.wee0.box.log.ILogger;
import com.wee0.box.log.LoggerFactory;
import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.ISubjectContext;
import com.wee0.box.subject.ITokenFactory;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.StringUtils;
import com.wee0.box.util.shortcut.ThreadUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:05
 * @Description 基于shiro的当前使用者主体对象信息管理上下文环境实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class ShiroSubjectContext implements ISubjectContext {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(ShiroSubjectContext.class);

    // 默认的资源配置文件
    private static final String DEF_RESOURCE = "classpath:config/shiro.ini";

    // 对象存储容器
    private static final ThreadLocal<ISubject> SUBJECT_HOLDER = ThreadUtils.createThreadLocal();

    // 令牌工厂对象实例
    private static final ShiroTokenFactory TOKEN_FACTORY = new ShiroTokenFactory();

    // Cookie作用域
    private static final String COOKIE_DOMAIN = BoxConfig.impl().get(BoxConfigKeys.domain, null);

    @Override
    public ISubject getSubject(HttpServletRequest request, HttpServletResponse response) {
        String _boxId = findSessionId(request);
        ISubject _subject = getSubject(_boxId);
        // 判断当前会话标识是否发生改变
        final String _SESSION_ID = _subject.getSessionId();
        if (!_SESSION_ID.equals(_boxId)) {
            request.setAttribute(KEY_BOX_ID, _SESSION_ID);
            response.addCookie(createIdCookie(_boxId, 0));
            response.addCookie(createIdCookie(_SESSION_ID, -1));
            log.trace("sessionId from {} to {}", _boxId, _SESSION_ID);
        }
        return _subject;
    }

    @Override
    public ISubject getSubject(String id) {
        id = CheckUtils.checkTrimEmpty(id, "id can't be empty!");
        SUBJECT_HOLDER.remove();
        ISubject _subject = new ShiroSubject(new Subject.Builder().sessionId(id).buildSubject());
        _subject.sessionTouch();
        SUBJECT_HOLDER.set(_subject);
        return _subject;
    }

    @Override
    public ISubject getSubject() {
        return SUBJECT_HOLDER.get().sessionTouch();
//        ISubject _subject = SUBJECT_HOLDER.get();
//        if (null == _subject) {
//            _subject = new ShiroSubject(SecurityUtils.getSubject());
//            SUBJECT_HOLDER.set(_subject);
//        }
//        return _subject;
    }

    @Override
    public ITokenFactory getTokenFactory() {
        return TOKEN_FACTORY;
    }

    // servlet 环境相关
    // 默认的客户端标识关联键名
    static final String KEY_BOX_ID = "boxId";

    // 获取标识
    private static String findSessionId(HttpServletRequest request) {
        String _result = StringUtils.parseString(request.getAttribute(KEY_BOX_ID), null);
        if (null == _result)
            _result = CheckUtils.checkTrimEmpty(request.getParameter(KEY_BOX_ID), null);
        if (null == _result)
            _result = CheckUtils.checkTrimEmpty(request.getHeader(KEY_BOX_ID), null);
        if (null == _result) {
            Cookie[] _cookies = request.getCookies();
            if (null != _cookies && 0 != _cookies.length) {
                for (Cookie _cookie : _cookies) {
                    if (KEY_BOX_ID.equals(_cookie.getName())) {
                        _result = CheckUtils.checkTrimEmpty(_cookie.getValue(), null);
                    }
                }
            }
        }
        return _result;
    }

    // 创建指定标识Cookie
    static Cookie createIdCookie(String value, int expiry) {
        Cookie _cookie = new Cookie(KEY_BOX_ID, value);
        if (null != COOKIE_DOMAIN) {
            _cookie.setDomain(COOKIE_DOMAIN);
        }
        _cookie.setPath("/");
        _cookie.setMaxAge(expiry);
        _cookie.setHttpOnly(true);
//        _cookie.setSecure(true);
        return _cookie;
    }

    // 初始化逻辑
    private void init() {
        IniSecurityManagerFactory _factory = new IniSecurityManagerFactory();
        Ini _ini = Ini.fromResourcePath(DEF_RESOURCE);
        _factory.setIni(_ini);
        _factory.setSingleton(true);
        SecurityUtils.setSecurityManager(_factory.getInstance());
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private ShiroSubjectContext() {
        if (null != ShiroSubjectContextHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }

        // 初始化
        init();
    }

    // 当前对象唯一实例持有者。
    private static final class ShiroSubjectContextHolder {
        private static final ShiroSubjectContext _INSTANCE = new ShiroSubjectContext();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return ShiroSubjectContextHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static ShiroSubjectContext me() {
        return ShiroSubjectContextHolder._INSTANCE;
    }

}
