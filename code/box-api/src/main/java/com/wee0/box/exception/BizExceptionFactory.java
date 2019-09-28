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

package com.wee0.box.exception;

import com.wee0.box.BoxConfig;
import com.wee0.box.code.IBizCode;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:20
 * @Description 业务异常工厂快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BizExceptionFactory {

    // 实现类实例
    private static final IBizExceptionFactory IMPL = BoxConfig.impl().getInterfaceImpl(IBizExceptionFactory.class);

    /**
     * 创建业务异常
     *
     * @param bizCode 业务编码
     * @param e       异常对象
     * @param args    异常信息文本参数
     * @return 业务异常
     */
    public static BizException create(IBizCode bizCode, Throwable e, String... args) {
        return IMPL.create(bizCode, e, args);
    }

    /**
     * 创建业务异常
     *
     * @param bizCode 业务编码
     * @param e       异常对象
     * @return 业务异常
     */
    public static BizException create(IBizCode bizCode, Throwable e) {
        return IMPL.create(bizCode, e);
    }

    /**
     * 创建业务异常
     *
     * @param bizCode 业务编码
     * @param args    异常信息文本参数
     * @return 业务异常
     */
    public static BizException create(IBizCode bizCode, String... args) {
        return IMPL.create(bizCode, args);
    }

    /**
     * 创建业务异常
     *
     * @param bizCode 业务编码
     * @return 业务异常
     */
    public static BizException create(IBizCode bizCode) {
        return IMPL.create(bizCode);
    }

    /**
     * 创建一个默认业务异常，未提供任何有效的处理信息，使用此方法需自行处理异常信息丢失问题，
     * 至少将真实异常信息记录到日志中。
     *
     * @return 默认的用户友好业务异常
     */
    public static BizException create() {
        return IMPL.create();
    }

}
