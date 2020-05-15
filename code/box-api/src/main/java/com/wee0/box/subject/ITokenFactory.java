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

package com.wee0.box.subject;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/13 22:08
 * @Description 令牌工厂
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ITokenFactory {

    /**
     * 创建登陆密码令牌对象
     *
     * @param loginId  登陆标识
     * @param password 登陆密码
     * @return 密码令牌对象
     */
    IPasswordToken createPasswordToken(String loginId, String password);

    /**
     * 创建框架令牌对象
     *
     * @param token 令牌数据
     * @return 令牌对象
     */
    IBoxToken createBoxToken(String token);

}
