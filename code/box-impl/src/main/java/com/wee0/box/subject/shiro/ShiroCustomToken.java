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

import com.wee0.box.subject.ICustomToken;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:21
 * @Description 基于shiro的开发人员自定义令牌对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class ShiroCustomToken implements AuthenticationToken, ICustomToken {

    // 用户名
    private String userId;
    // 密码
    private String userCode;

    /**
     * 使用指定的用户名密码创建实例
     *
     * @param userId 用户身份标识
     * @param userCode 密码
     */
    public ShiroCustomToken(final String userId, final String userCode) {
        this.userId = userId;
        this.userCode = userCode;
    }

    public ShiroCustomToken() {
    }

    /**
     * 设置用户身份标识
     *
     * @param userId 用户身份标识
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

    /**
     * @return 用户名
     */
    public String getUserId() {
        return this.userId;
    }

    /**
     * 设置用户身份校验码
     *
     * @param userCode 用户身份校验码
     */
    public void setUserCode(String userCode) {
        this.userCode = userCode;
    }

    /**
     * @return 用户身份校验码
     */
    public String getUserCode() {
        return this.userCode;
    }

    @Override
    public Object getPrincipal() {
        return this.userId;
    }

    @Override
    public Object getCredentials() {
        return this.userCode;
    }

    @Override
    public String toString() {
        return userId + ":" + userCode;
    }
}
