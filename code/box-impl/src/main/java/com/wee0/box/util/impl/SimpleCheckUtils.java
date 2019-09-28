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

import com.wee0.box.util.ICheckUtils;

import java.io.ObjectStreamException;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:18
 * @Description 一个简单的检查工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleCheckUtils implements ICheckUtils {

    // 默认的消息参数占位符
    private static final String DEF_PLACEHOLDER = "%s";

    /**
     * 检查指定对象是否为空，如果为空，则抛出一个空指针异常。
     *
     * @param <T>       待检查对象类型
     * @param reference 待检查对象
     * @return 检查通过的对象
     */
    public <T> T checkNotNull(T reference) {
        if (null == reference)
            throw new NullPointerException();
        return reference;
    }

    /**
     * 检查指定对象是否为空，如果为空，则抛出包含指定错误信息的空指针异常。
     *
     * @param <T>          待检查对象类型
     * @param reference    待检查对象
     * @param errorMessage 错误信息
     * @return 检查通过的对象
     */
    public <T> T checkNotNull(T reference, Object errorMessage) {
        if (null == reference)
            throw new NullPointerException(String.valueOf(errorMessage));
        return reference;
    }

    /**
     * 检查指定对象是否为空，如果为空，则抛出包含指定错误信息的空指针异常。
     *
     * @param <T>                  待检查对象类型
     * @param reference            待检查对象
     * @param errorMessageTemplate 错误信息模板
     * @param errorMessageParams   错误信息模板参数
     * @return 检查通过的对象
     */
    public <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageParams) {
        if (null == reference)
            throw new NullPointerException(_format(errorMessageTemplate, errorMessageParams));
        return reference;
    }

    /**
     * 检查指定字符串前后去空格后是否为空，如果为空或者前后去空格后为空字符串，则抛出包含指定错误信息的空指针异常。
     *
     * @param value                待检查字符串
     * @param errorMessageTemplate 错误信息模板
     * @param errorMessageParams   错误信息模板参数
     * @return 前后去空格的待检查对象
     */
    public String checkNotTrimEmpty(String value, String errorMessageTemplate, Object... errorMessageParams) {
        if (null == value || 0 == (value = value.trim()).length())
            throw new NullPointerException(_format(errorMessageTemplate, errorMessageParams));
        return value;
    }

    /**
     * 检查指定字符串是否为null或者前后去空格后为空字符串，如果是返回指定的默认值，不是返回前后去空格的字符串。
     *
     * @param value    要检查的字符串
     * @param defValue 为空时返回的默认字符串
     * @return 前后去空格的字符串 / 默认字符串
     */
    public String checkTrimEmpty(String value, String defValue) {
        if (null == value || 0 == (value = value.trim()).length())
            return defValue;
        return value;
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的参数无效异常。
     *
     * @param expression   判断表达式
     * @param errorMessage 错误信息
     */
    public void checkArgument(boolean expression, String errorMessage) {
        if (expression)
            throw new IllegalArgumentException(errorMessage);
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的参数无效异常。
     *
     * @param expression           判断表达式
     * @param errorMessageTemplate 错误信息模板
     * @param errorMessageParams   错误信息模板参数
     */
    public void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageParams) {
        if (expression)
            throw new IllegalArgumentException(_format(errorMessageTemplate, errorMessageParams));
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的状态无效异常。
     *
     * @param expression   判断表达式
     * @param errorMessage 错误信息
     */
    public void checkState(boolean expression, String errorMessage) {
        if (expression)
            throw new IllegalStateException(String.valueOf(errorMessage));
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的状态无效异常。
     *
     * @param expression           判断表达式
     * @param errorMessageTemplate 错误信息模板
     * @param errorMessageParams   错误信息模板参数
     */
    public void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageParams) {
        if (expression)
            throw new IllegalStateException(_format(errorMessageTemplate, errorMessageParams));
    }

    /**
     * 检查元素索引，必须处于0到size之间，否则抛出包含指定错误信息的索引越界异常。
     *
     * @param index  索引
     * @param length 数据长度
     * @param desc   错误信息主体
     */
    public void checkIndex(int index, int length, String desc) {
        if (0 > length)
            throw new IllegalArgumentException(_format("%s length can not be less than 0.", desc));
        if (0 > index)
            throw new IndexOutOfBoundsException(_format("%s index can not be less than 0.", desc));
        if (index >= length)
            throw new IndexOutOfBoundsException(_format("%s index( %s ) must not be greater than length( %s ).", desc, index, length));
    }

    /**
     * 检查元素索引，必须处于0到size之间，否则抛出默认的索引越界异常信息。
     *
     * @param index  索引
     * @param length 数据长度
     */
    public void checkIndex(int index, int length) {
        if (0 > length)
            throw new IllegalArgumentException("length can not be less than 0.");
        if (0 > index)
            throw new IndexOutOfBoundsException("index can not be less than 0.");
        if (index >= length)
            throw new IndexOutOfBoundsException(_format("index( %s ) must not be greater than length( %s ).", index, length));
    }

    /**
     * 使用指定参数列表格式化参数字符串，仅支持'%s'占位符。
     *
     * @param template 参数字符串
     * @param args     参数列表
     * @return 格式化后的字符串
     */
    private static String _format(String template, Object... args) {

        // null -> "null"
        template = String.valueOf(template);

        // 参数数量
        final int _argsCount = args.length;
        // 截取每个'%s'占位符替换为对应的参数。
        StringBuilder _builder = new StringBuilder(template.length() + 16 * _argsCount);
        int _templateStart = 0;
        int _i = 0;
        while (_i < _argsCount) {
            int _placeholderStart = template.indexOf(DEF_PLACEHOLDER, _templateStart);
            if (-1 == _placeholderStart) {
                break;
            }
            _builder.append(template, _templateStart, _placeholderStart);
            _builder.append(args[_i++]);
            _templateStart = _placeholderStart + 2;
        }
        _builder.append(template, _templateStart, template.length());

        // 如果有多余的参数，则直接作为参数序列附加。
        if (_i < _argsCount) {
            _builder.append(" [");
            _builder.append(args[_i++]);
            while (_i < _argsCount) {
                _builder.append(", ");
                _builder.append(args[_i++]);
            }
            _builder.append(']');
        }

        return _builder.toString();
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleCheckUtils() {
        if (null != SimpleCheckUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleCheckUtilsHolder {
        private static final SimpleCheckUtils _INSTANCE = new SimpleCheckUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleCheckUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleCheckUtils me() {
        return SimpleCheckUtilsHolder._INSTANCE;
    }

}
