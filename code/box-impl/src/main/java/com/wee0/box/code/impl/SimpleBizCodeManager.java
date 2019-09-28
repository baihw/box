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

package com.wee0.box.code.impl;

import com.wee0.box.BoxConfig;
import com.wee0.box.beans.IDestroyable;
import com.wee0.box.code.*;
import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.impl.BoxConfigKeys;
import com.wee0.box.util.shortcut.ClassUtils;

import java.io.ObjectStreamException;
import java.util.HashSet;
import java.util.Set;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/1 7:20
 * @Description 一个简单的业务编码管理器实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleBizCodeManager implements IBizCodeManager, IDestroyable {

    // 是否已经初始化
    private transient boolean isInitialized = false;

    // 初始化数据设置对象集合
    private final Set<IBizCodeInitializer> INITIALIZERS = new HashSet<>(16);

    // 业务编码枚举类
    private final Set<Class<? extends Enum<? extends IBizCode>>> BIZ_CODE_ENUMS = new HashSet<>(16);

    // 数据存储对象
    private final IBizCodeStore STORE;

    @Override
    public synchronized void init() {
        if (isInitialized)
            return;
        // 加载配置信息
        loadConfig();

        // 初始化默认值
        for (IBizCodeInitializer initializer : INITIALIZERS) {
            initializer.initialize(STORE);
        }

        // 加载外部数据
        STORE.loadData();

        // 校验业务编码是否都有对应的文本值。
        for (Class<? extends Enum<? extends IBizCode>> bizCodeEnum : BIZ_CODE_ENUMS) {
            for (IBizCode bizCode : (IBizCode[]) bizCodeEnum.getEnumConstants()) {
                if (!STORE.has(bizCode))
                    throw new BoxRuntimeException("not configured bizCode:" + bizCode);
            }
        }
        this.isInitialized = true;
    }

    @Override
    public void destroy() {
    }


    @Override
    public <T extends Enum<T> & IBizCode> void addBizCodeEnum(Class<T> bizCodeEnum) {
        if (null == bizCodeEnum || BIZ_CODE_ENUMS.contains(bizCodeEnum))
            return;
        BIZ_CODE_ENUMS.add(bizCodeEnum);

        if (IBizCodeInitializer.class.isAssignableFrom(bizCodeEnum)) {
            // 如果业务编码枚举类同时也是初始化器，则不用重复再注册初始化器。
            IBizCodeInitializer _initializer = (IBizCodeInitializer) bizCodeEnum.getEnumConstants()[0];
            this.addBizCodeInitializer(_initializer);
        }
    }

    @Override
    public void addBizCodeInitializer(IBizCodeInitializer initializer) {
        if (null == initializer || INITIALIZERS.contains(initializer))
            return;
        INITIALIZERS.add(initializer);
    }

    @Override
    public String getText(IBizCode code) {
        return STORE.get(code);
    }

    @Override
    public IBizCodeInfo getCodeInfo(IBizCode bizCode, String... args) {
        return STORE.getCodeInfo(bizCode, args);
    }

    /**
     * 加载枚举类配置信息
     */
    protected void loadEnumConfig() {
        String _enumNames = BoxConfig.impl().get(BoxConfigKeys.bizCodeEnums);
        if (null == _enumNames)
            return;

        String[] _enumClassNames = _enumNames.split(",");
        if (null == _enumClassNames || 0 == _enumClassNames.length)
            return;

        for (String _enumClassName : _enumClassNames) {
            if (null == _enumClassName || 0 == (_enumClassName = _enumClassName.trim()).length())
                continue;
            Class _enumClass = ClassUtils.resolveClassName(_enumClassName);
            if (!IBizCode.class.isAssignableFrom(_enumClass))
                throw new IllegalStateException(_enumClassName + " not implement the interface IBizCode.");
            if (!_enumClass.isEnum())
                throw new IllegalStateException(_enumClassName + " must be an enum.");
            this.addBizCodeEnum(_enumClass);
        }
    }

    /**
     * 加载初始化器配置信息
     */
    protected void loadInitializerConfig() {
        String _initializerNames = BoxConfig.impl().get(BoxConfigKeys.bizCodeInitializers);
        if (null == _initializerNames)
            return;

        String[] _initializerClassNames = _initializerNames.split(",");
        if (null == _initializerClassNames || 0 == _initializerClassNames.length)
            return;

        for (String _initializerClassName : _initializerClassNames) {
            if (null == _initializerClassName || 0 == (_initializerClassName = _initializerClassName.trim()).length())
                continue;
            Class<?> _initializerClass = ClassUtils.resolveClassName(_initializerClassName);
            if (!IBizCodeInitializer.class.isAssignableFrom(_initializerClass))
                throw new IllegalStateException(_initializerClassName + " not implement the interface IBizCodeInitializer.");
            IBizCodeInitializer _initializer = null;
            if (_initializerClass.isEnum()) {
                _initializer = (IBizCodeInitializer) _initializerClass.getEnumConstants()[0];
            } else {
                try {
                    _initializer = (IBizCodeInitializer) _initializerClass.newInstance();
                } catch (InstantiationException | IllegalAccessException e) {
                    throw new BoxRuntimeException(e);
                }
            }
            if (!this.INITIALIZERS.contains(_initializer))
                this.INITIALIZERS.add(_initializer);
        }
    }

    /**
     * 加载配置信息
     */
    protected void loadConfig() {
        // 加载初始化器配置信息
        loadInitializerConfig();

        // 加载枚举类配置信息
        loadEnumConfig();
    }

    /**
     * 校验指定的业务编码枚举对象是否都能找到对应的文本配置信息，
     * 如果找不到文本配置信息则报业务编码未定义异常信息。
     *
     * @param bizCodeEnum 业务编码枚举类
     * @param <T>         业务编码枚举接口
     */
    protected <T extends Enum<T> & IBizCode> void validate(Class<T> bizCodeEnum) {
        if (null == bizCodeEnum)
            return;
        String _bizCode = null;
        for (IBizCode bizCode : bizCodeEnum.getEnumConstants()) {
            if (!STORE.has(bizCode))
                throw new BoxRuntimeException("undefined bizCode:" + _bizCode);
        }
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleBizCodeManager() {
        if (null != SimpleBizCodeManagerHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
        // 初始化数据容器 simpleStore, i18nStore
        String _storeName = BoxConfig.impl().get(BoxConfigKeys.bizCodeStore);
        if (null != _storeName && I18nBizCodeStore.NAME.equals(_storeName.trim())) {
            this.STORE = new I18nBizCodeStore();
        } else {
            this.STORE = new SimpleBizCodeStore();
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleBizCodeManagerHolder {
        private static final SimpleBizCodeManager _INSTANCE = new SimpleBizCodeManager();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleBizCodeManagerHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleBizCodeManager me() {
        return SimpleBizCodeManagerHolder._INSTANCE;
    }

}
