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

package com.wee0.box.notify.sms;

import com.wee0.box.struct.CMD;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2020/3/28 8:01
 * @Description 短信操作助手
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface ISmsHelper {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.notify.sms.dy.DySmsHelper";

    /**
     * 发送短信
     *
     * @param params 请求参数集合，参考官方文档
     * @return 响应结果
     */
    CMD<String> sendSms(Map<String, String> params);

    /**
     * 查询短信发送结果
     *
     * @param params 请求参数集合，参考官方文档
     * @return 响应结果
     */
    CMD querySendSmsDetail(Map<String, String> params);

}
