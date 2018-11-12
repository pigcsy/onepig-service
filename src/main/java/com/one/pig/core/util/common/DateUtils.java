/**
 * Copyright (c) 2015-2016, Chill Zhuang 庄骞 (smallchill@163.com).
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.one.pig.core.util.common;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

    /**
     * 日期格式:HH:mm:ss
     */
    public static final String HH_MM_SS = "HH:mm:ss";
    /**
     * 日期格式:HH:mm
     */
    public static final String HH_MM = "HH:mm";
    /**
     * 日期格式:yyyy-MM-dd
     */
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    /**
     * 日期格式:yyyy-MM-dd
     */
    public static final String YYYY_MM = "yyyy-MM";
    /**
     * 日期格式:MM-dd
     */
    public static final String MM_DD = "MM-dd";

    /**
     * 日期格式:yy-MM-dd
     */
    public static final String YY_MM_DD = "yy-MM-dd";
    /**
     * 日期格式:yyyy-MM-dd HH:mm:ss
     */
    public static final String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH_MM = "yyyy-MM-dd HH:mm";
    public static final String YYYY_MM_DD_HH = "yyyy-MM-dd HH";
    /**
     * 日期格式:yyyy-MM-dd HH:mm:ss:ms
     */
    public static final String YYYY_MM_DD_HH_MM_SS_MS = "yyyy-MM-dd HH:mm:ss:ms";
    /**
     * 日期格式:yyyyMMddHHmmssms
     */
    public static final String YYYYMMDDHHMMSSMS = "yyyyMMddHHmmssms";
    /**
     * 日期格式:yyMMddHHmmssms
     */
    public static final String YYMMDDHHMMSSMS = "yyMMddHHmmssms";

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear() {
        return formatDate(new Date(), "yyyy");
    }

    /**
     * 获取YYYY格式
     *
     * @return
     */
    public static String getYear(Date date) {
        return formatDate(date, "yyyy");
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay() {
        return formatDate(new Date(), "yyyy-MM-dd");
    }

    /**
     * 获取YYYY-MM-DD格式
     *
     * @return
     */
    public static String getDay(Date date) {
        return formatDate(date, "yyyy-MM-dd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays() {
        return formatDate(new Date(), "yyyyMMdd");
    }

    /**
     * 获取YYYYMMDD格式
     *
     * @return
     */
    public static String getDays(Date date) {
        return formatDate(date, "yyyyMMdd");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss.SSS格式
     *
     * @return
     */
    public static String getMsTime() {
        return formatDate(new Date(), "yyyy-MM-dd HH:mm:ss.SSS");
    }

    /**
     * 获取YYYYMMDDHHmmss格式
     *
     * @return
     */
    public static String getAllTime() {
        return formatDate(new Date(), "yyyyMMddHHmmss");
    }

    /**
     * 获取YYYY-MM-DD HH:mm:ss格式
     *
     * @return
     */
    public static String getTime(Date date) {
        return formatDate(date, "yyyy-MM-dd HH:mm:ss");
    }

    public static String formatDate(Date date, String pattern) {
        String formatDate = null;
        if (StringUtils.isNotBlank(pattern)) {
            formatDate = DateFormatUtils.format(date, pattern);
        } else {
            formatDate = DateFormatUtils.format(date, "yyyy-MM-dd");
        }
        return formatDate;
    }

    /**
     * @param s
     * @param e
     * @return boolean
     * @throws
     * @Title: compareDate
     * @Description:(日期比较，如果s>=e 返回true 否则返回false)
     * @author luguosui
     */
    public static boolean compareDate(String s, String e) {
        if (parseDate(s) == null || parseDate(e) == null) {
            return false;
        }
        return parseDate(s).getTime() >= parseDate(e).getTime();
    }

    /**
     * @param date
     * @param format
     * @return
     * @author FZC
     * @version 2015年7月20日 下午4:48:54
     */
    public static Date getFormatDateByDate(Date date, String format) {
        String rs = getStrByDate(date, format);
        return setDateByStr(rs, format);
    }

    public static Date setDateByStr(String str, String format) {
        if (str == null || str.length() == 0) {
            return null;
        }
        SimpleDateFormat f = new SimpleDateFormat(format);
        Date d = null;
        try {
            d = f.parse(str);
        } catch (ParseException e) {
            return null;
        }
        return d;
    }

    /**
     * 将输入的日期转化成输入的日期格式返回
     *
     * @param date      输入的日期
     * @param formatStr 输入的日期格式
     * @return 返回转化后的值, 转化失败为null
     * @author FZC
     * @version 2015年7月10日 下午2:30:33
     */
    public static String getStrByDate(Date date, String formatStr) {
        if (date == null || date.getTime() == 0L)
            return null;
        String result = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat(formatStr);
        result = dateFormat.format(date);
        return result;
    }

    /**
     * 格式化日期
     *
     * @return
     */
    public static Date parseDate(String date) {
        return setDateByStr(date, "yyyy-MM-dd");
    }

    /**
     * 格式化日期
     **/

    public static Date parseTime(String date) {
        return setDateByStr(date, "yyyy-MM-dd HH:mm:ss");
    }

    /**
     * 格式化日期
     *
     * @return
     */

    /**
     * @param date
     * @param format
     * @return
     * @author FZC
     * @version 2015年7月20日 下午4:48:54
     */
    public static Date parse(Date date, String format) {
        String rs = getStrByDate(date, format);
        return setDateByStr(rs, format);
    }


    /**
     * 格式化日期
     *
     * @return
     */
    public static String format(Date date, String pattern) {
        return DateFormatUtils.format(date, pattern);
    }

    /**
     * 把日期转换为Timestamp
     *
     * @param date
     * @return
     */
    public static Timestamp format(Date date) {
        return new Timestamp(date.getTime());
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s) {
        return setDateByStr(s, "yyyy-MM-dd HH:mm:ss") != null;
    }

    /**
     * 校验日期是否合法
     *
     * @return
     */
    public static boolean isValidDate(String s, String pattern) {
        return setDateByStr(s, pattern) != null;
    }

    public static int getDiffYear(String startTime, String endTime) {
        DateFormat fmt = new SimpleDateFormat("yyyy-MM-dd");
        try {
            int years = (int) (((fmt.parse(endTime).getTime() - fmt.parse(
                    startTime).getTime()) / (1000 * 60 * 60 * 24)) / 365);
            return years;
        } catch (Exception e) {
            // 如果throw java.text.ParseException或者NullPointerException，就说明格式不对
            return 0;
        }
    }

    /**
     * <li>功能描述：时间相减得到天数
     *
     * @param beginDateStr
     * @param endDateStr
     * @return long
     * @author Administrator
     */
    public static long getDaySub(String beginDateStr, String endDateStr) {
        long day = 0;
        SimpleDateFormat format = new SimpleDateFormat(
                "yyyy-MM-dd");
        Date beginDate = null;
        Date endDate = null;

        try {
            beginDate = format.parse(beginDateStr);
            endDate = format.parse(endDateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        day = (endDate.getTime() - beginDate.getTime()) / (24 * 60 * 60 * 1000);
        // System.out.println("相隔的天数="+day);

        return day;
    }

    /**
     * 得到n天之后的日期
     *
     * @param days
     * @return
     */
    public static String getAfterDayDate(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdfd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdfd.format(date);

        return dateStr;
    }

    /**
     * 得到n天之后是周几
     *
     * @param days
     * @return
     */
    public static String getAfterDayWeek(String days) {
        int daysInt = Integer.parseInt(days);

        Calendar canlendar = Calendar.getInstance(); // java.util包
        canlendar.add(Calendar.DATE, daysInt); // 日期减 如果不够减会将月变动
        Date date = canlendar.getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("E");
        String dateStr = sdf.format(date);

        return dateStr;
    }

    /**
     * 日期减几天
     *
     * @param days
     * @return
     */
    public static Date mulDateByDays(int days) {
        return mulDateByDays(new Date(), days);
    }

    /**
     * 根据输入时间减int 天
     *
     * @param date
     * @param days
     * @return
     */
    public static Date mulDateByDays(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int day = calendar.get(Calendar.DATE);
        calendar.set(Calendar.DATE, day - days);
        return calendar.getTime();
    }

    /**
     * 根据输入时间减int 分钟
     *
     * @param date
     * @param minutes
     * @return
     */
    public static Date mulDateByMinute(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int minute = calendar.get(Calendar.MINUTE);
        calendar.set(Calendar.MINUTE, minute - minutes);
        return calendar.getTime();
    }

    /**
     * 在当前日期时间上增加输入的天数
     *
     * @param days 输入的天数
     * @return 返回增加后日期时间
     * @author FZC
     * @version 2015年7月10日 下午2:11:51
     */
    public static Date addDaysByNow(int days) {
        return addDaysByDate(new Date(), days);
    }

    /**
     * 在输入的日期时间上增加输入的天数
     *
     * @param date 输入的日期时间
     * @param days 输入的天数
     * @return 返回增加后日期时间，转化失败为null
     * @version 2015年7月10日 下午2:11:51
     */
    public static Date addDaysByDate(Date date, int days) {
        if (date == null)
            date = new Date();
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(getMillisByDate(date) + ((long) days) * 24 * 3600 * 1000);
        return c.getTime();
    }

    /**
     * 获取输入的日期的毫秒数
     *
     * @param date 输入的日期
     * @return 返回输入的日期的毫秒数
     * @author FZC
     * @version 2015年7月10日 下午2:14:53
     */
    public static long getMillisByDate(Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        return c.getTimeInMillis();
    }


    /**
     * 当前时间减去int 时间 格式yyyy-MM-dd HH
     *
     * @param hours
     * @return
     */
    public static String mulOneHoursAgoTime(Integer hours) {
        String oneHoursAgoTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hours);
        oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH").format(calendar.getTime());
        return oneHoursAgoTime;
    }

    /**
     * 输入日期基础上减去int 时间 格式yyyy-MM-dd HH
     *
     * @param date
     * @param hours
     * @return
     */
    public static String mulDateHoursAgoTime(Date date, Integer hours) {
        String oneHoursAgoTime = "";
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) - hours);
        oneHoursAgoTime = new SimpleDateFormat("yyyy-MM-dd HH").format(calendar.getTime());
        return oneHoursAgoTime;
    }

    // public static void main(String[] args) {
    //     System.out.println(getTime(new Date()));
    //     System.out.println(getTime(mulDateByMinute(new Date(),2)));
    //     System.out.println(getAfterDayWeek("3"));
    // }

}
