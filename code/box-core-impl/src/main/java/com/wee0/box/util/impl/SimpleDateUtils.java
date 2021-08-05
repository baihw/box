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
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Locale;

/**
 * @author <a href="78026399@qq.com">白华伟</a>
 * @CreateDate 2019/8/31 9:20
 * @Description 一个简单的日期处理工具实现
 * <pre>
 * 补充说明
 * </pre>
 **/
public class SimpleDateUtils implements IDateUtils {

    /**
     * 标准的日期时间格式"yyyy-MM-dd HH:mm:ss"
     */
    private static final DateTimeFormatter _FORMAT_DATETIME = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    /**
     * GMT日期时间格式"EEE, dd MMM yyyy HH:mm:ss 'GMT'"
     */
    private static final DateTimeFormatter _FORMAT_DATETIME_GMT = DateTimeFormatter.ofPattern("EEE, dd MMM yyyy HH:mm:ss 'GMT'", Locale.US);

    /**
     * 14位日期时间格式
     */
    private static final DateTimeFormatter _FORMAT_DATETIME14 = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");

    /**
     * 8位年月日格式
     */
    private static final DateTimeFormatter _FORMAT_DATE8 = DateTimeFormatter.ofPattern("yyyyMMdd");

    /**
     * 6位年月格式
     */
    private static final DateTimeFormatter _FORMAT_DATE6 = DateTimeFormatter.ofPattern("yyyyMM");

//    @Override
//    public String format(long time, String pattern) {
//        return DateTimeFormatter.ofPattern(pattern).format(Instant.ofEpochSecond(time));
//    }

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
    public int getCurrentYear() {
        return LocalDate.now().getYear();
    }

    /**
     * 获取当前月
     *
     * @return 数据
     */
    public int getCurrentMonth() {
        return LocalDate.now().getMonthValue();
    }

    /**
     * 获取当前日
     *
     * @return 数据
     */
    public int getCurrentDay() {
        return LocalDate.now().getDayOfMonth();
    }

    /**
     * @return 当前日期，以默认格式"yyyy-MM-dd"显示。
     */
    public String getCurrentDate() {
        return LocalDate.now().toString();
    }

    /**
     * @return 当前日期时间，以默认格式"yyyy-MM-dd HH:mm:ss"显示。
     */
    public String getCurrentDateTime() {
        return _FORMAT_DATETIME.format(LocalDateTime.now());
    }

    @Override
    public String getCurrentDateTimeGMT() {
//        SimpleDateFormat _dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
//        _dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
//        return _dateFormat.format(new Date());
        return _FORMAT_DATETIME_GMT.format(LocalDateTime.now());
    }

    /**
     * @return 14位年月日时分秒，中间无空格的当前日期时间。
     */
    public String getCurrentDateTime14() {
//		ZoneId chinaZoneId = ZoneId.of( "Asia/Shanghai" ) ; // ZoneId.systemDefault() ;
//		LocalDateTime localDateTime = LocalDateTime.ofInstant( Instant.now(), chinaZoneId );
        return _FORMAT_DATETIME14.format(LocalDateTime.now());
    }

    /**
     * @return 8位年月日格式的当前日期
     */
    public String getCurrentDate8() {
        return _FORMAT_DATE8.format(LocalDate.now());
    }

    /**
     * @return 6位年月格式的当前日期
     */
    public String getCurrentDate6() {
        return _FORMAT_DATE6.format(LocalDate.now());
    }

    /************************************************************
     ************* 单例样板代码。
     ************************************************************/
    private SimpleDateUtils() {
        if (null != SimpleDateUtilsHolder._INSTANCE) {
            // 防止使用反射API创建对象实例。
            throw new IllegalStateException("that's not allowed!");
        }
    }

    // 当前对象唯一实例持有者。
    private static final class SimpleDateUtilsHolder {
        private static final SimpleDateUtils _INSTANCE = new SimpleDateUtils();
    }

    // 防止使用反序列化操作获取多个对象实例。
    private Object readResolve() throws ObjectStreamException {
        return SimpleDateUtilsHolder._INSTANCE;
    }

    /**
     * 获取当前对象唯一实例。
     *
     * @return 当前对象唯一实例
     */
    public static SimpleDateUtils me() {
        return SimpleDateUtilsHolder._INSTANCE;
    }

//    public static void main(String[] args) {
//        IDateUtils _impl = SimpleDateUtils.me();
//        System.out.println("currentYear:" + _impl.getCurrentYear());
//        System.out.println("currentMonth:" + _impl.getCurrentMonth());
//        System.out.println("currentDay:" + _impl.getCurrentDay());
//        System.out.println("currentDate:" + _impl.getCurrentDate());
//        System.out.println("currentDateTime:" + _impl.getCurrentDateTime());
//        System.out.println("currentDateTime14:" + _impl.getCurrentDateTime14());
//        System.out.println("currentDate8:" + _impl.getCurrentDate8());
//        System.out.println("currentDate6:" + _impl.getCurrentDate6());
//        System.out.println("currentDateTimeGMT:" + _impl.getCurrentDateTimeGMT());
//
////		ZoneId.getAvailableZoneIds().forEach( a -> System.out.println(a) );
////		ZoneId.getAvailableZoneIds().forEach( System.out::println );
////		ZoneId.SHORT_IDS.forEach( ( String key, String value ) ->{
////			System.out.println( key + ":" + value );
////		});
//
////		String japanDT = LocalDateTime.ofInstant( Instant.now(), ZoneId.of( "Japan" ) ).toString() ;
////		String chinaDT = LocalDateTime.ofInstant( Instant.now(), ZoneId.of( "Asia/Shanghai" ) ).toString() ;
////		System.out.println( "japanDT:" + japanDT );
////		System.out.println( "chinaDT:" + chinaDT );
//
//    }

}
