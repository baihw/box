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

import com.wee0.box.util.IDateUtils;

import java.io.ObjectStreamException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:20
 * @Description 一个支持java7的简单日期处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleDateUtilsJava7 implements IDateUtils {

    @Override
    public String format(Date date, String pattern) {
        return new SimpleDateFormat(pattern).format(date);
    }

    @Override
    public String format(long time, String pattern) {
        return format(new Date(time), pattern);
    }

    @Override
    public Date parse(String source, String pattern) {
        try {
            return new SimpleDateFormat(pattern).parse(source);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * @return 当前年
     */
    @Override
    public int getCurrentYear() {
        return Calendar.getInstance().get(Calendar.YEAR);
    }

    /**
     * @return 当前月
     */
    @Override
    public int getCurrentMonth() {
        return Calendar.getInstance().get(Calendar.MONTH) + 1;
    }

    /**
     * @return 当前日
     */
    @Override
    public int getCurrentDay() {
        return Calendar.getInstance().get(Calendar.DATE);
    }

    /**
     * @return 当前日期，以默认格式"yyyy-MM-dd"显示。
     */
    @Override
    public String getCurrentDate() {
        return new SimpleDateFormat(PATTERN_DATE).format(new Date());
    }

    /**
     * @return 当前日期时间，以默认格式"yyyy-MM-dd HH:mm:ss"显示。
     */
    @Override
    public String getCurrentDateTime() {
        return new SimpleDateFormat(PATTERN_DATE_TIME).format(new Date());
    }

    @Override
    public String getCurrentDateTimeGMT() {
        return new SimpleDateFormat(PATTERN_GMT).format(new Date());
    }

    /**
     * @return 14位年月日时分秒，中间无空格的当前日期时间。
     */
    @Override
    public String getCurrentDateTime14() {
        return new SimpleDateFormat(PATTERN_CHAR_14).format(new Date());
    }

    /**
     * @return 8位年月日格式的当前日期
     */
    @Override
    public String getCurrentDate8() {
        return new SimpleDateFormat(PATTERN_DATE8).format(new Date());
    }

    /**
     * @return 6位年月格式的当前日期
     */
    @Override
    public String getCurrentDate6() {
        return new SimpleDateFormat(PATTERN_DATE6).format(new Date());
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleDateUtilsJava7() {
        if (null != SimpleDateUtilsJava7Holder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleDateUtilsJava7Holder {
        private static final SimpleDateUtilsJava7 _INSTANCE = new SimpleDateUtilsJava7();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleDateUtilsJava7Holder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleDateUtilsJava7 me() {
        return SimpleDateUtilsJava7Holder._INSTANCE;
    }

}
