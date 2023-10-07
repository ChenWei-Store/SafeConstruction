package com.shuangning.safeconstruction.utils;

import android.text.format.Time;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * @ClassName 日期工具类
 * Created by yx on 2017-04-26.
 */

public class DateUtil {
    private static SimpleDateFormat sf = null;

    /**
     * 获取当前时间
     *
     * @return
     */
    public String getNowTime() {
        String timeString = null;
        Time time = new Time();
        time.setToNow();
        String year = thanTen(time.year);
        String month = thanTen(time.month + 1);
        String monthDay = thanTen(time.monthDay);
        String hour = thanTen(time.hour);
        String minute = thanTen(time.minute);

        timeString = year + "-" + month + "-" + monthDay + " " + hour + ":"
                + minute;
        // System.out.println("-------timeString----------" + timeString);
        return timeString;
    }

    /**
     * 十以下加零
     *
     * @param str
     * @return
     */
    public String thanTen(int str) {

        String string = null;

        if (str < 10) {
            string = "0" + str;
        } else {

            string = "" + str;

        }
        return string;
    }

    /**
     * 计算时间差
     *
     * @param starTime      开始时间
     * @param endTime       结束时间
     * @param ==1----天，时，分。 ==2----时
     * @return 返回时间差
     */
    public String getTimeDifference(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();

            long day = diff / (24 * 60 * 60 * 1000);
            long hour = (diff / (60 * 60 * 1000) - day * 24);
            long min = ((diff / (60 * 1000)) - day * 24 * 60 - hour * 60);
            long s = (diff / 1000 - day * 24 * 60 * 60 - hour * 60 * 60 - min * 60);
            long ms = (diff - day * 24 * 60 * 60 * 1000 - hour * 60 * 60 * 1000
                    - min * 60 * 1000 - s * 1000);
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");
            long hour1 = diff / (60 * 60 * 1000);
            String hourString = hour1 + "";
            long min1 = ((diff / (60 * 1000)) - hour1 * 60);
            timeString = hour1 + "小时" + min1 + "分";
            // System.out.println(day + "天" + hour + "小时" + min + "分" + s +
            // "秒");

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timeString;

    }

    /**
     * 计算相差的小时
     *
     * @param starTime
     * @param endTime
     * @return
     */
    public static String getTimeDifferenceHour(String starTime, String endTime) {
        String timeString = "";
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endTime);

            long diff = parse1.getTime() - parse.getTime();
            String string = Long.toString(diff);

            float parseFloat = Float.parseFloat(string);

            float hour1 = parseFloat / (60 * 60 * 1000);

//            timeString = Float.toString(hour1);

            DecimalFormat df1 = new DecimalFormat("#.#");
            DecimalFormat df2 = new DecimalFormat("#");

            if (hour1 > 24) {
                return df1.format(hour1 / 24) + "天";
            } else {
                return df2.format(Math.ceil(hour1)) + "小时";
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";

    }

    /**
     * 把时间戳变yyyy-MM-dd HH:mm格式时间
     *
     * @param time
     * @return
     */
    public static String getDateToString(long time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    public static String getDateToString2(long time) {
        String re_StrTime = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        // 例如：cc_time=1291778220
        long lcc_time = Long.valueOf(time);
        re_StrTime = sdf.format(new Date(lcc_time * 1000L));
        return re_StrTime;
    }

    /* 将字符串转为时间戳 */
    public static String getDateToString(String time) {
        String re_time = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date d;
        try {

            d = sdf.parse(time);
            long l = d.getTime() / 1000;
            re_time = String.valueOf(l);
//            re_time = str.substring(0, 13);


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return re_time;
    }

    /**
     * 返回时间戳
     *
     * @param time
     * @return
     */
    public long dataOne(String time) {
        SimpleDateFormat sdr = new SimpleDateFormat("yyyy-MM-dd HH:mm",
                Locale.CHINA);
        Date date;
        long l = 0;
        try {
            date = sdr.parse(time);
            l = date.getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return l;
    }

    /**
     * 比较两个时间
     *
     * @param starTime  开始时间
     * @param endString 结束时间
     * @return 结束时间大于开始时间返回true，否则反之֮
     */
    public static boolean compareTwoTime(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            if (diff > 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isDayu;

    }

    public boolean compareTwoTime2(String starTime, String endString) {
        boolean isDayu = false;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

        try {
            Date parse = dateFormat.parse(starTime);
            Date parse1 = dateFormat.parse(endString);

            long diff = parse1.getTime() - parse.getTime();
            if (diff >= 0) {
                isDayu = true;
            } else {
                isDayu = false;
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return isDayu;

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
    public static String getWeek(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = dateFormat.parse(date);
            String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
            Calendar cal = Calendar.getInstance();
            cal.setTime(parse);
            int week_index = cal.get(Calendar.DAY_OF_WEEK) - 1;
            if (week_index < 0) {
                week_index = 0;
            }
            return weeks[week_index];
        } catch (ParseException e) {
            e.printStackTrace();
        }

        return "";
    }

    //根据时间戳获取今天是周几
    public static String getWeekForTimeStamp(long date) {
        Date parse = new Date(date);
        String[] weeks = {"周日", "周一", "周二", "周三", "周四", "周五", "周六"};
        Calendar cal = Calendar.getInstance();
        cal.setTime(parse);
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
    public static String getYear(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int year = calendar.get(Calendar.YEAR);//获取年份
            return year + "";
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }

    public static int getYears(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int year = calendar.get(Calendar.YEAR);//获取年份
            return year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 功能描述：返回日期月份
     */
    public static int getMonth(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int year = calendar.get(Calendar.MONTH) + 1;//获取月份
            return year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    /**
     * 功能描述：返回日期日
     */
    public static int getDay(String date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date parse = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            int year = calendar.get(Calendar.DAY_OF_MONTH);//获取天
            return year;
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
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


    public static String getTimeByHour(String date, int hour) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        try {
            Date parse = null;
            parse = dateFormat.parse(date);
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(parse);
            calendar.set(Calendar.HOUR_OF_DAY, calendar.get(Calendar.HOUR_OF_DAY) + hour);
            dateFormat.format(calendar.getTime());
            return dateFormat.format(calendar.getTime());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";


    }

}
