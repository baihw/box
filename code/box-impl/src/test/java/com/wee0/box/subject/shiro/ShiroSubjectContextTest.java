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
        ISubject _subject = SubjectContext.getSubject("91a51bd0e1324880ad1c6473e510303f");
//        ISubject _subject = SubjectContext.getSubject();
        if (_subject.isLogin()) {
            System.out.println("already login.");
        } else {
            String _loginId = "superadmin";
            String _loginPwd = "b401f11862ae17d6397a7202cf985dc3";
            _subject.login(SubjectContext.getTokenFactory().createPasswordToken(_loginId, _loginPwd));
        }
        System.out.println("login ok.");
        System.out.println("hasRole: " + _subject.hasRole("role1"));
    }

}
