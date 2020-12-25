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

import com.wee0.box.subject.IBoxToken;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/5 9:32
 * @Description 基于shiro的账号密码令牌对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class ShiroBoxToken implements AuthenticationToken, IBoxToken {

    // 令牌数据
    private String token;

    /**
     * 使用指定的令牌数据创建实例
     *
     * @param token 令牌数据
     */
    public ShiroBoxToken(final String token) {
        this.token = token;
    }

    public ShiroBoxToken() {
    }

    /**
     * 设置令牌数据
     *
     * @param token 令牌数据
     */
    public void setToken(String token) {
        this.token = token;
    }

    /**
     * @return 令牌数据
     */
    public String getToken() {
        return this.token;
    }

    @Override
    public Object getPrincipal() {
        return this.token;
    }

    @Override
    public Object getCredentials() {
        return this.token;
    }

    @Override
    public String toString() {
        return "BoxToken:" + this.token;
    }
}
