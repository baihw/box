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

import com.wee0.box.code.IBizCode;
import com.wee0.box.code.IBizCodeInitializer;
import com.wee0.box.code.IBizCodeSetter;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:30
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public enum BizCodeExt1 implements IBizCode, IBizCodeInitializer {
    Ext1Test1("ET1001"),
    Ext1Test2("ET1002"),
    Ext1Test3("ET1003"),
    Ext1Test4("ET1004");

    // 实际的业务代码
    private final String CODE;

    BizCodeExt1(String code) {
        this.CODE = code;
    }

    @Override
    public String getCode() {
        return this.CODE;
    }

    @Override
    public String toString() {
        return this.CODE;
    }

    @Override
    public void initialize(IBizCodeSetter setter) {
        setter.set(Ext1Test1, "系统异常，请跟管理员联系");
        setter.set(Ext1Test2, "系统异常，请跟管理员联系。p0:{0}，p1:{1}，p2:{2}。");
        setter.set(Ext1Test3, "系统异常，请跟管理员联系。p2:{2}，p1:{1}，p0:{0}。");
        setter.set(Ext1Test4, "系统异常，请跟管理员联系。p0:{0}，p1:{1}，p0:{0}。");
    }
}
