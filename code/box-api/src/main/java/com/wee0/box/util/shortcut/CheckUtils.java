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
import com.wee0.box.util.ICheckUtils;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:06
 * @Description 检查工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class CheckUtils {

    // 实现类实例
    private static final ICheckUtils IMPL = BoxConfig.impl().getInterfaceImpl(ICheckUtils.class);

    /**
     * 检查指定对象是否为空，如果为空，则抛出一个空指针异常。
     *
     * @param <T>       待检查对象类型
     * @param reference 待检查对象
     * @return 检查通过的对象
     */
    public static <T> T checkNotNull(T reference) {
        return IMPL.checkNotNull(reference);
    }

    /**
     * 检查指定对象是否为空，如果为空，则抛出包含指定错误信息的空指针异常。
     *
     * @param <T>          待检查对象类型
     * @param reference    待检查对象
     * @param errorMessage 错误信息
     * @return 检查通过的对象
     */
    public static <T> T checkNotNull(T reference, Object errorMessage) {
        return IMPL.checkNotNull(reference, errorMessage);
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
    public static <T> T checkNotNull(T reference, String errorMessageTemplate, Object... errorMessageParams) {
        return IMPL.checkNotNull(reference, errorMessageTemplate, errorMessageParams);
    }

    /**
     * 检查指定字符串前后去空格后是否为空，如果为空或者前后去空格后为空字符串，则抛出包含指定错误信息的空指针异常。
     *
     * @param value                待检查字符串
     * @param errorMessageTemplate 错误信息模板
     * @param errorMessageParams   错误信息模板参数
     * @return 前后去空格的待检查对象
     */
    public static String checkNotTrimEmpty(String value, String errorMessageTemplate, Object... errorMessageParams) {
        return IMPL.checkNotTrimEmpty(value, errorMessageTemplate, errorMessageParams);
    }

    /**
     * 检查指定字符串是否为null或者前后去空格后为空字符串，如果是返回指定的默认值，不是返回前后去空格的字符串。
     *
     * @param value    要检查的字符串
     * @param defValue 为空时返回的默认字符串
     * @return 前后去空格的字符串 / 默认字符串
     */
    public static String checkTrimEmpty(String value, String defValue) {
        return IMPL.checkTrimEmpty(value, defValue);
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的参数无效异常。
     *
     * @param expression   判断表达式
     * @param errorMessage 错误信息
     */
    public static void checkArgument(boolean expression, String errorMessage) {
        IMPL.checkArgument(expression, errorMessage);
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的参数无效异常。
     *
     * @param expression           判断表达式
     * @param errorMessageTemplate 错误信息模板
     * @param errorMessageParams   错误信息模板参数
     */
    public static void checkArgument(boolean expression, String errorMessageTemplate, Object... errorMessageParams) {
        IMPL.checkArgument(expression, errorMessageTemplate, errorMessageParams);
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的状态无效异常。
     *
     * @param expression   判断表达式
     * @param errorMessage 错误信息
     */
    public static void checkState(boolean expression, String errorMessage) {
        IMPL.checkState(expression, errorMessage);
    }

    /**
     * 如果指定的表达式结果为真，则抛出包含指定错误信息的状态无效异常。
     *
     * @param expression           判断表达式
     * @param errorMessageTemplate 错误信息模板
     * @param errorMessageParams   错误信息模板参数
     */
    public static void checkState(boolean expression, String errorMessageTemplate, Object... errorMessageParams) {
        IMPL.checkState(expression, errorMessageTemplate, errorMessageParams);
    }

    /**
     * 检查元素索引，必须处于0到size之间，否则抛出包含指定错误信息的索引越界异常。
     *
     * @param index  索引
     * @param length 数据长度
     * @param desc   错误信息主体
     */
    public static void checkIndex(int index, int length, String desc) {
        IMPL.checkIndex(index, length, desc);
    }

    /**
     * 检查元素索引，必须处于0到size之间，否则抛出默认的索引越界异常信息。
     *
     * @param index  索引
     * @param length 数据长度
     */
    public static void checkIndex(int index, int length) {
        IMPL.checkIndex(index, length);
    }

}
