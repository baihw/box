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

package com.wee0.box.util;

import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/9/8 9:22
 * @Description 对象处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IObjectUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.CBUObjectUtils";

    /**
     * 设置对象属性
     *
     * @param bean  对象
     * @param name  属性名称
     * @param value 属性值
     */
    void setProperty(Object bean, String name, Object value);

    /**
     * 根据集合数据设置对象属性
     *
     * @param bean       对象
     * @param properties 属性集合
     */
    void setProperties(Object bean, Map<String, ?> properties);

    /**
     * 将对象属性信息转换为键值集合对象数据
     *
     * @param bean 对象
     * @return 属性键值集合对象
     */
    Map<String, Object> toMap(Object bean);

    /**
     * 通过反射生成对象的toString文本
     *
     * @param bean 对象
     * @return 字符串文本
     */
    String reflectionToString(Object bean);

}
