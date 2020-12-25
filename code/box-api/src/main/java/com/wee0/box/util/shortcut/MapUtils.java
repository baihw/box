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
import com.wee0.box.util.IMapUtils;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:09
 * @Description Map处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class MapUtils {

    // 实现类实例
    private static final IMapUtils IMPL = BoxConfig.impl().getInterfaceImpl(IMapUtils.class);

    /**
     * 获取数据源中指定键对应的字符串值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static String getString(final Map map, final Object key) {
        return IMPL.getString(map, key);
    }

    /**
     * 获取数据源中指定键对应的布尔值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static Boolean getBoolean(final Map map, final Object key) {
        return IMPL.getBoolean(map, key);
    }

    /**
     * 获取数据源中指定键对应的数值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static Number getNumber(final Map map, final Object key) {
        return IMPL.getNumber(map, key);
    }

    /**
     * 获取数据源中指定键对应的整型值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static Integer getInteger(final Map map, final Object key) {
        return IMPL.getInteger(map, key);
    }

    /**
     * 获取数据源中指定键对应的长整型值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static Long getLong(final Map map, final Object key) {
        return IMPL.getLong(map, key);
    }

    /**
     * 获取数据源中指定键对应的大数值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static BigDecimal getBigDecimal(final Map map, final Object key) {
        return IMPL.getBigDecimal(map, key);
    }

    /**
     * 获取数据源中指定键对应的日期值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static Date getDate(final Map map, final Object key) {
        return IMPL.getDate(map, key);
    }

    /**
     * 获取数据源中指定键对应的集合值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    public static List getList(final Map map, final Object key) {
        return IMPL.getList(map, key);
    }

    /**
     * 获取包含指定前缀的数据集合，返回的键名中默认去除了前缀部分。
     *
     * @param map    原始数据集合
     * @param prefix 前缀文本
     * @param <T>    值类型
     * @return 仅包含指定前缀的数据集合
     */
    public static <T> Map<String, T> getByPrefix(final Map<String, T> map, String prefix) {
        return IMPL.getByPrefix(map, prefix);
    }

    /**
     * 判断数据源中指定键对应的值是否为空值
     *
     * @param map 数据源
     * @param key 键名
     * @return 没值或者值为空（空字符串，空集合等）时返回false
     */
    public static boolean isContainsValue(final Map map, final Object key) {
        return IMPL.isContainsValue(map, key);
    }

    /**
     * 删除数据源中指定键值
     *
     * @param map  数据源
     * @param keys 键名集合
     */
    public static void remove(final Map map, Object... keys) {
        IMPL.remove(map, keys);
    }

    /**
     * 保持数据源中指定键值，其它删除。
     *
     * @param map  数据源
     * @param keys 要保留的键名集合
     */
    public static void keep(final Map map, Object... keys) {
        IMPL.keep(map, keys);
    }

}
