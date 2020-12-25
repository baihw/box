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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/2/23 7:29
 * @Description Servlet环境请求对象签名校验者
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IServletRequestSignChecker {

    /**
     * 参数关键字：密钥
     */
    String KEY_SECRET_KEY = "__SECRET_KEY__";

    /**
     * 参数关键字：签名
     */
    String KEY_SIGN = "sign";

    /**
     * 设置签名密钥
     *
     * @param secretKey 签名密钥
     */
    void setSecretKey(String secretKey);

    /**
     * 签名校验逻辑
     *
     * @param request 请求对象
     * @return 校验通过返回True
     */
    boolean check(HttpServletRequest request);

}
