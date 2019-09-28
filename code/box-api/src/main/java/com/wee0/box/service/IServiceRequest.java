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

package com.wee0.box.service;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:09
 * @Description 服务请求对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IServiceRequest {

    /**
     * 获取指定名称参数的字符串值
     *
     * @param name     参数名称
     * @param defValue 值不存在或为空时返回的默认值
     * @return 字符串值
     */
    String getString(String name, String defValue);

    /**
     * 获取指定名称参数的布尔值
     *
     * @param name     参数名称
     * @param defValue 值不存在或为空时返回的默认值
     * @return 布尔值
     */
    boolean getBoolean(String name, boolean defValue);

    /**
     * 获取指定名称参数的整型值
     *
     * @param name     参数名称
     * @param defValue 值不存在或为空时返回的默认值
     * @return 整型值
     */
    int getInt(String name, int defValue);

    /**
     * 获取指定名称参数的长整型值
     *
     * @param name     参数名称
     * @param defValue 值不存在或为空时返回的默认值
     * @return 长整型值
     */
    long getLong(String name, long defValue);

    /**
     * 存入指定名称的数据
     *
     * @param name  名称
     * @param value 数据
     * @return 链式调用对象
     */
    IServiceRequest put(String name, Object value);

    /**
     * 数据导出为Map类型
     *
     * @return Map类型数据
     */
    Map<String, Object> toMap();

}
