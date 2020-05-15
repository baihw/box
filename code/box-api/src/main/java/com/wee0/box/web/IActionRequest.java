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
public interface IActionRequest {

    /**
     * 获取指定名称的请求参数
     *
     * @param name 参数名称
     * @return 参数值
     */
    String getParameter(String name);

    /**
     * 获取指定名称的请求参数
     *
     * @param name         参数名称
     * @param defaultValue 参数不存在或者为空时的默认值
     * @return 参数值
     */
    String getParameter(String name, String defaultValue);

    /**
     * 获取请求参数集合
     *
     * @return 请求参数集合
     */
    Map<String, String[]> getParameterMap();

    /**
     * 获取请求参数集合
     *
     * @return 请求参数集合
     */
    Map<String, String> getParameters();

    /**
     * 设置请求属性
     *
     * @param name  属性名称
     * @param value 属性值
     */
    IActionRequest setAttr(String name, Object value);

    /**
     * 获取请求属性
     *
     * @param name 属性名称
     * @return 属性值
     */
    <T> T getAttr(String name);

    /**
     * 获取请求属性
     *
     * @param name         属性名称
     * @param defaultValue 值不存在时返回的默认值
     * @return 属性值
     */
    <T> T getAttr(String name, T defaultValue);

    /**
     * 获取header参数值
     *
     * @param name 参数名
     * @return 参数值
     */
    String getHeader(String name);

    /**
     * 获取指定名称的Cookie，如果为null,则返回默认值。
     *
     * @param name         名称
     * @param defaultValue 如果为null时的默认值
     * @return 数据 / 默认值
     */
    String getCookie(String name, String defaultValue);

    /**
     * 获取上下文路径
     *
     * @return 上下文路径
     */
    String getContextPath();

    /**
     * 获取资源请求路径。
     *
     * @return 资源请求路径
     */
    String getURI();

    /**
     * 获取完整的资源请求路径，包含get请求中的参数字符串。
     *
     * @return 完整的资源请求路径
     */
    String getFullURI();

    /**
     * 获取请求者IP的方法。考虑了使用反向代理的情况 。
     *
     * @return 请求者IP
     */
    String getRequestIP();

    /**
     * @return 获取原始对象
     */
    Object getRaw();

}
