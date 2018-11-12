package com.one.pig.core.base;

import com.anniu.ordercall.core.utils.DateTimeUtils;

import java.util.Date;

/**
 * 封装java.util.Date
 *
 * @author xiaoleilu
 */
public class DateTime extends Date {
    private static final long serialVersionUID = -5395712593979185936L;

    /**
     * 当前时间
     */
    public DateTime() {
        super();
    }

    /**
     * 给定日期的构造
     *
     * @param date 日期
     */
    public DateTime(Date date) {
        this(date.getTime());
    }

    /**
     * 给定日期毫秒数的构造
     *
     * @param timeMillis 日期毫秒数
     */
    public DateTime(long timeMillis) {
        super(timeMillis);
    }

    /**
     * 转换JDK date为 DateTime
     *
     * @param date JDK Date
     * @return DateTime
     */
    public static DateTime parse(Date date) {
        return new DateTime(date);
    }

    @Override
    public String toString() {
        return DateTimeUtils.formatDateTime(this);
    }

    public String toString(String format) {
        return DateTimeUtils.format(this, format);
    }

    /**
     * @return 输出精确到毫秒的标准日期形式
     */
    public String toMsStr() {
        return DateTimeUtils.format(this, DateTimeUtils.NORM_DATETIME_MS_PATTERN);
    }

    /**
     * @return java.util.Date
     */
    public Date toDate() {
        return new Date(this.getTime());
    }
}
