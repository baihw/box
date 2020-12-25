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

import com.wee0.box.subject.IWeiXinToken;
import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/23 7:16
 * @Description 基于微信访问码的令牌对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public final class ShiroWeiXinToken implements AuthenticationToken, IWeiXinToken {

    // 微信访问码
    private String code;
    // 自定义状态码
    private String state;

    /**
     * 使用指定的微信访问码数据创建实例
     *
     * @param code  微信访问码
     * @param state 自定义状态码
     */
    public ShiroWeiXinToken(final String code, final String state) {
        this.code = code;
        this.state = state;
    }

    public ShiroWeiXinToken() {
    }

    @Override
    public Object getPrincipal() {
        return this.code;
    }

    @Override
    public Object getCredentials() {
        return this.code;
    }

    @Override
    public String toString() {
        return "WeiXinToken code:" + this.code + ", state:" + this.state;
    }

    /**
     * getter and setter
     */
    public void setCode(String code) {
        this.code = code;
    }

    @Override
    public String getCode() {
        return this.code;
    }


    public void setState(String state) {
        this.state = state;
    }

    @Override
    public String getState() {
        return this.state;
    }
}
