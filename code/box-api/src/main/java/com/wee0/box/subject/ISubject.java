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

import java.io.Serializable;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:52
 * @Description 当前使用者主体对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ISubject {

    /**
     * @return 主体对象唯一标识
     */
    String getId();

    /**
     * @return 会话唯一标识
     */
    String getSessionId();

    /**
     * @return 是否已经登陆
     */
    boolean isLogin();

    /**
     * 执行登陆逻辑
     *
     * @param token 登陆令牌
     */
    void login(IToken token);

    /**
     * 执行登出逻辑
     */
    void logout();

    /**
     * 判断是否具备指定角色
     *
     * @param role 角色标识
     * @return true / false
     */
    boolean hasRole(String role);

    /**
     * 判断是否具备指定权限
     *
     * @param permission 权限标识
     * @return true / false
     */
    boolean hasPermission(String permission);

    /**
     * 设置属性
     *
     * @param key   属性名
     * @param value 属性值
     */
    void setAttribute(String key, Object value);

    /**
     * 获取属性
     *
     * @param key 属性名
     * @return 属性值
     */
    Object getAttribute(String key);

    /**
     * 移除属性
     *
     * @param key 属性名
     */
    void removeAttribute(String key);

    /**
     * 属性名集合
     *
     * @return 属性名集合
     */
    Set<String> getAttributeKeys();

}
