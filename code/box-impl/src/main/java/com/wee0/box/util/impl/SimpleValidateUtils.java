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

package com.wee0.box.util.impl;

import com.wee0.box.exception.BoxRuntimeException;
import com.wee0.box.util.IValidateUtils;
import com.wee0.box.util.shortcut.CheckUtils;
import com.wee0.box.util.shortcut.StringUtils;

import java.io.ObjectStreamException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/10/13 22:22
 * @Description 一个简单的校验处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleValidateUtils implements IValidateUtils {

    // 破折号,用来分割a-b
    private static final String _DASH = "-";

    /**
     * 日期格式1--yyyy-MM-dd
     */
    public static final String DATE_PATTERN_1 = "yyyy-MM-dd";
    /**
     * 日期格式2(14位紧凑表示法)--yyyyMMddHHmmss
     */
    public static final String DATE_PATTERN_2 = "yyyyMMddHHmmss";

    /**
     * 正则表达式字符串--email
     */
    public static final String PATTERN_EMAIL = "\\b(^['_A-Za-z0-9-]+(\\.['_A-Za-z0-9-]+)*@([A-Za-z0-9-])+(\\.[A-Za-z0-9-]+)*((\\.[A-Za-z0-9]{2,})|(\\.[A-Za-z0-9]{2,}\\.[A-Za-z0-9]{2,}))$)\\b";

    /**
     * 正则表达式字符串--email1
     */
    public static final String PATTERN_EMAIL1 = "\\w+([-+.']\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*";

    /**
     * 正则表达式字符串--url
     */
    public static final String PATTERN_URL = "(\\w+:\\/\\/)?\\w+(\\.\\w+)+.*";

    /**
     * 正则表达式字符串--ipv4
     */
    public static final String PATTERN_IPV4 = "(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])\\.(\\d{1,2}|1\\d\\d|2[0-4]\\d|25[0-5])";

    /**
     * 正则表达式字符串--手机号码
     */
    public static final String PATTERN_MOBILE = "13[0-9]{9}$|14[0-9]{9}|15[0-9]{9}$|18[0-9]{9}$";

    /**
     * 正则表达式字符串--邮政编码
     */
    public static final String PATTERN_ZIPCODE = "[0-9]{6}";

    /**
     * 正则表达式字符串--字符串类型,不包括特殊字符
     */
    public static final String PATTERN_STRING = "[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]+";

    /**
     * 正则表达式字符串--字符串类型限定长度,不包括特殊字符,需要两个参数,最小长度,最大长度.
     */
    public static final String PATTERN_STRING_N_N = "[\\u4E00-\\u9FA5\\uf900-\\ufa2d\\w\\.\\s]{%1$s,%2$s}"; // {6,18}

    /**
     * 正则表达式字符串--英文字符，a-z,A-Z,0-9,_
     */
    public static final String PATTERN_STR = "[\\w]+";

    /**
     * 正则表达式字符串--英文字符，a-z,A-Z,0-9,_,需要两个参数,最小长度,最大长度.
     */
    public static final String PATTERN_STR_N_N = "[\\w]{%1$s,%2$s}";

    /**
     * 正则表达式字符串--数字类型
     */
    public static final String PATTERN_NUMBER = "\\d+";

    /**
     * 正则表达式字符串--数字类型限定长度,需要两个参数,最小值,最大值.
     */
    public static final String PATTERN_NUMBER_N_N = "\\d{%1$s,%2$s}"; // {6,18}

    /**
     * 正则表达式字符串--任意类型,任何字符,不留空即可通过验证
     */
    public static final String PATTERN_ALL = "[\\w\\W]+";

    /**
     * 正则表达式字符串--任意类型限定长度,需要两个参数,最小长度,最大长度.
     */
    public static final String PATTERN_ALL_N_N = "[\\w\\W]{%1$s,%2$s}"; // {6,18}

    /**
     * 正则表达式字符串--匹配指定前缀类型长度的表达式.需要一个参数,前缀如:*,s,n,r s6-18, n3-12, *5-15, r(.+)(\\d+)-(\\d+)
     */
    public static final String PATTERN_MATCH_PREFIX_N_N = "%1$s(\\d+)-(\\d+)";

    /**
     * 正则表达式字符串--匹配指定类型长度的表达式.如: s6-18, S6-18, n3-12, *5-15
     */
    public static final String PATTERN_MATCH_N_N = "[\\*|s|S|n]([\\d\\.]+)-([\\d\\.]+)"; // "(.+)(\\d+)-(\\d+)";

    /**
     * 正则表达式字符串--匹配指定类型长度的表达式.如: S6, s6-18, S6-18, n3-12, *5-15
     */
    public static final String PATTERN_MATCH_N_PLUS = "[\\*|s|S|n]([\\d]+)-?([\\d]*)";

    /**
     * 正则表达式字符串--匹配n前缀的长度表达式.如:n6-18.建议用来做数字类型长度表示使用。
     */
    public static final String PATTERN_MATCH_N_N_N = "n(\\d+)-(\\d+)";

    /**
     * 正则表达式字符串--匹配s前缀的长度表达式.如:s6-18.建议用来做常规字符串类型长度表示使用。
     */
    public static final String PATTERN_MATCH_S_N_N = "s(\\d+)-(\\d+)";

    /**
     * 正则表达式字符串--匹配S前缀的长度表达式.如:S6-18.建议用来做非中文字符串类型长度表示使用。
     */
    public static final String PATTERN_MATCH_S1_N_N = "S(\\d+)-(\\d+)";

    /**
     * 正则表达式字符串--匹配*前缀的长度表达式.如:*6-18.建议用来做任意非空字符串类型长度表示使用。
     */
    public static final String PATTERN_MATCH_ALL_N_N = "\\*(\\d+)-(\\d+)";

    /**
     * 正则表达式字符串--匹配文件名
     */
    public static final String PATTERN_FILENAME = "[^!#$%&`~,;。=?$'*/\\s\\^\\x22\\\\]+";

    /**
     * 正则表达式字符串--匹配文件目录
     */
    public static final String PATTERN_DIRNAME = "/{1}[^!#$%&`~,;。=?$'*\\s\\^\\x22\\\\]+/{1}|/{1}";


    @Override
    public boolean validatePattern(String value, String pattern, boolean ignoreNull, boolean byteLenModel) {
        pattern = CheckUtils.checkNotTrimEmpty(pattern, "pattern can not be empty!");
        value = CheckUtils.checkTrimEmpty(value, null);
        if (null == value) {
//            if (ignoreNull && !"*".equals(pattern))
//                return true;
//            return false;
            return ignoreNull;
        }

        boolean _result = false;

        // 如果指定了使用字节校验模式,则将中文字符替换为两个字符进行长度计算.
        if (byteLenModel)
            value = value.replaceAll("[^\\x00-\\xff]", "aa");

        if ("*".equals(pattern)) {
            _result = value.length() > 0;
        } else if ("s".equals(pattern)) {
            _result = validateRegex(value, PATTERN_STRING);
        } else if ("S".equals(pattern)) {
            _result = validateRegex(value, PATTERN_STR);
        } else if ("n".equals(pattern)) {
            _result = validateRegex(value, PATTERN_NUMBER);
        } else if ("m".equals(pattern)) {
            _result = validateRegex(value, PATTERN_MOBILE);
        } else if ("e".equals(pattern)) {
            _result = validateRegex(value, PATTERN_EMAIL);
        } else if ("url".equals(pattern)) {
            _result = validateRegex(value, PATTERN_URL);
        } else if ("ipv4".equals(pattern)) {
            _result = validateRegex(value, PATTERN_IPV4);
        } else if ("p".equals(pattern)) {
            _result = validateRegex(value, PATTERN_ZIPCODE);
        } else if (pattern.matches(PATTERN_MATCH_N_PLUS)) {
            // 如果是长度限定格式,则取出长度的开始与结束值.
            int _dashIndex = pattern.indexOf(_DASH);
            String minStr, maxStr;
            if (-1 == _dashIndex) {
                minStr = maxStr = pattern.substring(1);
            } else {
                minStr = pattern.substring(1, _dashIndex);
                maxStr = pattern.substring(_dashIndex + 1);
            }

            if (pattern.startsWith("n")) {
                _result = validateDouble(value, minStr, maxStr);
//			    _result = validateRegex( value, String.format( PATTERN_NUMBER_N_N, minStr, maxStr ) );
            } else if (-1 != pattern.indexOf('.')) {
                throw new BoxRuntimeException("除数字类型外其它类型的范围限制数字不支持小数点!");
            } else if (pattern.startsWith("*")) {
                _result = validateRegex(value, String.format(PATTERN_ALL_N_N, minStr, maxStr));
            } else if (pattern.startsWith("s")) {
                _result = validateRegex(value, String.format(PATTERN_STRING_N_N, minStr, maxStr));
            } else if (pattern.startsWith("S")) {
                _result = validateRegex(value, String.format(PATTERN_STR_N_N, minStr, maxStr));
            }
        } else if (StringUtils.startsWithIgnoreCase(pattern, "reg:")) {
            final String regStr = pattern.substring(4);
            _result = validateRegex(value, regStr);
        } else {
            throw new BoxRuntimeException("不支持的校验类型: " + pattern);
        }
        return _result;
    }

    @Override
    public boolean validatePattern(String value, String pattern) {
        return validatePattern(value, pattern, false, false);
    }

    @Override
    public boolean validateRegex(String value, String regExpression, boolean isCaseSensitive) {
        if (null == value)
            return false;
        Pattern _pattern = isCaseSensitive ? Pattern.compile(regExpression) : Pattern.compile(regExpression, Pattern.CASE_INSENSITIVE);
        Matcher _matcher = _pattern.matcher(value);
        return _matcher.matches();
    }

    @Override
    public boolean validateRegex(String value, String regExpression) {
        return validateRegex(value, regExpression, false);
    }

    @Override
    public boolean validateDate(String value, String min, String max, String formatPattern) {
        try {
            // Date valueDate = Date.valueOf(value); 为了兼容 64位 JDK,放弃此写法
            SimpleDateFormat _format = new SimpleDateFormat(formatPattern);
            Date _value = _format.parse(value);
            Date _min = _format.parse(min);
            Date _max = _format.parse(max);
            if (_value.before(_min) || _value.after(_max))
                return false;
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateDate(String value, String min, String max) {
        return validateDate(value, min, max, DATE_PATTERN_1);
    }

    @Override
    public boolean validateEqualString(String value, String expect) {
        if (null == value || null == expect || (!value.equals(expect)))
            return false;
        return true;
    }

    @Override
    public boolean validateEqualInteger(String value, int expect) {
        try {
            return expect == Integer.parseInt(value);
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateDouble(String value, double min, double max) {
        try {
            double _value = Double.parseDouble(value);
            return _value <= max && _value >= min;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 对指定字符串进行双精度浮点型校验
     *
     * @param value 要检查的字符串对象
     * @param min   最小值字符串
     * @param max   最大值字符串
     * @return true / false
     */
    private boolean validateDouble(String value, String min, String max) {
        try {
            double _value = Double.parseDouble(value);
            double _min = Double.parseDouble(min);
            double _max = Double.parseDouble(max);
            return _value <= _max && _value >= _min;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateLong(String value, long min, long max) {
        try {
            long _value = Long.parseLong(value);
            return _value >= min && _value <= max;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateInteger(String value, int min, int max) {
        try {
            int _value = Integer.parseInt(value);
            return _value >= min && _value <= max;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean validateStringLength(String value, int minLen, int maxLen, boolean allowEmpty) {
        value = CheckUtils.checkTrimEmpty(value, null);
        if (null == value) {
            return allowEmpty;
        }
        int _len = value.length();
        return _len >= minLen && _len <= maxLen;
    }

    @Override
    public boolean validateStringLength(String value, int minLen, int maxLen) {
        return validateStringLength(value, minLen, maxLen, false);
    }

    @Override
    public boolean validateEmail(String value) {
        if (null == value || 0 == (value = value.trim()).length())
            return false;
        return validateRegex(value, PATTERN_EMAIL);
    }

    @Override
    public boolean validateUrl(String value) {
        if (null == value || 0 == (value = value.trim()).length())
            return false;
        try {
            // URL暂不支持https协议,转换为http协议.
            if (value.regionMatches(true, 0, "https:", 0, 6)) {
                value = "http:" + value.substring(6);
            }
            new URL(value);
        } catch (MalformedURLException e) {
            return false;
        }
        return true;
    }

    @Override
    public boolean validateIPv4(String value) {
        if (null == value || 0 == (value = value.trim()).length())
            return false;
        return validateRegex(value, PATTERN_IPV4);
    }

    @Override
    public boolean validatePort(String value) {
        return validateInteger(value, 0, 65535);
    }

    /**
     * 获取指定规则表达式的默认错误提示。
     *
     * @param pattern   匹配模式
     * @param fieldName 字段名
     * @return 错误消息
     */
    String getDefaultErrorInfo(String pattern, String fieldName) {
        String result = null;

        if (null == pattern || 0 == (pattern = pattern.trim()).length())
            return result;

        pattern = pattern.trim();

        boolean hasFieldName = (null == fieldName);
        if ("*".equals(pattern))
            result = hasFieldName ? "不能为空!" : fieldName + "不能为空!";
        else if ("n".equals(pattern))
            result = hasFieldName ? "只能为数字类型!" : fieldName + "只能为数字类型!";
        else if ("s".equals(pattern))
            result = hasFieldName ? "不允许包含特殊字符!" : fieldName + "不允许包含特殊字符!";
        else if ("S".equals(pattern))
            result = hasFieldName ? "只能为英文字符!" : fieldName + "只能为英文字符!";
        else if ("p".equals(pattern))
            result = hasFieldName ? "不是规范的邮政编码!" : fieldName + "不是规范的邮政编码!";
        else if ("m".equals(pattern))
            result = hasFieldName ? "不是规范的手机号码!" : fieldName + "不是规范的手机号码!";
        else if ("e".equals(pattern))
            result = hasFieldName ? "不是规范的邮箱地址!" : fieldName + "不是规范的邮箱地址!";
        else if ("url".equals(pattern))
            result = hasFieldName ? "不是规范的网址!" : fieldName + "不是规范的网址!";
        else if ("ipv4".equals(pattern))
            result = hasFieldName ? "不是规范的IPv4地址!" : fieldName + "不是规范的IPv4地址!";
        else if (StringUtils.startsWithIgnoreCase(pattern, "reg:")) {
            String regStr = "不符合指定的正则:" + pattern.substring(4) + "!";
            result = hasFieldName ? regStr : fieldName + regStr;
        } else if (pattern.matches(PATTERN_MATCH_N_PLUS)) {
            // 如果是长度限定格式,则取出长度的开始与结束值.
            String minMaxStr = "长度只为能" + pattern.substring(1) + "位!";
            result = hasFieldName ? minMaxStr : fieldName + minMaxStr;
        }
        return result;
    }

    /**
     * 获取指定规则表达式的默认错误提示。
     *
     * @param pattern 匹配模式
     * @return 错误消息
     */
    String getDefaultErrorInfo(String pattern) {
        return getDefaultErrorInfo(pattern, null);
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleValidateUtils() {
        if (null != SimpleValidateUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleValidateUtilsHolder {
        private static final SimpleValidateUtils _INSTANCE = new SimpleValidateUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleValidateUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleValidateUtils me() {
        return SimpleValidateUtilsHolder._INSTANCE;
    }
}
