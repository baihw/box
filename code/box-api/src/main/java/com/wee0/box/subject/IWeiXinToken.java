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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/5/2 7:22
 * @Description 微信登陆令牌对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IWeiXinToken extends IToken {

    /**
     * @return 微信扫描二维码返回的 授权临时票据
     */
    String getCode();

    /**
     * @return 跳转到登陆页面时生成的对应当次登陆的状态码
     */
    String getState();
}
