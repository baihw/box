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
 * @CreateDate 2019/12/22 15:03
 * @Description 请求对象
 * <pre>
 * 一个通用的基础响应对象，不限于servlet等具体环境。
 * </pre>
 **/
public interface IActionResponse {

    /**
     * 设置响应状态
     *
     * @param status 响应状态
     * @return 链式调用对象
     */
    IActionResponse setStatus(int status);

    /**
     * @return 响应状态
     */
    int getStatus();

    /**
     * 设置响应数据
     *
     * @param data 响应数据
     * @return 链式调用对象
     */
    IActionResponse setData(Object data);

    /**
     * @return 响应数据
     */
    Object getData();

    /**
     * @return 获取原始对象
     */
    Object getRaw();

}
