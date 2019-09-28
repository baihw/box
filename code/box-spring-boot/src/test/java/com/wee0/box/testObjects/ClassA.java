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

package com.wee0.box.testObjects;

import javax.annotation.Resource;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:56
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public class ClassA implements InterfaceA, InterfaceB {

    String string;
    static String staticString;
    static final String staticFinalString = "";

    private String privateString;
    private static String privateStaticString;
    private static final String privateStaticFinalString = "";

    protected String protectedString;
    protected static String protectedStaticString;
    protected static final String protectedStaticFinalString = "";

    public String publicString;
    public static String publicStaticString;
    public static final String publicStaticFinalString = "";

    @Resource
    private InterfaceA interfaceA;

    public void publicMethod() {
    }

    public void publicMethod1(String s1, Integer t1, int t2, Object o1) {
    }

    void method() {
    }

    protected void protectedMethod() {
    }

    private void privateMethod() {
    }

    private void privateMethod1(String s1, Integer t1, int t2, Object o1) {
    }
}
