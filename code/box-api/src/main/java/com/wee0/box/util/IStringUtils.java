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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 7:58
 * @Description 字符串处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IStringUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleStringUtils";

    /**
     * 判断是否为null，或者空字符串。
     *
     * @param str 字符串对象
     * @return true / false
     */
    boolean isEmpty(String str);

    /**
     * 判断字符串是否包含有意义字符（非空白字符）。
     *
     * @param str 待处理的字符串
     * @return true / false
     */
    boolean hasText(String str);

    /**
     * 去除指定字符串前后的空白字符。
     *
     * @param str 待处理的字符串
     * @return 前后去空白字符后的字符串
     * @see java.lang.Character#isWhitespace
     */
    String trim(String str);

    /**
     * 判断前后去空白后是否为空字符串，如果是返回指定的默认值，否则返回前后去空白后的字符串。
     *
     * @param str 待处理的字符串
     * @param def 为空时的默认值
     * @return 处理后的字符串
     */
    String trimEmptyDef(String str, String def);

    /**
     * 将指定对象解析为字符串，默认前后去空白，如果为空返回指定的默认值，否则返回前后去空白后的字符串。
     *
     * @param str 待处理的转字符串对象
     * @param def 为空时的默认值
     * @return 处理后的字符串
     */
    String parseString(Object str, String def);

    /**
     * 将指定字符串解析为布尔值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 布尔值
     */
    boolean parseBoolean(String str, boolean def);

    /**
     * 将指定对象转为字符串后解析为布尔值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 布尔值
     */
    boolean parseBoolean(Object obj, boolean def);

    /**
     * 将指定字符串解析为整数值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 整数值
     */
    int parseInt(String str, int def);

    /**
     * 将指定对象转为字符串后解析为整数值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 整数值
     */
    int parseInt(Object obj, int def);

    /**
     * 将指定字符串解析为长整数值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 整数值
     */
    long parseLong(String str, long def);

    /**
     * 将指定对象转为字符串后解析为长整数值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 整数值
     */
    long parseLong(Object obj, long def);

    /**
     * 将指定字符串解析为单精度浮点值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 单精度浮点值
     */
    float parseFloat(String str, float def);

    /**
     * 将指定对象转为字符串后解析为单精度浮点值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 单精度浮点值
     */
    float parseFloat(Object obj, float def);

    /**
     * 将指定字符串解析为双精度浮点值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 双精度浮点值
     */
    double parseDouble(String str, double def);

    /**
     * 将指定对象转为字符串后解析为双精度浮点值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 双精度浮点值
     */
    double parseDouble(Object obj, double def);

    /**
     * 将指定字符串解析为任意精度大数值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 任意精度大数值
     */
    BigDecimal parseBigDecimal(String str, BigDecimal def);

    /**
     * 判断指定字符串是否以指定前缀开始，忽略大小写。
     *
     * @param str    待处理的字符串
     * @param prefix 前缀字符串
     * @return true / false
     */
    boolean startsWithIgnoreCase(String str, String prefix);

    /**
     * 判断指定字符串是否以指定后缀字符串结尾，忽略大小写。
     *
     * @param str    待处理的字符串
     * @param suffix 后缀字符串
     * @return true / false
     */
    boolean endsWithIgnoreCase(String str, String suffix);

    /**
     * 判断指定字符串是否以指定字符开始，如果不是，则增加指定字符前缀。
     *
     * @param str 待处理的字符串
     * @param c   期望首字符
     * @return 以期望首字符开始的字符串
     */
    String startsWithChar(String str, char c);

    /**
     * 判断指定字符串是否以指定字符结尾，如果不是，则增加指定字符后缀。
     *
     * @param str 待处理的字符串
     * @param c   期望尾字符
     * @return 以期望尾字符开始的字符串
     */
    String endsWithChar(String str, char c);

    /**
     * 判断指定字符串是否以指定字符开始，如果是，则删除指定字符前缀。
     *
     * @param str 待处理的字符串
     * @param c   首字符
     * @return 不以指定首字符开始的字符串
     */
    String startsWithoutChar(String str, char c);

    /**
     * 判断指定字符串是否以指定字符结尾，如果是，则删除指定字符后缀。
     *
     * @param str 待处理的字符串
     * @param c   尾字符
     * @return 不以指定尾字符开始的字符串
     */
    String endsWithoutChar(String str, char c);

    /**
     * 首字母转大写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    String capitalize(String str);

    /**
     * 首字母转小写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    String unCapitalize(String str);

    /**
     * 将字符串中的单词转为下划线分割的全小写字符，通常用于类名称的转换
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    String underscore(String str);

    /**
     * 字符串转大写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    String upperCase(String str);

    /**
     * 字符串转小写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    String lowerCase(String str);

    /**
     * 将指定字符串中的所有值进行匹配替换。
     *
     * @param str  待处理的字符串
     * @param from 要被替换的字符串
     * @param to   替换为的字符串
     * @return 处理后的字符串
     */
    String replace(String str, String from, String to);

    /**
     * 将指定字符串中的所有匹配值删除
     *
     * @param str     待处理的字符串
     * @param pattern 匹配字符串
     * @return 处理后的字符串
     */
    String delete(String str, String pattern);

    /**
     * 将指定字符串中的所有匹配值中包含字符删除
     *
     * @param str   待处理的字符串
     * @param chars 字符集合
     * @return 处理后的字符串
     */
    String deleteAny(String str, String chars);


}
