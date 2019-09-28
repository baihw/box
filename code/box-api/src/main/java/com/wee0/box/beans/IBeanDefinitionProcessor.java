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

package com.wee0.box.beans;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 23:10
 * @Description 组件自定义处理器
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IBeanDefinitionProcessor {

    /**
     * 获取指定类型的工厂类名称
     *
     * @param clazz 类型
     * @return 工厂类名称
     */
    String getFactoryBeanClassName(Class clazz);

    /**
     * 框架环境中无法提供指定类型的实例时，交由自定义处理器来提供。
     *
     * @param clazz 组件类型
     * @return 组件实例
     */
    Object getInstance(Class clazz);

    /**
     * 扫描过程中找到组件时的回调方法
     *
     * @param clazz 组件对象类
     */
    default void foundBean(Class<?> clazz) {
    }

    /**
     * 组件对象实例初始化之前调用
     *
     * @param clazz 组件对象类
     */
    default void beforeInitialization(Class<?> clazz) {
    }

    /**
     * 组件对象实例初始化完成时调用
     *
     * @param obj 组件对象实例
     * @return 处理后的实例
     */
    default Object afterInitialization(Object obj) {
        return obj;
    }

}
