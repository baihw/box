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

import com.wee0.box.sql.ds.DsManagerTest;
import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.SubjectContext;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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
    public void test01() {
//        ISubject _subject = SubjectContext.getSubject();
        ISubject _subject = SubjectContext.getSubject("b211fab9f83b4763a23621ef1edf3099");
        if (!_subject.isLogin()) {
            System.out.println("login admin:admin");
            String _loginId = "admin";
            String _loginPwd = "admin";
            _subject.login(SubjectContext.getTokenFactory().createPasswordToken(_loginId, _loginPwd));
        }
        printInfo(_subject);
    }

    @Test
    public void test02() {
        ISubject _subject = SubjectContext.getSubject("015b8047b6344e40ab7c6d3b804a2172");
        if (!_subject.isLogin()) {
            System.out.println("login guest:123456");
            String _loginId = "guest";
            String _loginPwd = "123456";
            _subject.login(SubjectContext.getTokenFactory().createPasswordToken(_loginId, _loginPwd));
        }
        printInfo(_subject);
        _subject.logout();
    }

    private static void printInfo(ISubject subject) {
        System.out.println("subject id: " + subject.getId());
        System.out.println("hasRole[admin]: " + subject.hasRole("admin"));
        System.out.println("hasRole[guest]: " + subject.hasRole("guest"));
        System.out.println("hasPermission[common_add]: " + subject.hasPermission("common_add"));
        System.out.println("hasPermission[common_edit]: " + subject.hasPermission("common_edit"));
        System.out.println("hasPermission[common_delete]: " + subject.hasPermission("common_delete"));
        System.out.println("hasPermission[common_read]: " + subject.hasPermission("common_read"));
    }

}
