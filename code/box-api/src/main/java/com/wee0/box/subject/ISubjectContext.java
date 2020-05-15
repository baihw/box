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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:53
 * @Description 当前使用者主体对象信息管理上下文环境
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ISubjectContext {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.subject.shiro.ShiroSubjectContext";

    /**
     * 获取当前请求关联的使用者主体对象
     *
     * @param request  请求对象
     * @param response 响应对象
     * @return 使用者主体对象
     */
    ISubject getSubject(HttpServletRequest request, HttpServletResponse response);

    /**
     * 获取指定令牌使用者主体对象
     *
     * @param token 令牌对象
     * @return 使用者主体对象
     */
    ISubject getSubject(String token);

    /**
     * 获取当前使用者主体对象
     *
     * @return 当前使用者主体对象
     */
    ISubject getSubject();

    /**
     * 清除所有缓存的授权数据，不清除登陆状态。
     */
    void clearAuthorizationCache();

    /**
     * 获取令牌工厂实例对象
     *
     * @return 令牌工厂实例对象
     */
    ITokenFactory getTokenFactory();

}
