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
import com.wee0.box.sql.ds.DsManagerTest;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.config.Ini;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.session.Session;
import org.apache.shiro.session.mgt.DefaultSessionManager;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.SessionDAO;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.ThreadContext;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:13
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for manual")
public class ShiroSecurityManagerTest {

    // 日志对象
    private static ILogger log = LoggerFactory.getLogger(ShiroSecurityManagerTest.class);

    @BeforeClass
    public static void setup() {
        DsManagerTest.initTestDs();
    }

    @Test
    public void test01() {
        SecurityManager _securityManager;
        _securityManager = createIniSecurityManager();
//        _securityManager = createDefaultSecurityManager();
        log.debug("securityManager: {}", _securityManager);
        SecurityUtils.setSecurityManager(_securityManager);

        Subject _keepSubject;
//        _keepSubject = new Subject.Builder().sessionId("a910d13577c94cb586c371119895b946").buildSubject();
        _keepSubject = new Subject.Builder().sessionId("946047175826490e9ddf9a190a73de3c").buildSubject();
        ThreadContext.bind(_keepSubject);

        Subject _subject = SecurityUtils.getSubject();
        if (!_subject.isAuthenticated()) {
            log.debug("please login...");
//            ShiroPasswordToken _token = new ShiroPasswordToken("admin", "123");
            ShiroPasswordToken _token = new ShiroPasswordToken("admin", "admin");
            _subject.login(_token);
            log.debug("login success!");
        }
        log.debug("authenticated: {}", _subject.isAuthenticated());
        Session _session = _subject.getSession();
//        _session.setTimeout(120000);
////        _session.setAttribute("a", "a1");
////        _session.touch();
        printSession(_session);
    }

    private void printSession(Session session) {
        log.debug("******************** session begin ********************");
        log.debug("id: {}", session.getId());
        log.debug("host: {}", session.getHost());
        log.debug("timeout: {}", session.getTimeout());
        log.debug("startTimestamp: {}", session.getStartTimestamp());
        log.debug("lastAccessTime: {}", session.getLastAccessTime());
        for (Object _key : session.getAttributeKeys()) {
            log.debug("key: {}, val: {}", _key, session.getAttribute(_key));
        }
        log.debug("******************** session   end ********************");
    }

    SecurityManager createDefaultSecurityManager() {
        Realm _realm = new BoxPasswordRealm();
        DefaultSecurityManager _securityManager = new DefaultSecurityManager(_realm);
//        ModularRealmAuthenticator _realmAuthenticator = (ModularRealmAuthenticator) _securityManager.getAuthenticator();
//        _realmAuthenticator.setAuthenticationStrategy(new AtLeastOneSuccessfulStrategy());
        DefaultSessionManager _sessionManager = new DefaultSessionManager();
        SessionDAO _sessionDAO = new EnterpriseCacheSessionDAO();
        _sessionManager.setSessionDAO(_sessionDAO);
        _securityManager.setSessionManager(_sessionManager);
        return _securityManager;
    }

    SecurityManager createIniSecurityManager() {
        IniSecurityManagerFactory _factory = new IniSecurityManagerFactory();
        Ini _ini = Ini.fromResourcePath("classpath:config/shiro.ini");
        _factory.setIni(_ini);
        _factory.setSingleton(true);

        SecurityManager _securityManager = _factory.getInstance();
        return _securityManager;
    }

}
