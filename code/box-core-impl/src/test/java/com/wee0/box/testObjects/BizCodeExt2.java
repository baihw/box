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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:31
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public enum BizCodeExt2 implements IBizCode {
    Ext2Test1("ET2001"),
    Ext2Test2("ET2002"),
    Ext2Test3("ET2003");

    // 实际的业务代码
    private final String CODE;

    BizCodeExt2(String code) {
        this.CODE = code;
    }

    @Override
    public String getCode() {
        return this.CODE;
    }

    @Override
    public String toString() {
        return new StringBuilder(16).append("{name:").append(this.name()).append(",code:").append(this.getCode()).append("}").toString();
    }
}
