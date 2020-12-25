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

import com.wee0.box.BoxConfig;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/2 8:55
 * @Description 当前使用者主体对象信息管理上下文环境快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SubjectContext {

    // 实现类实例
    private static final ISubjectContext IMPL = BoxConfig.impl().getInterfaceImpl(ISubjectContext.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static ISubjectContext impl() {
        return IMPL;
    }

//    /**
//     * 设置当前使用者主体对象
//     *
//     * @param subject 当前使用者主体对象
//     */
//    public static void setSubject(ISubject subject) {
//        IMPL.setSubject(subject);
//    }

    /**
     * 获取当前请求关联的使用者主体对象
     *
     * @param request  请求对象
     * @param response 响应对象
     * @return 使用者主体对象
     */
    public static ISubject getSubject(HttpServletRequest request, HttpServletResponse response) {
        return IMPL.getSubject(request, response);
    }

    /**
     * 获取指定令牌使用者主体对象
     *
     * @param token 令牌
     * @return 使用者主体对象
     */
    public static ISubject getSubject(String token) {
        return IMPL.getSubject(token);
    }

    /**
     * 获取当前使用者主体对象
     *
     * @return 当前使用者主体对象
     */
    public static ISubject getSubject() {
        return IMPL.getSubject();
    }

    /**
     * 获取令牌操作助手实例对象
     *
     * @return 令牌操作助手实例对象
     */
    public static ITokenHelper getTokenHelper() {
        return IMPL.getTokenHelper();
    }

    /**
     * 获取令牌工厂实例对象
     *
     * @return 令牌工厂实例对象
     */
    public static ITokenFactory getTokenFactory() {
        return IMPL.getTokenFactory();
    }

}
