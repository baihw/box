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

package com.wee0.box;

import com.wee0.box.code.IBizCode;
import com.wee0.box.struct.CmdFactory;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/8 7:12
 * @Description 框架中可被开发者定制的组件可通过实现此接口中的对应方法定制。
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IBoxConfigObject {

    /**
     * 默认的业务异常HTTP状态码
     */
    int DEF_BIZ_EXCEPTION_HTTP_STATUS_CODE = 500;

    /**
     * 默认的权限异常HTTP状态码
     */
    int DEF_PERMISSION_EXCEPTION_HTTP_STATUS_CODE = 403;

    /**
     * @return 业务异常HTTP状态码
     */
    default int getBizExceptionHttpStatusCode() {
        return DEF_BIZ_EXCEPTION_HTTP_STATUS_CODE;
    }

    /**
     * @return 权限异常HTTP状态码
     */
    default int getPermissionExceptionHttpStatusCode() {
        return DEF_PERMISSION_EXCEPTION_HTTP_STATUS_CODE;
    }

    /**
     * @return 默认的无参系统异常采用的业务编码
     */
    IBizCode getSystemErrorBizCode();

    /**
     * @return 默认的有参系统异常采用的业务编码
     */
    IBizCode getSystemErrorInfoBizCode();

    /**
     * @return 需要登陆后访问的场景中采用的业务编码
     */
    IBizCode getNeedLoginBizCode();

    /**
     * @return 需要授权后访问的场景中采用的业务编码
     */
    IBizCode getUnauthorizedBizCode();

    /**
     * 获取请求返回值的默认统一包装对象
     *
     * @param returnValue 返回值
     * @return 返回值包装对象
     */
    default Object getWrappedActionReturnValue(Object returnValue) {
        return CmdFactory.create("200", "ok", returnValue);
    }

}
