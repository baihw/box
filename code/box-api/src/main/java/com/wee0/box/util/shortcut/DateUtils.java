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
import com.wee0.box.util.IDateUtils;

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:11
 * @Description 日期处理工具快捷入口
 * <pre>
 * 补充说明
 * </pre>
 **/
public class DateUtils {

    // 实现类实例
    private static final IDateUtils IMPL = BoxConfig.impl().getInterfaceImpl(IDateUtils.class);

    /**
     * 自定义格式化
     *
     * @param time    时间戳
     * @param pattern 格式化模式
     * @return 格式化后的字符串
     */
    public static String format(long time, String pattern) {
        return IMPL.format(time, pattern);
    }

    /**
     * 自定义格式化
     *
     * @param date    日期
     * @param pattern 格式化模式
     * @return 格式化后的字符串
     */
    public static String format(Date date, String pattern) {
        return IMPL.format(date, pattern);
    }

    /**
     * 解析日期
     *
     * @param source  源字符串
     * @param pattern 格式化模式
     * @return 日期对象
     */
    public static Date parse(String source, String pattern) {
        return IMPL.parse(source, pattern);
    }

    /**
     * @return 当前年
     */
    public static int getCurrentYear() {
        return IMPL.getCurrentYear();
    }

    /**
     * @return 当前月
     */
    public static int getCurrentMonth() {
        return IMPL.getCurrentMonth();
    }

    /**
     * @return 当前日
     */
    public static int getCurrentDay() {
        return IMPL.getCurrentDay();
    }

    /**
     * @return 当前日期，以默认格式"yyyy-MM-dd"显示。
     */
    public static String getCurrentDate() {
        return IMPL.getCurrentDate();
    }

    /**
     * @return 当前日期时间，以默认格式"yyyy:MM:dd HH:mm:ss"显示。
     */
    public static String getCurrentDateTime() {
        return IMPL.getCurrentDateTime();
    }

    /**
     * @return 当前日期时间，以GMT格式"EEE, dd MMM yyyy HH:mm:ss 'GMT'"显示。
     */
    public static String getCurrentDateTimeGMT() {
        return IMPL.getCurrentDateTimeGMT();
    }


    /**
     * @return 14位年月日时分秒，中间无空格的当前日期时间。
     */
    public static String getCurrentDateTime14() {
        return IMPL.getCurrentDateTime14();
    }

    /**
     * @return 8位年月日格式的当前日期
     */
    public static String getCurrentDate8() {
        return IMPL.getCurrentDate8();
    }

    /**
     * @return 6位年月格式的当前日期
     */
    public static String getCurrentDate6() {
        return IMPL.getCurrentDate6();
    }

}
