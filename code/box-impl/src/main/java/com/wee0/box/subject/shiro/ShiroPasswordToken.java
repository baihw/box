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

import com.wee0.box.subject.IPasswordToken;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 7:21
 * @Description 基于shiro的账号密码令牌对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class ShiroPasswordToken implements AuthenticationToken, IPasswordToken {

    // 用户名
    private String username;
    // 密码
    private String password;

    /**
     * 使用指定的用户名密码创建实例
     *
     * @param username 用户名
     * @param password 密码
     */
    public ShiroPasswordToken(final String username, final String password) {
        this.username = username;
        this.password = password;
    }

    public ShiroPasswordToken() {
    }

    /**
     * 设置用户名
     *
     * @param username 用户名
     */
    public void setUsername(String username) {
        this.username = username;
    }

    /**
     * @return 用户名
     */
    public String getUsername() {
        return username;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password;
    }

    /**
     * @return 密码
     */
    public String getPassword() {
        return password;
    }

    @Override
    public Object getPrincipal() {
        return this.username;
    }

    @Override
    public Object getCredentials() {
        return this.password;
    }

    @Override
    public String toString() {
        return username + ":" + password;
    }
}
