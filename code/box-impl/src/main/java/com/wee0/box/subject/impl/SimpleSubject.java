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

package com.wee0.box.subject.impl;

import com.wee0.box.subject.ISubject;
import com.wee0.box.subject.IToken;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:56
 * @Description 一个简单的当前使用者主体对象信息管理实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class SimpleSubject implements ISubject {

    @Override
    public String getId() {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public boolean isLogin() {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public void login(IToken token) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public void logout() {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public boolean hasRole(String role) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }

    @Override
    public boolean hasPermission(String permission) {
        throw new UnsupportedOperationException("This method is not yet implemented.");
    }
}
