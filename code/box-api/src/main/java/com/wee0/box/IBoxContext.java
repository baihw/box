/*
 *
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
 *
 */

package com.wee0.box;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 17:13
 * @Description Box上下文信息管理对象
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IBoxContext {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.spring.boot.SpringBoxContext";

    /**
     * 检查是否包含指定标识的托管对象
     *
     * @param id 标识
     * @return true / false
     */
    boolean hasBean(String id);

    /**
     * 根据类型获取托管对象实例
     *
     * @param type 类型
     * @param <T>  类型
     * @return 实例
     */
    <T> T getBean(Class<T> type);

    /**
     * 根据标识获取托管对象实例
     *
     * @param id 标识
     * @return 实例
     */
    Object getBean(String id);

    /**
     * 根据标识获取指定类型托管对象实例
     *
     * @param id   标识
     * @param type 类型
     * @param <T>  类型
     * @return 实例
     */
    <T> T getBean(String id, Class<T> type);

}
