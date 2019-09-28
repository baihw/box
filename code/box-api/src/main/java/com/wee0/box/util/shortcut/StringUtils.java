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
import com.wee0.box.util.IStringUtils;

import java.math.BigDecimal;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:07
 * @Description 字符串处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class StringUtils {

    // 实现类实例
    private static final IStringUtils IMPL = BoxConfig.impl().getInterfaceImpl(IStringUtils.class);

    /**
     * 判断是否为null，或者空字符串。
     *
     * @param str 字符串对象
     * @return true / false
     */
    public static boolean isEmpty(String str) {
        return IMPL.isEmpty(str);
    }

    /**
     * 判断字符串是否包含有意义字符（非空白字符）。
     *
     * @param str 待处理的字符串
     * @return true / false
     */
    public static boolean hasText(String str) {
        return IMPL.hasText(str);
    }

    /**
     * 去除指定字符串前后的空白字符。
     *
     * @param str 待处理的字符串
     * @return 前后去空白字符后的字符串
     * @see java.lang.Character#isWhitespace
     */
    public static String trim(String str) {
        return IMPL.trim(str);
    }

    /**
     * 判断前后去空白后是否为空字符串，如果是返回指定的默认值，否则返回前后去空白后的字符串。
     *
     * @param str 待处理的字符串
     * @param def 为空时的默认值
     * @return 处理后的字符串
     */
    public static String trimEmptyDef(String str, String def) {
        return IMPL.trimEmptyDef(str, def);
    }

    /**
     * 将指定对象解析为字符串，默认前后去空白，如果为空返回指定的默认值，否则返回前后去空白后的字符串。
     *
     * @param str 待处理的转字符串对象
     * @param def 为空时的默认值
     * @return 处理后的字符串
     */
    public static String parseString(Object str, String def) {
        return IMPL.parseString(str, def);
    }

    /**
     * 将指定字符串解析为布尔值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 布尔值
     */
    public static boolean parseBoolean(String str, boolean def) {
        return IMPL.parseBoolean(str, def);
    }

    /**
     * 将指定对象转为字符串后解析为布尔值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 布尔值
     */
    public static boolean parseBoolean(Object obj, boolean def) {
        return IMPL.parseBoolean(obj, def);
    }

    /**
     * 将指定字符串解析为整数值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 整数值
     */
    public static int parseInt(String str, int def) {
        return IMPL.parseInt(str, def);
    }

    /**
     * 将指定对象转为字符串后解析为整数值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 整数值
     */
    public static int parseInt(Object obj, int def) {
        return IMPL.parseInt(obj, def);
    }

    /**
     * 将指定字符串解析为长整数值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 整数值
     */
    public static long parseLong(String str, long def) {
        return IMPL.parseLong(str, def);
    }

    /**
     * 将指定对象转为字符串后解析为长整数值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 整数值
     */
    public static long parseLong(Object obj, long def) {
        return IMPL.parseLong(obj, def);
    }

    /**
     * 将指定字符串解析为单精度浮点值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 单精度浮点值
     */
    public static float parseFloat(String str, float def) {
        return IMPL.parseFloat(str, def);
    }

    /**
     * 将指定对象转为字符串后解析为单精度浮点值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 单精度浮点值
     */
    public static float parseFloat(Object obj, float def) {
        return IMPL.parseFloat(obj, def);
    }

    /**
     * 将指定字符串解析为双精度浮点值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 双精度浮点值
     */
    public static double parseDouble(String str, double def) {
        return IMPL.parseDouble(str, def);
    }

    /**
     * 将指定对象转为字符串后解析为双精度浮点值，解析失败返回指定的默认值。
     *
     * @param obj 待处理对象
     * @param def 默认值
     * @return 双精度浮点值
     */
    public static double parseDouble(Object obj, double def) {
        return IMPL.parseDouble(obj, def);
    }

    /**
     * 将指定字符串解析为任意精度大数值，解析失败返回指定的默认值。
     *
     * @param str 待处理的字符串
     * @param def 默认值
     * @return 任意精度大数值
     */
    public static BigDecimal parseBigDecimal(String str, BigDecimal def) {
        return IMPL.parseBigDecimal(str, def);
    }

    /**
     * 判断指定字符串是否以指定前缀开始，忽略大小写。
     *
     * @param str    待处理的字符串
     * @param prefix 前缀字符串
     * @return true / false
     */
    public static boolean startsWithIgnoreCase(String str, String prefix) {
        return IMPL.startsWithIgnoreCase(str, prefix);
    }

    /**
     * 判断指定字符串是否以指定后缀字符串结尾，忽略大小写。
     *
     * @param str    待处理的字符串
     * @param suffix 后缀字符串
     * @return true / false
     */
    public static boolean endsWithIgnoreCase(String str, String suffix) {
        return IMPL.endsWithIgnoreCase(str, suffix);
    }

    /**
     * 判断指定字符串是否以指定字符开始，如果不是，则增加指定字符前缀。
     *
     * @param str 待处理的字符串
     * @param c   期望首字符
     * @return 以期望首字符开始的字符串
     */
    public static String startsWithChar(String str, char c) {
        return IMPL.startsWithChar(str, c);
    }

    /**
     * 判断指定字符串是否以指定字符结尾，如果不是，则增加指定字符后缀。
     *
     * @param str 待处理的字符串
     * @param c   期望尾字符
     * @return 以期望尾字符开始的字符串
     */
    public static String endsWithChar(String str, char c) {
        return IMPL.endsWithChar(str, c);
    }

    /**
     * 判断指定字符串是否以指定字符开始，如果是，则删除指定字符前缀。
     *
     * @param str 待处理的字符串
     * @param c   首字符
     * @return 不以指定首字符开始的字符串
     */
    public static String startsWithoutChar(String str, char c) {
        return IMPL.startsWithoutChar(str, c);
    }

    /**
     * 判断指定字符串是否以指定字符结尾，如果是，则删除指定字符后缀。
     *
     * @param str 待处理的字符串
     * @param c   尾字符
     * @return 不以指定尾字符开始的字符串
     */
    public static String endsWithoutChar(String str, char c) {
        return IMPL.endsWithoutChar(str, c);
    }

    /**
     * 首字母转大写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    public static String capitalize(String str) {
        return IMPL.capitalize(str);
    }

    /**
     * 首字母转小写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    public static String unCapitalize(String str) {
        return IMPL.unCapitalize(str);
    }

    /**
     * 将字符串中的单词转为下划线分割的全小写字符，通常用于类名称的转换
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    public static String underscore(String str) {
        return IMPL.underscore(str);
    }

    /**
     * 字符串转大写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    public static String upperCase(String str) {
        return IMPL.upperCase(str);
    }

    /**
     * 字符串转小写
     *
     * @param str 待处理的字符串
     * @return 处理后的字符串
     */
    public static String lowerCase(String str) {
        return IMPL.lowerCase(str);
    }

    /**
     * 将指定字符串中的所有值进行匹配替换。
     *
     * @param str  待处理的字符串
     * @param from 要被替换的字符串
     * @param to   替换为的字符串
     * @return 处理后的字符串
     */
    public static String replace(String str, String from, String to) {
        return IMPL.replace(str, from, to);
    }

    /**
     * 将指定字符串中的所有匹配值删除
     *
     * @param str     待处理的字符串
     * @param pattern 匹配字符串
     * @return 处理后的字符串
     */
    public static String delete(String str, String pattern) {
        return IMPL.delete(str, pattern);
    }

    /**
     * 将指定字符串中的所有匹配值中包含字符删除
     *
     * @param str   待处理的字符串
     * @param chars 字符集合
     * @return 处理后的字符串
     */
    public static String deleteAny(String str, String chars) {
        return IMPL.deleteAny(str, chars);
    }

}
