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

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 15:25
 * @Description 功能描述
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ISubjectHandler {

    /**
     * 根据参数信息创建一个访问令牌
     *
     * @param params 参数集合
     * @return 访问令牌
     */
    String createToken(Map<String, String> params);

    /**
     * 校验访问令牌是否有效
     *
     * @param token 访问令牌
     * @return 是否有效
     */
    boolean checkToken(String token);

    void getRoles(String token);

}
