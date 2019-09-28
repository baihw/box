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

import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:00
 * @Description Json处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IJsonUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.JacksonJsonUtils";

    /**
     * 对象转换为Json字符串
     *
     * @param obj 待转换对象
     * @return Json字符串
     */
    String writeToString(Object obj);

    /**
     * Json字符串转换为指定对象
     *
     * @param jsonString json字符串
     * @param type       转换类
     * @param <T>        转换类型
     * @return 类对象实例
     */
    <T> T readToObject(String jsonString, Class<T> type);

    /**
     * 读取json文本到Map对象
     *
     * @param jsonString json文本
     * @return Map对象
     */
    Map<String, Object> readToMap(String jsonString);

    /**
     * 读取json文本到Map对象集合
     *
     * @param jsonString json文本
     * @return Map对象集合
     */
    List<Map<String, Object>> readToMapList(String jsonString);

}
