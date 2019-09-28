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

import com.wee0.box.code.IBizCodeInfo;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:18
 * @Description 统一的业务异常
 * <pre>
 * 补充说明
 * </pre>
 **/
public class BizException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    // 业务编码信息
    private IBizCodeInfo bizCodeInfo;

    public BizException(IBizCodeInfo bizCodeInfo) {
        super(bizCodeInfo.formatText());
        this.bizCodeInfo = bizCodeInfo;
    }

    public BizException(IBizCodeInfo bizCodeInfo, Throwable e) {
        super(bizCodeInfo.formatText(), e);
        this.bizCodeInfo = bizCodeInfo;
    }

    public IBizCodeInfo getBizCodeInfo() {
        return this.bizCodeInfo;
    }

}
