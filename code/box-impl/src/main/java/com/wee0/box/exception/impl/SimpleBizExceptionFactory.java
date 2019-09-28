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

package com.wee0.box.exception.impl;

import com.wee0.box.code.BizCodeDef;
import com.wee0.box.code.BizCodeManager;
import com.wee0.box.code.IBizCode;
import com.wee0.box.code.IBizCodeInfo;
import com.wee0.box.exception.BizException;
import com.wee0.box.exception.IBizExceptionFactory;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:15
 * @Description 一个简单的业务异常工厂实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleBizExceptionFactory implements IBizExceptionFactory {

    // 默认编码信息
    private static final IBizCodeInfo DEF_CODE_INFO = BizCodeManager.impl().getCodeInfo(BizCodeDef.S000000);

    @Override
    public BizException create(IBizCode bizCode, Throwable e, String... args) {
        IBizCodeInfo _codeInfo = BizCodeManager.impl().getCodeInfo(bizCode, args);
        return new BizException(_codeInfo, e);
    }

    @Override
    public BizException create(IBizCode bizCode, Throwable e) {
        IBizCodeInfo _codeInfo = BizCodeManager.impl().getCodeInfo(bizCode);
        return new BizException(_codeInfo, e);
    }

    @Override
    public BizException create(IBizCode bizCode, String... args) {
        IBizCodeInfo _codeInfo = BizCodeManager.impl().getCodeInfo(bizCode, args);
        return new BizException(_codeInfo);
    }

    @Override
    public BizException create(IBizCode bizCode) {
        IBizCodeInfo _codeInfo = BizCodeManager.impl().getCodeInfo(bizCode);
        return new BizException(_codeInfo);
    }

    @Override
    public BizException create() {
        return new BizException(DEF_CODE_INFO);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleBizExceptionFactory() {
        if (null != SimpleBizExceptionFactoryHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleBizExceptionFactoryHolder {
        private static final SimpleBizExceptionFactory _INSTANCE = new SimpleBizExceptionFactory();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleBizExceptionFactoryHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleBizExceptionFactory me() {
        return SimpleBizExceptionFactoryHolder._INSTANCE;
    }

}
