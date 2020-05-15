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

import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 8:02
 * @Description 日期处理工具
 * <pre>
 * 补充说明
 * </pre>
 **/
public interface IDateUtils {

    /**
     * 默认的实现类名称
     */
    String DEF_IMPL_CLASS_NAME = "com.wee0.box.util.impl.SimpleDateUtils";

    /**
     * 只有日期的格式，如：2019-07-05
     */
    String PATTERN_DATE = "yyyy-MM-dd";

    /**
     * 只有日期的格式，如：20190705
     */
    String PATTERN_DATE8 = "yyyyMMdd";

    /**
     * 只有日期的格式，如：20190705
     */
    String PATTERN_DATE6 = "yyyyMM";

    /**
     * 包含时间毫秒的格式，如：22:47:19,660
     */
    String PATTERN_TIME_3S = "HH:mm:ss,SSS";

    /**
     * 不包含时间毫秒的格式，如：22:47:19
     */
    String PATTERN_TIME8 = "HH:mm:ss";

    /**
     * 不包含时间毫秒的格式，如：224719
     */
    String PATTERN_TIME6 = "HHmmss";

    /**
     * 包含日期时间的格式，如：2019-07-05 22:47:19
     */
    String PATTERN_DATE_TIME = "yyyy-MM-dd HH:mm:ss";

    /**
     * 包含日期时间的14位字符格式,通常用于数据库保存
     */
    String PATTERN_CHAR_14 = "yyyyMMddHHmmss";

    /**
     * 包含日期时间毫秒的格式，如：2019-07-05 22:47:19,660
     */
    String PATTERN_DTS = "yyyy-MM-dd HH:mm:ss,SSS";

    /**
     * 包含日期时间星期的格式，如：2019-07-05 22:47:19 星期三
     */
    String PATTERN_DTE = "yyyy-MM-dd HH:mm:ss E";

    /**
     * 包含日期时间时区的格式，如：2019-07-05 22:47:19 +0800
     */
    String PATTERN_DTZ = "yyyy-MM-dd HH:mm:ss Z";

    /**
     * 包含月份时间的格式，如：Jul 05 22:47:19
     */
    String PATTERN_MDT = "MMM dd HH:mm:ss";

    /**
     * GMT时间格式，如：Fri, 05 Jul 2019 22:47:19 GMT
     */
    String PATTERN_GMT = "EEE, dd MMM yyyy HH:mm:ss 'GMT'";

    /**
     * ISO8601时间格式，如：2019-07-05T23:07:19Z
     */
    String PATTERN_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    /**
     * 自定义格式化
     *
     * @param time    时间戳
     * @param pattern 格式化模式
     * @return 格式化后的字符串
     */
    String format(long time, String pattern);

    /**
     * 自定义格式化
     *
     * @param date    日期
     * @param pattern 格式化模式
     * @return 格式化后的字符串
     */
    String format(Date date, String pattern);

    /**
     * 解析日期
     *
     * @param source  源字符串
     * @param pattern 格式化模式
     * @return 日期对象
     */
    Date parse(String source, String pattern);

    /**
     * @return 当前年
     */
    int getCurrentYear();

    /**
     * @return 当前月
     */
    int getCurrentMonth();

    /**
     * @return 当前日
     */
    int getCurrentDay();

    /**
     * @return 当前日期，以默认格式"yyyy-MM-dd"显示。
     */
    String getCurrentDate();

    /**
     * @return 当前日期时间，以默认格式"yyyy:MM:dd HH:mm:ss"显示。
     */
    String getCurrentDateTime();

    /**
     * @return 当前日期时间，以GMT格式"EEE, dd MMM yyyy HH:mm:ss 'GMT'"显示。
     */
    String getCurrentDateTimeGMT();

    /**
     * @return 14位年月日时分秒，中间无空格的当前日期时间。
     */
    String getCurrentDateTime14();

    /**
     * @return 8位年月日格式的当前日期
     */
    String getCurrentDate8();

    /**
     * @return 6位年月格式的当前日期
     */
    String getCurrentDate6();

}
