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

import com.wee0.box.cache.CacheManager;
import com.wee0.box.sql.ds.DsManagerTest;
import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.SubjectContext;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:29
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
@Ignore("for manual")
public class ShiroSubjectContextTest {

    @BeforeClass
    public static void setup() {
        DsManagerTest.initTestDs();
    }

    @Test
    public void testTokenLogin() {

        // admin: B4A9ABDC32A81CB29EBF28D5610F4FCD0402231AF1527BE61933B5952938F30EE7D719AF3BD154ACC8659BA6F4B70A5D
        // guest: E7C0AEAD81C0D00C504819D41B0FC9100402231AF1527BE61933B5952938F30EE7D719AF3BD154ACCADDCE22F00E6796
        ISubject _subject = SubjectContext.getSubject("B4A9ABDC32A81CB29EBF28D5610F4FCD0402231AF1527BE61933B5952938F30EE7D719AF3BD154ACC8659BA6F4B70A5D");
        printInfo(_subject);
        _subject.logout();
    }

    @Test
    public void testPasswordLogin() {
        ISubject _subject = SubjectContext.getSubject();
        if (!_subject.isLogin()) {
            System.out.println("login admin:admin");
            String _loginId = "admin";
            String _loginPwd = "admin";

            _subject.login(SubjectContext.getTokenFactory().createPasswordToken(_loginId, _loginPwd));
        }
        printInfo(_subject);
        _subject.logout();
    }

    @Test
    public void testMobileLogin() {
//        System.out.println("login guest:123456");
//        String _loginId = "guest";
////            String _loginId = "13112345678";
////            String _loginId = "a@a.com";
//        String _loginPwd = "123456";

        String _mobile = "13112345678";
        String _userId = "0a19ba58e8ab11e9b3700242ac12010a";
        String _code = "1234";
        String _cacheKey = "BoxQueryCode_" + "_" + _mobile + "_" + _code;
        CacheManager.impl().getDefaultCache().put(_cacheKey, _userId, 30);
        ISubject _subject = SubjectContext.getSubject();
        if (!_subject.isLogin()) {
            System.out.println("login " + _mobile + ":1234");
            _subject.login(SubjectContext.getTokenFactory().createPasswordToken(_mobile, _code));
        }
        printInfo(_subject);
        _subject.logout();
    }

    private static void printInfo(ISubject subject) {
        System.out.println("subject : " + subject);
        System.out.println("subject id: " + subject.getId());
//        System.out.println("subject sessionId: " + subject.getSessionId());
        System.out.println("subject isLogin: " + subject.isLogin());
        System.out.println("hasRole[admin]: " + subject.hasRole("admin"));
        System.out.println("hasRole[guest]: " + subject.hasRole("guest"));
        System.out.println("hasPermission[common_add]: " + subject.hasPermission("common_add"));
        System.out.println("hasPermission[common_edit]: " + subject.hasPermission("common_edit"));
        System.out.println("hasPermission[common_delete]: " + subject.hasPermission("common_delete"));
        System.out.println("hasPermission[common_read]: " + subject.hasPermission("common_read"));
    }

}
