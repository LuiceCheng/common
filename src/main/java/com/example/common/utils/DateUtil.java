package com.example.common.utils;

import com.example.common.config.execptions.BussinessException;
import org.apache.commons.lang3.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.*;

/**
 * 日期工具类
 */
public class DateUtil {

    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYYMMDDHH = "yyyy-MM-dd HH";
    public static final String YYYYMMDDHHMM = "yyyy-MM-dd HH:mm";
    public static final String YYYYMMDDHHMMSS = "yyyy-MM-dd HH:mm:ss";

    public static final String YYYYMM = "yyyyMM";
    public static final String YYYYMMDD = "yyyyMMdd";

    private static final long DAY_MILLISECONDS = 86400000L;
    private static final long HOUR_MILLISECONDS = 3600000L;
    private static final long MINUTE_MILLISECONDS = 60000L;
    private static final long SECOND_MILLISECONDS = 1000L;

    /**
     * 日期格式化对象
     */
    public final static SimpleDateFormat SIMPLE_DATE_FORMAT = new SimpleDateFormat();

    /**
     * 日历对象
     */
    public final static Calendar CALENDAR = Calendar.getInstance();

    /**
     * 常用日期格式
     */
    public static final Map<String, String> DATE_FORMATES = new HashMap<>();
    static {
        DATE_FORMATES.put("^\\d{4}$", "yyyy");
        DATE_FORMATES.put("^\\d{4}\\-\\d{1,2}$", "yyyy-MM");
        DATE_FORMATES.put("^\\d{4}\\-\\d{1,2}\\-\\d{1,2}$", "yyyy-MM-dd");
        DATE_FORMATES.put("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}$", "yyyy-MM-dd HH");
        DATE_FORMATES.put("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm");
        DATE_FORMATES.put("^\\d{4}\\-\\d{1,2}\\-\\d{1,2} \\d{1,2}:\\d{1,2}:\\d{1,2}$", "yyyy-MM-dd HH:mm:ss");
        DATE_FORMATES.put("^\\d{4}\\/\\d{1,2}\\/\\d{1,2}$", "yyyy/MM/dd");
    }


    /**
     * 链式解析或格式化日期对象
     * @param pattern
     * @return
     */
    public static SimpleDateFormat applyPattern(String pattern){
        SIMPLE_DATE_FORMAT.applyPattern(pattern);
        return SIMPLE_DATE_FORMAT;
    }

    /**
     * 某一天
     * @param date
     * @return
     */
    public static Date dayOfMonth(int i, Date date) {
        Calendar monthStart = Calendar.getInstance();
        monthStart.setTime(date);
        monthStart.set(Calendar.DAY_OF_MONTH, i);
        monthStart.set(Calendar.HOUR_OF_DAY, 0);
        monthStart.set(Calendar.MINUTE, 0);
        monthStart.set(Calendar.SECOND, 0);
        monthStart.set(Calendar.MILLISECOND, 0);
        return monthStart.getTime();
    }

    /**
     * 当月一共多少天
     * @return
     */
    public static int daysOfMonth(Date date) {
        date = monthEnd(date);
        return Integer.parseInt(new SimpleDateFormat("dd").format(date));
    }

    /**
     * 获取天
     * @return
     */
    public static int getDay(Date date) {
        return Integer.parseInt(new SimpleDateFormat("dd").format(date));
    }

    /**
     * 日期格式化
     * @param date
     * @param pattern
     * @return
     */
    public static String format(Date date, String pattern) {
        if (null == date) {
            return "";
        }
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        sdf.setTimeZone(TimeZone.getTimeZone("GMT+08"));
        return sdf.format(date);
    }

    /**
     * 格式化日期转Date
     * @param formatDate
     * @param pattern
     * @return
     */
    public static Date parse(String formatDate, String pattern) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat(pattern);
            return sdf.parse(formatDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date parseDate(String date) {
        if (StringUtils.isBlank(date)){
            return null;
        }
        String formatString = null;
        for (String format : DATE_FORMATES.keySet()) {
            if (date.trim().matches(format)) {
                formatString = DATE_FORMATES.get(format);
            }
        }
        if (formatString != null) {
            return DateUtil.parse(date, formatString);
        } else {
            if (date.trim().matches("^\\d{13,}$")) {
                return new Date(Long.parseLong(date));
            } else if (date.trim().matches("^\\d{1,}$")){
                return org.apache.poi.ss.usermodel.DateUtil.getJavaDate(Double.parseDouble(date));
            } else {
                throw new BussinessException("日期格式错误:"+date);
            }
        }
    }

    /**
     * 得到格式化时间 yyyy-MM-dd HH:mm:ss
     * @param date
     * @return
     */
    public static String getFormat(Date date) {
        return format(date, YYYYMMDDHHMMSS);
    }

    /**
     * 得到格式化时间 yyyy-MM-dd HH:mm
     * @param date
     * @return
     */
    public static String getMinuteFormat(Date date) {
        return format(date, YYYYMMDDHHMM);
    }

    /**
     * 得到格式化时间 yyyy-MM-dd
     * @param date
     * @return
     */
    public static String getDayFormat(Date date) {
        return format(date, YYYY_MM_DD);
    }

    /**
     * 得到格式化时间 yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getFormat() {
        return getFormat(new Date());
    }

    /**
     * 得到格式化时间 yyyy-MM-dd
     * @return
     */
    public static String getDayFormat() {
        return getDayFormat(new Date());
    }

    /**
     * 得到格式化时间 yyyy-MM-dd HH:mm
     * @return
     */
    public static String getMinuteFormat() {
        return getMinuteFormat(new Date());
    }

    /**
     * 得到日期 yyyy-MM-dd HH:mm:ss
     * @param formatDate
     * @return
     */
    public static Date get(String formatDate) {
        return parse(formatDate, YYYYMMDDHHMMSS);
    }

    /**
     * 得到日期 yyyy-MM-dd
     * @param formatDate
     * @return
     */
    public static Date getDay(String formatDate) {
        return parse(formatDate, YYYY_MM_DD);
    }

    /**
     * 得到日期 yyyy-MM-dd HH:mm
     * @param formatDate
     * @return
     */
    public static Date getMinute(String formatDate) {
        return parse(formatDate, YYYYMMDDHHMM);
    }

    /**
     * 得到当前日期n天后的日期，n为负数时表示当前日期前几天
     * @param date
     * @param days
     * @return
     */
    public static Date nDaysAfter(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, days);
        return calendar.getTime();
    }

    /**
     * 得到当前日期n天后的日期，n为负数时表示当前日期前几天
     * @param formatDate
     * @param pattern
     * @param days
     * @return
     */
    public static String nDaysAfter(String formatDate, String pattern, int days) {
        Date date = parse(formatDate, pattern);
        return format(nDaysAfter(date, days), pattern);
    }

    /**
     * 得到当前日期n小时后日期，n为负数时表示当前日期前几小时
     * @param date
     * @param hours
     * @return
     */
    public static Date nHoursAfter(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hours);
        return calendar.getTime();
    }

    /**
     * 得到当前日期n小时后日期，n为负数时表示当前日期前几小时
     * @param formatDate
     * @param pattern
     * @param hours
     * @return
     */
    public static String nHoursAfter(String formatDate, String pattern, int hours) {
        Date date = parse(formatDate, pattern);
        return format(nHoursAfter(date, hours), pattern);
    }

    /**
     * 得到当前日期n分钟后日期，n为负数时表示当前日期前几分钟
     * @param date
     * @param minutes
     * @return
     */
    public static Date nMinutesAfter(Date date, int minutes) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minutes);
        return calendar.getTime();
    }

    /**
     * 得到当前日期n分钟后日期，n为负数时表示当前日期前几分钟
     * @param formatDate
     * @param pattern
     * @param minutes
     * @return
     */
    public static String nMinutesAfter(String formatDate, String pattern, int minutes) {
        Date date = parse(formatDate, pattern);
        return format(nMinutesAfter(date, minutes), pattern);
    }

    /**
     * 得到当前日期n秒钟后日期，n为负数时表示当前日期前几秒钟
     * @param date
     * @param seconds
     * @return
     */
    public static Date nSecondsAfter(Date date, int seconds) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, seconds);
        return calendar.getTime();
    }

    /**
     * 得到当前日期n秒钟后日期，n为负数时表示当前日期前几秒钟
     * @param formatDate
     * @param pattern
     * @param seconds
     * @return
     */
    public static String nSecondsAfter(String formatDate, String pattern, int seconds) {
        Date date = parse(formatDate, pattern);
        return format(nSecondsAfter(date, seconds), pattern);
    }

    /**
     * 两个日期相差天数
     * @param date1
     * @param date2
     * @return
     */
    public static int nDaysBetween(Date date1, Date date2) {
        long ms = nMillisecondsBetween(date1, date2);
        int days = (int) (ms/DAY_MILLISECONDS);
        return Math.abs(days);
    }

    /**
     * 两个日期相差天数
     * @param formatDate1
     * @param formatDate2
     * @return
     */
    public static int nDaysBetween(String formatDate1, String formatDate2) {
        Date date1 = parse(formatDate1, YYYY_MM_DD);
        Date date2 = parse(formatDate2, YYYY_MM_DD);
        return nDaysBetween(date1, date2);
    }

    /**
     * 两个日期相差小时数
     * @param date1
     * @param date2
     * @return
     */
    public static int nHoursBetween(Date date1, Date date2) {
        long ms = nMillisecondsBetween(date1, date2);
        int hours = (int) (ms/HOUR_MILLISECONDS);
        return Math.abs(hours);
    }

    /**
     * 两个日期相差小时数
     * @param formatDate1
     * @param formatDate2
     * @return
     */
    public static int nHoursBetween(String formatDate1, String formatDate2) {
        Date date1 = parse(formatDate1, YYYYMMDDHH);
        Date date2 = parse(formatDate2, YYYYMMDDHH);
        return nHoursBetween(date1, date2);
    }

    /**
     * 两个日期相差分钟数
     * @param date1
     * @param date2
     * @return
     */
    public static int nMinutesBetween(Date date1, Date date2) {
        long ms = nMillisecondsBetween(date1, date2);
        int minutes = (int) (ms/MINUTE_MILLISECONDS);
        return Math.abs(minutes);
    }

    /**
     * 两个日期相差分钟数
     * @param formatDate1
     * @param formatDate2
     * @return
     */
    public static int nMinutesBetween(String formatDate1, String formatDate2) {
        Date date1 = parse(formatDate1, YYYYMMDDHHMM);
        Date date2 = parse(formatDate2, YYYYMMDDHHMM);
        return nMinutesBetween(date1, date2);
    }

    /**
     * 两个日期相差秒数
     * @param date1
     * @param date2
     * @return
     */
    public static int nSecondsBetween(Date date1, Date date2) {
        long ms = nMillisecondsBetween(date1, date2);
        int seconds = (int) (ms/SECOND_MILLISECONDS);
        return Math.abs(seconds);
    }

    /**
     * 两个日期相差秒数
     * @param formatDate1
     * @param formatDate2
     * @return
     */
    public static int nSecondsBetween(String formatDate1, String formatDate2) {
        Date date1 = parse(formatDate1, YYYYMMDDHHMM);
        Date date2 = parse(formatDate2, YYYYMMDDHHMM);
        return nSecondsBetween(date1, date2);
    }

    /**
     * 两个日期相差毫秒数
     * @param date1
     * @param date2
     * @return
     */
    public static long nMillisecondsBetween(Date date1, Date date2) {
        long time1 = date1.getTime();
        long time2 = date2.getTime();
        return time1 - time2;
    }

    /**
     * 当天的开始
     * @param date
     * @return
     */
    public static Date dayStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar todayStart = Calendar.getInstance();
        todayStart.setTime(date);
        todayStart.set(Calendar.HOUR_OF_DAY, 0);
        todayStart.set(Calendar.MINUTE, 0);
        todayStart.set(Calendar.SECOND, 0);
        todayStart.set(Calendar.MILLISECOND, 0);
        return todayStart.getTime();
    }

    /**
     * 当天的结束
     * @param date
     * @return
     */
    public static Date dayEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 当月的开始
     * @param date
     * @return
     */
    public static Date monthStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar monthStart = Calendar.getInstance();
        monthStart.setTime(date);
        monthStart.set(Calendar.DAY_OF_MONTH, 1);
        monthStart.set(Calendar.HOUR_OF_DAY, 0);
        monthStart.set(Calendar.MINUTE, 0);
        monthStart.set(Calendar.SECOND, 0);
        monthStart.set(Calendar.MILLISECOND, 0);
        return monthStart.getTime();
    }

    /**
     * 当月的结束
     * @param date
     * @return
     */
    public static Date monthEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar todayEnd = Calendar.getInstance();
        todayEnd.setTime(date);
        todayEnd.set(Calendar.DAY_OF_MONTH, 0);
        todayEnd.add(Calendar.MONTH, 1);
        todayEnd.set(Calendar.HOUR_OF_DAY, 23);
        todayEnd.set(Calendar.MINUTE, 59);
        todayEnd.set(Calendar.SECOND, 59);
        todayEnd.set(Calendar.MILLISECOND, 999);
        return todayEnd.getTime();
    }

    /**
     * 根据日期获取当天星期
     * @param date
     * @return
     */
    public static String getWeekOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int week = calendar.get(Calendar.DAY_OF_WEEK);
        if (Calendar.MONDAY == week) {
            return "星期一";
        } else if (Calendar.TUESDAY == week) {
            return "星期二";
        } else if (Calendar.WEDNESDAY == week) {
            return "星期三";
        } else if (Calendar.THURSDAY == week) {
            return "星期四";
        } else if (Calendar.FRIDAY == week) {
            return "星期五";
        } else if (Calendar.SATURDAY == week) {
            return "星期六";
        } else if (Calendar.SUNDAY == week) {
            return "星期日";
        }
        return "";
    }

    /**
     * 这周的结束
     * @param date
     * @return
     */
    public static Date weekStart(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 1);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        return calendar.getTime();
    }

    /**
     * 这周的开始
     * @param date
     * @return
     */
    public static Date weekEnd(Date date) {
        if (date == null) {
            return null;
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_WEEK, 7);
        calendar.add(Calendar.DAY_OF_WEEK, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 59);
        calendar.set(Calendar.MILLISECOND, 999);
        return calendar.getTime();
    }

    /**
     * 根据日期获取当天星期
     * @param formatDate
     * @param pattern
     * @return
     */
    public static String getWeekOfDate(String formatDate, String pattern) {
        return getWeekOfDate(parse(formatDate, pattern));
    }

    /**
     * 转换秒为天,时,分
     * @param time
     * @return
     */
    public static String convertTime(int time) {
        if (time < 60){
            return "0分钟";
        }else if (time >= 60*60*24){
            return time/60/60/24 + "天" + time/60/60%24 + "时" + time/60%60  + "分钟";
        }else if (time >= 60*60){
            return time/60/60 + "时" + time/60%60  + "分钟";
        }else if (time >= 60){
            return time/60  + "分钟";
        }else {
            throw new BussinessException("转换异常:" + time);
        }
    }

    /**
     * 日期是星期几
     * @param date
     * @return
     */
    public static String convertDateToWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        int weekNum = calendar.get(Calendar.DAY_OF_WEEK);
        if (weekNum == 2){
            return "周一";
        }else if (weekNum == 3){
            return "周二";
        }else if (weekNum == 4){
            return "周三";
        }else if (weekNum == 5){
            return "周四";
        }else if (weekNum == 6){
            return "周五";
        }else if (weekNum == 7){
            return "周六";
        }else if (weekNum == 1){
            return "周日";
        }else {
            throw new BussinessException("异常");
        }
    }

    /**
     * 判断是否在指定时间之内，包含指定时间，只比较日期
     */
    public static boolean nowDayInStartAndEnd(Date startDate, Date endDate) {
        LocalDate start = LocalDateTime.ofInstant(startDate.toInstant(),
                ZoneId.systemDefault()).toLocalDate();
        LocalDate end = LocalDateTime.ofInstant(endDate.toInstant(),
                ZoneId.systemDefault()).toLocalDate();
        return start.compareTo(LocalDate.now()) <= 0 && end.plus(1, ChronoUnit.DAYS).compareTo(LocalDate.now()) >= 0;
    }

    /**
     * 按指定格式，格式化日期
     * yyyy-MM-dd yyyy-MM-dd HH:mm:ss
     *
     * @param date
     * @param pattern
     * @return
     */
    public static String formatDate(Date date, String pattern) {
        if (date == null)
            return null;
        SimpleDateFormat df = new SimpleDateFormat(pattern);
        String formatDate = df.format(date);
        return formatDate;
    }

    public static Date stringToDate(String str) {
        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat(YYYY_MM_DD);
        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            e.printStackTrace();

        }
        return date;


    }

    public static Date now(){
        return new Date();
    }
}
