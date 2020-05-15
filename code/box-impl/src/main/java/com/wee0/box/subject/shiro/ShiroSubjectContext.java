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
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.cache.Cache;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.RealmSecurityManager;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.subject.Subject;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.ObjectStreamException;
import java.util.Iterator;

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
        final String _boxToken = findBoxToken(request);
        if (null == _boxToken)
            return null;
        ShiroSubject _subject = (ShiroSubject) getSubject(_boxToken);
        if (!_subject.isLogin()) {
            log.info("delete invalid token: {}", _boxToken);
            // 移除无效的cookie
            response.addCookie(createIdCookie(_boxToken, 0));
        }

        return _subject;
    }

    @Override
    public ISubject getSubject(String token) {
        token = CheckUtils.checkNotTrimEmpty(token, "token can not be empty!");
        ShiroSubject _subject = (ShiroSubject) SUBJECT_HOLDER.get();
        if (null != _subject && token.equals(_subject.getToken()))
            return _subject;
        SUBJECT_HOLDER.remove();
//        _subject = new ShiroSubject(new Subject.Builder().sessionId(token).buildSubject());
        _subject = new ShiroSubject(new Subject.Builder().sessionCreationEnabled(false).buildSubject());
        SUBJECT_HOLDER.set(_subject);
        _subject.setToken(token);
        ShiroBoxToken _loginToken = new ShiroBoxToken(token);
        try {
            _subject.login(_loginToken);
            log.trace("auto login token: {} ok", token);
        } catch (Exception e) {
            log.info("auto login token: {} error: {}", token, e.getMessage());
        }
        return _subject;
    }

    @Override
    public ISubject getSubject() {
//        return SUBJECT_HOLDER.get();
//        return ((ShiroSubject) SUBJECT_HOLDER.get()).sessionTouch();
        ISubject _subject = SUBJECT_HOLDER.get();
        if (null == _subject) {
            _subject = new ShiroSubject(new Subject.Builder().sessionCreationEnabled(false).buildSubject());
            SUBJECT_HOLDER.set(_subject);
        }
        return _subject;
    }

    @Override
    public void clearAuthorizationCache() {

//        String _userId = "";
//        String _realmName = "boxJdbcRealm";
//        Subject _subject = SecurityUtils.getSubject();
//        SimplePrincipalCollection _principals = new SimplePrincipalCollection(_userId,_realmName);
//        _subject.runAs(_principals);

        RealmSecurityManager _manager = (RealmSecurityManager) SecurityUtils.getSecurityManager();
        Iterator<Realm> _realms = _manager.getRealms().iterator();
        while (_realms.hasNext()) {
            Realm _realm = _realms.next();
            if (_realm instanceof AuthorizingRealm) {
                Cache<Object, AuthorizationInfo> _realmCache = ((AuthorizingRealm) _realm).getAuthorizationCache();
                _realmCache.clear();
//                _realmCache.remove(_subject.getPrincipal());
//                _realmCache.remove(_subject.getPrincipals());
//                _subject.releaseRunAs();
            }
        }
    }

    @Override
    public ITokenFactory getTokenFactory() {
        return TOKEN_FACTORY;
    }

    // 初始化逻辑
    private void init() {
        IniSecurityManagerFactory _factory = new IniSecurityManagerFactory();
        Ini _ini = Ini.fromResourcePath(DEF_RESOURCE);
        _factory.setIni(_ini);
        _factory.setSingleton(true);
        SecurityUtils.setSecurityManager(_factory.getInstance());
    }

    // 默认的客户端令牌关联键名
    static final String KEY_BOX_TOKEN = "boxToken";

    // 获取标识
    private static String findBoxToken(HttpServletRequest request) {
        String _result = StringUtils.parseString(request.getAttribute(KEY_BOX_TOKEN), null);
        if (null == _result)
            _result = CheckUtils.checkTrimEmpty(request.getParameter(KEY_BOX_TOKEN), null);
        if (null == _result)
            _result = CheckUtils.checkTrimEmpty(request.getHeader(KEY_BOX_TOKEN), null);
        if (null == _result) {
            Cookie[] _cookies = request.getCookies();
            if (null != _cookies && 0 != _cookies.length) {
                for (Cookie _cookie : _cookies) {
                    if (KEY_BOX_TOKEN.equals(_cookie.getName())) {
                        _result = CheckUtils.checkTrimEmpty(_cookie.getValue(), null);
                    }
                }
            }
        }
        return _result;
    }

    // 创建指定标识Cookie
    static Cookie createIdCookie(String value, int expiry) {
        Cookie _cookie = new Cookie(KEY_BOX_TOKEN, value);
        if (null != COOKIE_DOMAIN) {
            _cookie.setDomain(COOKIE_DOMAIN);
        }
        _cookie.setPath("/");
        _cookie.setMaxAge(expiry);
        _cookie.setHttpOnly(true);
//        _cookie.setSecure(true);
        return _cookie;
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
