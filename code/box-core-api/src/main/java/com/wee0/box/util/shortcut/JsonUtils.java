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

package com.wee0.box.util.shortcut;

import com.wee0.box.BoxConfig;
import com.wee0.box.util.IJsonUtils;

import java.io.InputStream;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:10
 * @Description Json处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class JsonUtils {

    // 实现类实例
    private static final IJsonUtils IMPL = BoxConfig.impl().getInterfaceImpl(IJsonUtils.class);

    /**
     * 获取实现类实例
     *
     * @return 实现类实例
     */
    public static IJsonUtils impl() {
        return IMPL;
    }

    /**
     * 对象转换为Json字符串
     *
     * @param obj 待转换对象
     * @return Json字符串
     */
    public static String writeToString(Object obj) {
        return IMPL.writeToString(obj);
    }

    /**
     * Json字符串转换为指定对象
     *
     * @param jsonString json字符串
     * @param type       转换类
     * @param <T>        转换类型
     * @return 类对象实例
     */
    public static <T> T readToObject(String jsonString, Class<T> type) {
        return IMPL.readToObject(jsonString, type);
    }

    /**
     * Json流转换为指定对象
     *
     * @param jsonStream json流
     * @param type       转换类
     * @param <T>        转换类型
     * @return 类对象实例
     */
    public static <T> T readToObject(InputStream jsonStream, Class<T> type) {
        return IMPL.readToObject(jsonStream, type);
    }

    /**
     * json地址数据转换为指定对象
     *
     * @param jsonUrl json地址
     * @param type    转换类
     * @param <T>     转换类型
     * @return 类对象实例
     */
    public static <T> T readToObject(URL jsonUrl, Class<T> type) {
        return IMPL.readToObject(jsonUrl, type);
    }

    /**
     * 读取json文本到Map对象
     *
     * @param jsonString json文本
     * @return Map对象
     */
    public static Map<String, Object> readToMap(String jsonString) {
        return IMPL.readToMap(jsonString);
    }

    /**
     * 读取Json流到Map对象
     *
     * @param jsonStream Json流
     * @return Map对象
     */
    public static Map<String, Object> readToMap(InputStream jsonStream) {
        return IMPL.readToMap(jsonStream);
    }

    /**
     * 读取json地址数据到Map对象
     *
     * @param jsonUrl json地址
     * @return Map对象
     */
    public static Map<String, Object> readToMap(URL jsonUrl) {
        return IMPL.readToMap(jsonUrl);
    }

    /**
     * 读取json文本到Map对象集合
     *
     * @param jsonString json文本
     * @return Map对象集合
     */
    public static List<Map<String, Object>> readToMapList(String jsonString) {
        return IMPL.readToMapList(jsonString);
    }

    /**
     * 读取Json流到Map对象集合
     *
     * @param jsonStream Json流
     * @return Map对象集合
     */
    public static List<Map<String, Object>> readToMapList(InputStream jsonStream) {
        return IMPL.readToMapList(jsonStream);
    }

    /**
     * 读取json地址数据到Map对象集合
     *
     * @param jsonUrl json地址
     * @return Map对象集合
     */
    public static List<Map<String, Object>> readToMapList(URL jsonUrl) {
        return IMPL.readToMapList(jsonUrl);
    }


}
