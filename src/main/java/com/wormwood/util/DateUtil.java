package com.wormwood.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Objects;


public class DateUtil {
    private static final Logger logger = LoggerFactory.getLogger(DateUtil.class);
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd";
    public static final String FORMAT_YYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得时间戳
     *
     * @return Long
     */
    public static Long getTimeStamp() {
        return getCurDate().getTime();
    }

    public static Date getCurDate() {
        return new Date();
    }

    public static Date getDate(Long time) {
        return Objects.isNull(time) ? null : new Date(time);
    }


    public static Long getMilliseconds(Date date) {
        return Objects.isNull(date) ? null : date.getTime();
    }


    public static Date parseDate(String date) {
        Date newDate = null;
        try {
            newDate = new SimpleDateFormat(DEFAULT_FORMAT).parse(date);
        } catch (ParseException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
        return newDate;
    }

    public static int compareTo(final Date date1, final Date date2) {
        return date1.compareTo(date2);
    }


    public static Date getStartTimeOfTheDay(int day, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static Date getEndTimeOfTheDay(int day, Date date) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DAY_OF_MONTH, day);
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.MINUTE, 59);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MILLISECOND, 0);
        return c.getTime();
    }

    public static String getDateString(Date date) {
        return new SimpleDateFormat(FORMAT_YYMMDDHHMMSS).format(date);
    }
}
