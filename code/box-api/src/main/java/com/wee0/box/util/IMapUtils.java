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

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:01
 * @Description Map处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IMapUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleMapUtils";

    /**
     * 获取数据源中指定键对应的字符串值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    String getString(final Map map, final Object key);

    /**
     * 获取数据源中指定键对应的布尔值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    Boolean getBoolean(final Map map, final Object key);

    /**
     * 获取数据源中指定键对应的数值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    Number getNumber(final Map map, final Object key);

    /**
     * 获取数据源中指定键对应的整型值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    Integer getInteger(final Map map, final Object key);

    /**
     * 获取数据源中指定键对应的长整型值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    Long getLong(final Map map, final Object key);

    /**
     * 获取数据源中指定键对应的大数值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    BigDecimal getBigDecimal(final Map map, final Object key);

    /**
     * 获取数据源中指定键对应的日期值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    Date getDate(final Map map, final Object key);

    /**
     * 获取数据源中指定键对应的集合值
     *
     * @param map 数据源
     * @param key 键名
     * @return 键值
     */
    List getList(final Map map, final Object key);

    /**
     * 获取包含指定前缀的数据集合，返回的键名中默认去除了前缀部分。
     *
     * @param map    原始数据集合
     * @param prefix 前缀文本
     * @param <T>    值类型
     * @return 仅包含指定前缀的数据集合
     */
    <T> Map<String, T> getByPrefix(final Map<String, T> map, String prefix);

    /**
     * 判断数据源中指定键对应的值是否为空值
     *
     * @param map 数据源
     * @param key 键名
     * @return 没值或者值为空（空字符串，空集合等）时返回false
     */
    boolean isContainsValue(final Map map, final Object key);

    /**
     * 删除数据源中指定键值
     *
     * @param map  数据源
     * @param keys 键名集合
     */
    void remove(final Map map, Object... keys);

    /**
     * 保持数据源中指定键值，其它删除。
     *
     * @param map  数据源
     * @param keys 要保留的键名集合
     */
    void keep(final Map map, Object... keys);

}
