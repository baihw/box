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

import com.wee0.box.BoxConstants;
import com.wee0.box.util.IStringUtils;

import java.io.ObjectStreamException;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:26
 * @Description 一个简单的字符串处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleStringUtils implements IStringUtils {

    @Override
    public boolean isEmpty(String str) {
        return (null == str || 0 == str.length());
    }

    @Override
    public String trim(String str) {
        if (isEmpty(str))
            return BoxConstants.EMPTY_STRING;
        return str.trim();
    }

    @Override
    public boolean hasText(String str) {
        if (isEmpty(str))
            return false;
        for (int _i = 0, _iLen = str.length(); _i < _iLen; _i++) {
            if (!Character.isWhitespace(str.charAt(_i))) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String trimEmptyDef(String str, String def) {
        str = trim(str);
        if (isEmpty(str))
            return def;
        return str;
    }

    @Override
    public String parseString(Object str, String def) {
        if (null == str)
            return def;
        return trimEmptyDef(str.toString(), def);
    }

    @Override
    public boolean parseBoolean(String str, boolean def) {
        if (isEmpty(str))
            return def;
        str = trim(str);
        return Boolean.parseBoolean(str);
    }

    @Override
    public boolean parseBoolean(Object obj, boolean def) {
        if (null == obj)
            return def;
        return parseBoolean(obj.toString(), def);
    }

    @Override
    public int parseInt(String str, int def) {
        if (isEmpty(str))
            return def;
        str = trim(str);
        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    public int parseInt(Object obj, int def) {
        if (null == obj)
            return def;
        return parseInt(obj.toString(), def);
    }

    @Override
    public long parseLong(String str, long def) {
        if (isEmpty(str))
            return def;
        str = trim(str);
        try {
            return Long.parseLong(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    public long parseLong(Object obj, long def) {
        if (null == obj)
            return def;
        return parseLong(obj.toString(), def);
    }

    @Override
    public float parseFloat(String str, float def) {
        if (isEmpty(str))
            return def;
        str = trim(str);
        try {
            return Float.parseFloat(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    public float parseFloat(Object obj, float def) {
        if (null == obj)
            return def;
        return parseFloat(obj.toString(), def);
    }

    @Override
    public double parseDouble(String str, double def) {
        if (isEmpty(str))
            return def;
        str = trim(str);
        try {
            return Double.parseDouble(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    public double parseDouble(Object obj, double def) {
        if (null == obj)
            return def;
        return parseDouble(obj.toString(), def);
    }

    @Override
    public BigDecimal parseBigDecimal(String str, BigDecimal def) {
        if (isEmpty(str))
            return def;
        str = trim(str);
        try {
            return new BigDecimal(str);
        } catch (NumberFormatException e) {
            return def;
        }
    }

    @Override
    public boolean startsWithIgnoreCase(String str, String prefix) {
        if (null == str || null == prefix || str.length() < prefix.length())
            return false;
        return str.regionMatches(true, 0, prefix, 0, prefix.length());
    }

    @Override
    public boolean endsWithIgnoreCase(String str, String suffix) {
        if (null == str || null == suffix || str.length() < suffix.length())
            return false;
        return str.regionMatches(true, str.length() - suffix.length(), suffix, 0, suffix.length());
    }

    @Override
    public String startsWithChar(String str, char c) {
        if (null == str)
            return null;
        if (0 == str.length())
            return String.valueOf(c);
        if (c == str.charAt(0))
            return str;
        return c + str;
    }

    @Override
    public String endsWithChar(String str, char c) {
        if (null == str)
            return null;
        if (0 == str.length())
            return String.valueOf(c);
        if (c == str.charAt(str.length() - 1))
            return str;
        return str + c;
    }

    @Override
    public String startsWithoutChar(String str, char c) {
        if (null == str || 0 == str.length())
            return str;
        if (c == str.charAt(0))
            return str.substring(1);
        return str;
    }

    @Override
    public String endsWithoutChar(String str, char c) {
        if (null == str || 0 == str.length())
            return str;
        if (c == str.charAt(str.length() - 1))
            return str.substring(0, str.length() - 1);
        return str;
    }

    @Override
    public String capitalize(String str) {
        if (isEmpty(str))
            return str;
        char _char0 = str.charAt(0);
        char _char0Upper = Character.toUpperCase(_char0);
        if (_char0 == _char0Upper)
            return str;
        char[] _chars = str.toCharArray();
        _chars[0] = _char0Upper;
        return new String(_chars, 0, _chars.length);
    }

    @Override
    public String unCapitalize(String str) {
        if (isEmpty(str))
            return str;
        char _char0 = str.charAt(0);
        char _char0Lower = Character.toLowerCase(_char0);
        if (_char0 == _char0Lower)
            return str;
        char[] _chars = str.toCharArray();
        _chars[0] = _char0Lower;
        return new String(_chars, 0, _chars.length);
    }

    @Override
    public String underscore(String str) {
        if (null == str || 0 == (str = str.trim()).length())
            return BoxConstants.EMPTY_STRING;
        StringBuilder _sb = new StringBuilder();
        _sb.append(Character.toLowerCase(str.charAt(0)));
        for (int _i = 1, _iLen = str.length(); _i < _iLen; _i++) {
            char _c = str.charAt(_i);
            if (Character.isUpperCase(_c)) {
                _sb.append('_').append(Character.toLowerCase(_c));
            } else {
                _sb.append(_c);
            }
        }
        return _sb.toString();
    }

    @Override
    public String upperCase(String str) {
        if (isEmpty(str))
            return str;
        return str.toUpperCase(Locale.US);
    }

    @Override
    public String lowerCase(String str) {
        if (isEmpty(str))
            return str;
        return str.toLowerCase(Locale.US);
    }

    @Override
    public String replace(String str, String from, String to) {
        if (isEmpty(str) || isEmpty(from) || null == to)
            return str;
        int _index = str.indexOf(from);
        if (-1 == _index)
            return str;

        int _capacity = str.length();
        if (to.length() > from.length()) {
            _capacity += 32;
        }
        StringBuilder _sb = new StringBuilder(_capacity);

        int _pos = 0;
        int _patLen = from.length();
        while (_index >= 0) {
            _sb.append(str.substring(_pos, _index));
            _sb.append(to);
            _pos = _index + _patLen;
            _index = str.indexOf(from, _pos);
        }

        _sb.append(str.substring(_pos));
        return _sb.toString();
    }

    @Override
    public String delete(String str, String pattern) {
        return replace(str, pattern, BoxConstants.EMPTY_STRING);
    }

    @Override
    public String deleteAny(String str, String chars) {
        if (isEmpty(str) || isEmpty(chars))
            return str;
        StringBuilder _sb = new StringBuilder(str.length());
        for (int _i = 0, _iLen = str.length(); _i < _iLen; _i++) {
            char _c = str.charAt(_i);
            if (chars.indexOf(_c) == -1) {
                _sb.append(_c);
            }
        }
        return _sb.toString();
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleStringUtils() {
        if (null != SimpleStringUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleStringUtilsHolder {
        private static final SimpleStringUtils _INSTANCE = new SimpleStringUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleStringUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleStringUtils me() {
        return SimpleStringUtilsHolder._INSTANCE;
    }

}
