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

package com.wee0.box.web;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 8:32
 * @Description 请求对象
 * <pre>
 * 一个通用的基础请求对象，不限于servlet等具体环境。
 * </pre>
 **/
public interface IRequest {

    /**
     * 获取指定名称的请求参数
     *
     * @param name 参数名称
     * @return 参数值
     */
    String getParameter(String name);

    /**
     * 获取请求参数集合
     *
     * @return 请求参数集合
     */
    Map<String, String> getParameters();

}
