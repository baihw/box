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

package com.wee0.box.struct.impl;

import com.wee0.box.code.BizCodeManager;
import com.wee0.box.code.IBizCode;
import com.wee0.box.struct.CMD;
import com.wee0.box.struct.ICmdFactory;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 8:06
 * @Description 一个简单的CMD类型工厂实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleCmdFactory implements ICmdFactory {
    @Override
    public CMD<String> create(String code, String message, Object data) {
        return new SimpleCMD(code, message, data);
    }

    @Override
    public CMD<String> create(Object data) {
        return new SimpleCMD(data);
    }

    @Override
    public CMD<String> create(String code, String message) {
        return new SimpleCMD(code, message);
    }

    @Override
    public CMD<String> create(String message) {
        return new SimpleCMD(message);
    }

    @Override
    public CMD<String> create(IBizCode bizCode) {
        if (null == bizCode)
            throw new IllegalArgumentException("bizCode can't be null!");
        String _bizText = BizCodeManager.impl().getText(bizCode);
        if (null == _bizText)
            throw new IllegalStateException("invalid bizCode: " + bizCode.getCode());
        return new SimpleCMD(bizCode.getCode(), _bizText);
    }

    @Override
    public CMD<String> create(IBizCode bizCode, String... args) {
        if (null == bizCode)
            throw new IllegalArgumentException("bizCode can't be null!");
        String _msg = BizCodeManager.impl().getCodeInfo(bizCode, args).formatText();
        return new SimpleCMD(bizCode.getCode(), _msg);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleCmdFactory() {
        if (null != SimpleCmdFactoryHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleCmdFactoryHolder {
        private static final SimpleCmdFactory _INSTANCE = new SimpleCmdFactory();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleCmdFactoryHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleCmdFactory me() {
        return SimpleCmdFactoryHolder._INSTANCE;
    }

}
