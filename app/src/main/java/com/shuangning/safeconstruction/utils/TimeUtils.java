package com.shuangning.safeconstruction.utils;

import android.annotation.SuppressLint;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class TimeUtils {

    public static final String MM_dd_HH_mm = "MM-dd HH:mm";

    public static final String yyyy_MM_dd = "yyyy-MM-dd";

    public static final String yyyy_MM_dd_HH_mm = "yyyy-MM-dd HH:mm";

    public static final String yyyy_MM_dd_HH_mm_ss     = "yyyy-MM-dd HH:mm:ss";
    public static final String yyyy_MM_dd_HH_mm_ss_SSS = "yyyy-MM-dd HH:mm:ss:SSS";

    public static final String hh_mm_ss = "hh:mm:ss";

    public static final String HH_mm_ss = "HH:mm:ss";

    public static final String weekDay = "EEEE";

    private static final String MM_dd       = "MM-dd";
    private static final String yyyy年MM月dd日 = "yyyy年MM月dd日";
    private static String str;

    /**
     * @param date   要转换的时间
     * @param format 要转换的格式
     * @return 返回转换后的时间, 如果转换失败返回空字符串.
     * @Description: 根据指定的字格式将时间转换为字符串
     */
    @SuppressLint("SimpleDateFormat")
    public static String parseTime(Date date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseTime(Date date) {
        try {
            return new SimpleDateFormat(yyyy年MM月dd日).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseTime(long time, String format) {
        try {
            return new SimpleDateFormat(format).format(time);
        } catch (Exception e) {
            return "";
        }
    }

    public static String parseStringTime(String time, String format) {
        try {
            return new SimpleDateFormat(format).format(String2Date(time));
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @param date
     * @param format
     * @return String
     * @Title: getStringDate
     * @Description: 将长时间格式字符串转换为时间
     */
    @SuppressLint("SimpleDateFormat")
    public static String getStringDate(Long date, String format) {
        try {
            return new SimpleDateFormat(format).format(date);
        } catch (Exception e) {
            return "";
        }
    }

    /**
     * @return 时间戳
     * @Description: 获取当前系统的时间戳, 单位为毫秒.
     */
    public static long getCurrentTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * @return String 返回时间字符串，格式为HH:mm:ss
     * @Title: getCurrentTime
     * @Description: 获取当前时间
     */
    public static String getCurrentTime() {
        return parseTime(new Date(getCurrentTimeMillis()), yyyy_MM_dd_HH_mm_ss);
    }

    public static String getCurrentDate3() {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * @param num
     * @return
     * @Description: 将日期字符串转成日期对象 如“2014-12-13”转成正常的日期
     */
    public static Date String2Date(String num) {
        DateFormat df = DateFormat.getDateInstance();
        df = new SimpleDateFormat(yyyy_MM_dd_HH_mm);
        try {
            Date date = df.parse(num);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }

    public static String String2Date(String num, String format) {
        DateFormat df = DateFormat.getDateInstance();
        df = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        try {
            Date date = df.parse(num);
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
            String da = sdf.format(date);
            return da;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String String2Date2(String num) {
        DateFormat df = DateFormat.getDateInstance();
        df = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        try {
            Date date = df.parse(num);
            SimpleDateFormat sdf = new SimpleDateFormat(yyyy年MM月dd日);
            String da = sdf.format(date);
            return da;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }


    /**
     * @param num
     * @return
     * @Description: 将日期字符串转成日期对象 如“2014-12-13”转成正常的日期
     */
    public static Date String2Date3(String num) {
        DateFormat df = DateFormat.getDateInstance();
        df = new SimpleDateFormat(yyyy_MM_dd);
        try {
            Date date = df.parse(num);
            return date;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return new Date();
    }


    /**
     * 获取两个日期的时间差
     */
    public static long getTimeInterval(String data, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        long interval = 0;
        try {
            Date currentTime = dateFormat.parse(dateFormat.format(new Date()));// 获取现在的时间
            Date beginTime = dateFormat.parse(data);
            interval = beginTime.getTime() - currentTime.getTime();// 时间差 单位毫秒
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return interval;
    }

    /* 将字符串转为时间戳 */
    public static String getDateToString(String time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        Date d;
        try {

            d = sdf.parse(time);
            long l = d.getTime();
            re_time = String.valueOf(l);
//            re_time = str.substring(0, 13);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }


    /**
     * 将时间戳转为字符串
     *
     * @param time
     * @return
     */
    public static String getStringToDate(String time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss);
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param time
     * @return
     */
    public static String getStringToDate2(long time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(yyyy_MM_dd_HH_mm_ss_SSS);
        // 例如：cc_time=1291778220
//        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(time)).substring(0, 19);
        return re_StrTime;
    }

    public static String getStringToDate4(long time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat(hh_mm_ss);
        // 例如：cc_time=1291778220
//        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(time)).substring(0, 19);
        return re_StrTime;
    }

    /**
     * 将时间戳转为字符串
     *
     * @param time
     * @return
     */
    public static String getStringToDate3(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        // 例如：cc_time=1291778220
//        long lcc_time = Long.valueOf(time);
        return sdf.format(new Date(time));
    }

    /**
     * 获取两个字符串时间差
     */
    public static String getEndTime(String date1, String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(date1);
            Date d2 = df.parse(date2);
            long diff = d1.getTime() - d2.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long second = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

            System.out.println("" + days + "天" + hours + "小时" + minutes + "分" + second);
            if (hours < 10) {
                return "" + days + "天 " + "0" + hours + ":" + minutes + ":" + second;
            }
            return "" + days + "天 " + hours + ":" + minutes + ":" + second;
        } catch (Exception e) {
            return "";
        }
    }

    public static long getDay(String date2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
            Date d1 = df.parse(getCurrentTime());
            Date d2 = String2Date3(date2);
            long diff = d2.getTime() - d1.getTime();//这样得到的差值是微秒级别
            long days = diff / (1000 * 60 * 60 * 24);
            long hours = (diff - days * (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60)) / (1000 * 60);
            long second = (diff - days * (1000 * 60 * 60 * 24) - hours * (1000 * 60 * 60) - minutes * (1000 * 60)) / 1000;

            System.out.println("" + days + "天" + hours + "小时" + minutes + "分" + second);
            return days;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM月dd日 HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }

    /**
     * 获取当前日期
     *
     * @return
     */
    public static String getCurrentDate2() {
        SimpleDateFormat formatter = new SimpleDateFormat("MM-dd HH:mm");
        Date curDate = new Date(System.currentTimeMillis());//获取当前时间
        String str = formatter.format(curDate);
        return str;
    }


    /**
     * 获取n天前的日期
     */
    public static String getNDate(int day) {
        final Calendar c = Calendar.getInstance();
        int mCurrentDay = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, mCurrentDay - day + 1);
        int mCurrentMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        return mCurrentMonth + "-" + mDay;
    }

    /**
     * 获取n天前的日期
     */
    public static String getNDate2(int day) {
        final Calendar c = Calendar.getInstance();
        int mCurrentDay = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.DAY_OF_MONTH, mCurrentDay - day + 1);
        int mCurrentMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        return mCurrentMonth + "月" + mDay + "日";
    }

    public static String getNDate22(int day) {
        final Calendar c = Calendar.getInstance();
        int mCurrentDay = c.get(Calendar.DAY_OF_MONTH);
        c.set(Calendar.YEAR, c.get(Calendar.YEAR));
        c.set(Calendar.DAY_OF_MONTH, mCurrentDay - day + 1);
        int year = c.get(Calendar.YEAR);
        int mCurrentMonth = c.get(Calendar.MONTH) + 1;
        int mDay = c.get(Calendar.DAY_OF_MONTH);
        return year + "-" + mCurrentMonth + "-" + mDay;
    }


    public static long getTimeInMills(String num) {
        return String2Date(num).getTime();
    }

    /**
     * 判断日期
     */
    public static String formatDateTime(String time) {
        Date date = String2Date(time);
        Calendar current = Calendar.getInstance();

        Calendar today = Calendar.getInstance();    //今天

        today.set(Calendar.YEAR, current.get(Calendar.YEAR));
        today.set(Calendar.MONTH, current.get(Calendar.MONTH));
        today.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH));
        //  Calendar.HOUR——12小时制的小时数 Calendar.HOUR_OF_DAY——24小时制的小时数
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);

        Calendar yesterday = Calendar.getInstance();    //昨天

        yesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        yesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        yesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 1);
        yesterday.set(Calendar.HOUR_OF_DAY, 0);
        yesterday.set(Calendar.MINUTE, 0);
        yesterday.set(Calendar.SECOND, 0);

        Calendar beforeYesterday = Calendar.getInstance();    //前天

        beforeYesterday.set(Calendar.YEAR, current.get(Calendar.YEAR));
        beforeYesterday.set(Calendar.MONTH, current.get(Calendar.MONTH));
        beforeYesterday.set(Calendar.DAY_OF_MONTH, current.get(Calendar.DAY_OF_MONTH) - 2);
        beforeYesterday.set(Calendar.HOUR_OF_DAY, 0);
        beforeYesterday.set(Calendar.MINUTE, 0);
        beforeYesterday.set(Calendar.SECOND, 0);

        current.setTime(date);

        if (current.after(today)) {
            return "今天 ";
//            return "今天 " + time.split(" ")[1];
        } else if (current.before(today) && current.after(yesterday)) {
            return "昨天 ";
//            return "昨天 " + time.split(" ")[1];
        } else if (current.before(yesterday) && current.after(beforeYesterday)) {
            return "前天";
//            return "前天" + time.split(" ")[1];
        } else {
//            int index = time.indexOf("-") + 1;
//            return time.substring(index, time.length());
            return parseStringTime(time, "MM月dd日");
        }
    }

    /**
     * Function:
     *
     * @param src
     * @return 去掉数值末尾的0
     * @author ys  DateTime 2015年6月17日 上午9:45:27
     */
    public static String floatNumberFormat(String src) {
        if (src.indexOf(".") > 0) {
            src = src.replaceAll("0+?$", "");//去掉多余的0
            src = src.replaceAll("[.]$", "");//如最后一位是.则去掉
        }
        return src;
    }

    /**
     * 功能描述：返回小
     *
     * @param date 日期
     * @return 返回小时
     */
    public static int getHour(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.HOUR_OF_DAY);
    }

    /**
     * 功能描述：返回分
     *
     * @param date 日期
     * @return 返回分钟
     */
    public static int getMinute(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.MINUTE);
    }

    public static String calendar2String(Calendar calendar) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SSS");
        String dateStr = sdf.format(calendar.getTime());
        return dateStr;
    }


    /**
     * 功能描述：返回日期年份
     *
     * @param date 日期
     * @return 返回年份
     */
    public static int getDateYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar.get(Calendar.YEAR);
    }

    //根据日期取得星期几
    public static String getWeek(Date date) {
        String[] weeks = {"星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
        if (week_index < 0) {
            week_index = 0;
        }
        return weeks[week_index];
    }


    /**
     * 功能描述：返回日期年份
     *
     * @param
     * @return 返回年份
     */
    public static String getYear() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);//获取年份
        return year + "";
    }

    /**
     * 功能描述：返回日期年份
     *
     * @param
     * @return 返回年份
     */
    public static String getWeek() {
        Calendar calendar = Calendar.getInstance();
        String week = "周";
        switch (calendar.get(Calendar.DAY_OF_WEEK)) {
            case 1:
                week = week + "日";
                break;
            case 2:
                week = week + "一";
                break;
            case 3:
                week = week + "二";
                break;
            case 4:
                week = week + "三";
                break;
            case 5:
                week = week + "四";
                break;
            case 6:
                week = week + "五";
                break;
            case 7:
                week = week + "六";
                break;
        }
        return week;
    }

    /**
     * 获取过去或者未来 任意天内的日期数组
     *
     * @param intervals intervals天内
     * @return 日期数组
     */
    public static ArrayList<String> getFetureDays(int intervals) {
        ArrayList<String> fetureDaysList = new ArrayList<>();
        for (int i = 0; i < intervals; i++) {
            fetureDaysList.add(getFetureDate(i));
        }
        return fetureDaysList;
    }

    /**
     * 获取未来 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getFetureDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) + past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }

    /**
     * 获取过去 第 past 天的日期
     *
     * @param past
     * @return
     */
    public static String getPassDate(int past) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_YEAR, calendar.get(Calendar.DAY_OF_YEAR) - past);
        Date today = calendar.getTime();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        String result = format.format(today);
        return result;
    }


    public static String getChatTimeStr(long timeStamp) {
        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        String timeStr = timeStamp + "";
        if (timeStr.length() == 10) {
            timeStamp = timeStamp * 1000;
        }
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
            return timeFormatStr(inputTime, sdf.format(currenTimeZone));
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("h:mm");
            return "昨天" + " " + timeFormatStr(inputTime, sdf.format(currenTimeZone));
        } else {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            calendar.set(Calendar.MONTH, Calendar.JANUARY);
            if (calendar.before(inputTime)) {
                SimpleDateFormat sdf = new SimpleDateFormat("MyPilesActivity" + "月" + "d" + "日");
                String temp1 = sdf.format(currenTimeZone);
                SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm");
                String temp2 = timeFormatStr(inputTime, sdf1.format(currenTimeZone));
                return temp1 + temp2;
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "年" + "MyPilesActivity" + "月" + "d" + "日");
                String temp1 = sdf.format(currenTimeZone);
                SimpleDateFormat sdf1 = new SimpleDateFormat("h:mm");
                String temp2 = timeFormatStr(inputTime, sdf1.format(currenTimeZone));
                return temp1 + temp2;
            }

        }

    }

    public static String multiSendTimeToStr(long timeStamp) {

        if (timeStamp == 0) return "";
        Calendar inputTime = Calendar.getInstance();
        String timeStr = timeStamp + "";
        if (timeStr.length() == 10) {
            timeStamp = timeStamp * 1000;
        }
        inputTime.setTimeInMillis(timeStamp);
        Date currenTimeZone = inputTime.getTime();
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return sdf.format(currenTimeZone);
        }
        calendar.add(Calendar.DAY_OF_MONTH, -1);
        if (calendar.before(inputTime)) {
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");
            return "昨天";
        } else {
            calendar.add(Calendar.DAY_OF_MONTH, -5);
            if (calendar.before(inputTime)) {
                return getWeekDayStr(inputTime.get(Calendar.DAY_OF_WEEK));
            } else {
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, Calendar.JANUARY);
                if (calendar.before(inputTime)) {
                    SimpleDateFormat sdf = new SimpleDateFormat("MyPilesActivity" + "/" + "d" + " ");
                    String temp1 = sdf.format(currenTimeZone);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    String temp2 = sdf1.format(currenTimeZone);
                    return temp1 + temp2;
                } else {
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy" + "/" + "MyPilesActivity" + "/" + "d" + " ");
                    String temp1 = sdf.format(currenTimeZone);
                    SimpleDateFormat sdf1 = new SimpleDateFormat("HH:mm");
                    String temp2 = sdf1.format(currenTimeZone);
                    return temp1 + temp2;
                }
            }
        }
    }

    /**
     * 时间转化为星期
     *
     * @param indexOfWeek 星期的第几天
     */
    public static String getWeekDayStr(int indexOfWeek) {
        String weekDayStr = "";
        switch (indexOfWeek) {
            case 1:
                weekDayStr = "星期日";
                break;
            case 2:
                weekDayStr = "星期一";
                break;
            case 3:
                weekDayStr = "星期二";
                break;
            case 4:
                weekDayStr = "星期三";
                break;
            case 5:
                weekDayStr = "星期四";
                break;
            case 6:
                weekDayStr = "星期五";
                break;
            case 7:
                weekDayStr = "星期六";
                break;
        }
        return weekDayStr;
    }

    /**
     * 24小时制转化成12小时制
     *
     * @param strDay
     */
    public static String timeFormatStr(Calendar calendar, String strDay) {
        String tempStr = "";
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        if (hour > 11) {
            tempStr = "下午" + " " + strDay;
        } else {
            tempStr = "上午" + " " + strDay;
        }
        return tempStr;
    }
}
