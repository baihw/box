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
 * @CreateDate 2019/12/22 15:05
 * @Description Action请求过滤器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IActionFilter {

    /**
     * 初始化逻辑。
     *
     * @param params 参数集合
     */
    default void init(Map<String, Object> params) {
    }

    /**
     * 请求过滤逻辑
     *
     * @param request  请求对象
     * @param response 响应对象
     * @return 是否结束，如果为true则整个请求流程将结束，不再执行后续动作。
     */
    boolean doFilter(IActionRequest request, IActionResponse response);

    /**
     * 资源销毁逻辑。
     */
    default void destroy() {
    }

}
