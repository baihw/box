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

package com.wee0.box.impl;

import com.wee0.box.IBoxConfigObject;
import com.wee0.box.code.IBizCode;
import com.wee0.box.code.impl.BizCodeDef;
import com.wee0.box.struct.CmdFactory;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/12/8 7:32
 * @Description 一个简单的可定制组件默认实现
 * <pre>
 * 补充说明
 * </pre>
 **/
final class SimpleBoxConfigObject implements IBoxConfigObject {

    @Override
    public IBizCode getSystemErrorBizCode() {
        return BizCodeDef.S000000;
    }

    @Override
    public IBizCode getSystemErrorInfoBizCode() {
        return BizCodeDef.S000001;
    }

    @Override
    public IBizCode getNeedLoginBizCode() {
        return BizCodeDef.NeedLogin;
    }

    @Override
    public IBizCode getUnauthorizedBizCode() {
        return BizCodeDef.Unauthorized;
    }

}
