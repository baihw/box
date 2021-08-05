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

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 22:12
 * @Description 校验处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IValidateUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleValidateUtils";

    /**
     * 使用内置的常用校验规则进行数据合法性校验,所有规则都不允许数据为空.
     * 内置规则表达式列表：
     * *：检测是否有输入，可以输入任何字符，不留空即可通过验证, 默认错误提示: "不能为空！",
     * *6-16：检测是否为6到16位任意字符, 默认错误提示: "请填写6到16位任意字符！",
     * n：数字类型, 默认错误提示: "请填写数字！",
     * n6-16：6到16位数字, 默认错误提示: "请填写6到16位数字！",
     * s：英文字符串类型, 默认错误提示: "只能输入英文字母和数字！",
     * s6-18：6到18位英文字符串, 默认错误提示: "请填写6到18位英文字母和数字！",
     * S：字符串类型, 默认错误提示: "不能输入特殊字符！",
     * S6-18：6到18位字符串, 默认错误提示: "请填写6到18位字符！",
     * p：验证是否为邮政编码, 默认错误提示: "请填写邮政编码！",
     * m：手机号码格式, 默认错误提示: "请填写手机号码！",
     * e：email格式, 默认错误提示: "邮箱地址格式不对！",
     * url：验证字符串是否为网址, 默认错误提示: "请填写网址！"
     * ipv4：验证字符串是否为规范的IPv4地址, 默认错误提示: "请填写IPv4地址！"
     * reg: 验证字符串是否匹配指定的正则表达式，默认错误提示为："参数非法!"
     *
     * @param value        检查值
     * @param pattern      内置规则表达式
     * @param ignoreNull   校验时是否对空值进行特殊处理,为true表示值为空时不进行校验直接通过,不为空时才执行校验规则.为false时值为空直接返回校验失败.
     * @param byteLenModel 校验时是否按字节处理长度,true表示按字节处理长度,即英文视为1个字节,中文视为2个字节.
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validatePattern(String value, String pattern, boolean ignoreNull, boolean byteLenModel);

    /**
     * ignoreNull,byteLenModel默认为false，进行数据校验。
     *
     * @param value   检查值
     * @param pattern 内置规则表达式
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validatePattern(String value, String pattern);

    /**
     * 对指定字符串进行正则表达式校验。
     *
     * @param value           检查值
     * @param regExpression   校验使用的正则表达式
     * @param isCaseSensitive 大小写是否敏感，设为false则忽略大小写
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateRegex(String value, String regExpression, boolean isCaseSensitive);

    /**
     * 对指定字符串使用指定的日期格式进行日期类型校验
     *
     * @param value      检查值
     * @param min        最小值
     * @param max        最大值
     * @param dateFormat 日期格式
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateDate(String value, String min, String max, String dateFormat);

    /**
     * 对指定字符串进行日期类型校验,默认格式为:yyyy-MM-dd
     *
     * @param value 检查值
     * @param min   最小值
     * @param max   最大值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateDate(String value, String min, String max);

    /**
     * 校验两个字符串是否相同
     *
     * @param value  检查值
     * @param expect 期望值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateEqualString(String value, String expect);

    /**
     * 校验两个整数是否相等
     *
     * @param value  检查值
     * @param expect 期望值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateEqualInteger(String value, int expect);

    /**
     * 对指定字符串进行双精度浮点型校验
     *
     * @param value 检查值
     * @param min   最小值
     * @param max   最大值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateDouble(String value, double min, double max);

    /**
     * 对指定字符串进行长整型校验
     *
     * @param value 检查值
     * @param min   最小值
     * @param max   最大值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateLong(String value, long min, long max);

    /**
     * 对指定字符串进行整型校验
     *
     * @param value 检查值
     * @param min   最小值
     * @param max   最大值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateInteger(String value, int min, int max);

    /**
     * 对指定字符串进行字符串长度校验。
     *
     * @param value      检查值
     * @param minLen     最小长度
     * @param maxLen     最大长度
     * @param allowEmpty 是否允许为空字符串，如果为false,无值或者前后去空格之后为空的字符串不能通过校验
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateStringLength(String value, int minLen, int maxLen, boolean allowEmpty);

    /**
     * 对指定字符串进行字符串长度校验，不允许空。
     *
     * @param value  检查值
     * @param minLen 最小长度
     * @param maxLen 最大长度
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateStringLength(String value, int minLen, int maxLen);

    /**
     * 对指定字符串进行正则表达式校验,忽略大小写
     *
     * @param value         检查值
     * @param regExpression 校验使用的正则表达式
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateRegex(String value, String regExpression);

    /**
     * 对指定字符串进行email地址格式校验。
     *
     * @param value 检查值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateEmail(String value);

    /**
     * 对指定字符串进行url地址校验。
     *
     * @param value 检查值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateUrl(String value);

    /**
     * 对指定字符串进行IPv4地址校验。
     *
     * @param value 检查值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validateIPv4(String value);

    /**
     * 对指定端口号进行校验。
     *
     * @param value 检查值
     * @return 通过返回 true, 否则返回 false
     */
    public boolean validatePort(String value);

}
