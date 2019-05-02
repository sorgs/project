package com.sorgs.baseproject.utils

import android.annotation.SuppressLint
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*

/**
 * description: 时间工具类.
 *
 * @author Sorgs.
 * Created date: 2019/4/14.
 */
object TimeUtils {

    /**
     * 上午
     */
    val AM = "am"
    /**
     * 下午
     */
    val PM = "pm"
    /**
     * 上午下课时间
     */
    val END_AM_TIME = "12:00:00"

    val SUNDAY = "星期日"
    val MONDAY = "星期一"
    val TUESDAY = "星期二"
    val WEDNESDAY = "星期三"
    val THURSDAY = "星期四"
    val FRIDAY = "星期五"
    val SATURDAY = "星期六"

    /**
     * 根据当前日期获得是星期几
     */
    val week: String?
        get() {
            val calendar = Calendar.getInstance()
            return getWeekString(calendar)
        }

    /**
     * 判断当前是否为上午
     */
    val isTodayAm: Boolean
        get() {
            @SuppressLint("SimpleDateFormat")
            val format = SimpleDateFormat("HH:mm:ss")
            try {
                val endAm = format.parse(END_AM_TIME)
                return format.parse(format.format(Date())).before(endAm)
            } catch (e: ParseException) {
                e.printStackTrace()
            }

            return false
        }

    //当前时间
    val todayString: String
        @SuppressLint("SimpleDateFormat")
        get() = SimpleDateFormat("yyyy-MM-dd").format(Date())


    //当前时间
    val nowDate: Date?
        @SuppressLint("SimpleDateFormat")
        get() {
            val simpleDateFormat = SimpleDateFormat("yyyy-MM-dd")
            return try {
                simpleDateFormat.parse(simpleDateFormat.format(Date()))
            } catch (e: ParseException) {
                e.printStackTrace()
                null
            }

        }

    private fun getWeekString(calendar: Calendar): String? {
        var week: String? = null
        val wek = calendar.get(Calendar.DAY_OF_WEEK)
        if (wek == 1) {
            week = SUNDAY
        }
        if (wek == 2) {
            week = MONDAY
        }
        if (wek == 3) {
            week = TUESDAY
        }
        if (wek == 4) {
            week = WEDNESDAY
        }
        if (wek == 5) {
            week = THURSDAY
        }
        if (wek == 6) {
            week = FRIDAY
        }
        if (wek == 7) {
            week = SATURDAY
        }
        return week
    }

    /**
     * 根据传入时间获得是星期几
     */
    fun getWeek(date: Date): String? {
        val calendar = Calendar.getInstance()
        calendar.time = date
        return getWeekString(calendar)
    }

    /**
     * 判断是否为上午
     *
     * @param date 当前时间
     */
    fun isAm(date: String): Boolean {
        var date = date
        date = date.substring(date.lastIndexOf(" ") + 1)
        @SuppressLint("SimpleDateFormat")
        val format = SimpleDateFormat("HH:mm:ss")
        try {
            val nowTime = format.parse(date)
            val endAm = format.parse(END_AM_TIME)
            return nowTime.before(endAm)
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return false
    }

    /**
     * 获取两个时间差
     *
     * @param data 时间1
     * @param time 时间2
     * @return 时间差
     */
    fun getToTime(data: String, time: String): String? {
        @SuppressLint("SimpleDateFormat")
        val format = SimpleDateFormat("HH:mm:ss")
        try {
            val nowTime = format.parse(data)
            val endAm = format.parse(time)
            return longTimeToDay(Math.abs(nowTime.time - endAm.time))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return null
    }

    private fun longTimeToDay(ms: Long): String {
        val ss = 1000
        val mi = ss * 60
        val hh = mi * 60
        val dd = hh * 24

        val day = ms / dd
        val hour = (ms - day * dd) / hh
        val minute = (ms - day * dd - hour * hh) / mi
        val second = (ms - day * dd - hour * hh - minute * mi) / ss
        val milliSecond = ms - day * dd - hour * hh - minute * mi - second * ss

        val sb = StringBuffer()
        if (day > 0) {
            sb.append(day).append("天")
        }
        if (hour > 0) {
            sb.append(hour).append("小时")
        }
        if (minute > 0) {
            sb.append(minute).append("分")
        }
        if (second > 0) {
            sb.append(second).append("秒")
        }
        /*if (milliSecond > 0) {
            sb.append(milliSecond).append("毫秒");
        }*/
        return sb.toString()
    }

    /**
     * @param date 判断时间是否为今天
     */
    @SuppressLint("SimpleDateFormat")
    fun isToday(date: String): Boolean {
        val sf = SimpleDateFormat("yyyy-MM-dd")
        //获取今天的日期
        val nowDay = todayString
        //对比的时间
        var day: String? = null
        try {
            day = sf.format(sf.parse(date))
        } catch (e: ParseException) {
            e.printStackTrace()
        }

        return nowDay == day
    }

    /**
     * 时间转化
     *
     * @param date Date
     * @param type 格式
     * @return String的时间
     */
    @SuppressLint("SimpleDateFormat")
    fun getStringTime(date: Date, type: String): String {
        return SimpleDateFormat(type).format(date)
    }

    /**
     * 时间转化
     *
     * @param date Date
     * @param type 格式
     * @return String的时间
     */
    @SuppressLint("SimpleDateFormat")
    fun getStringTime(date: String, type: String): String {
        val format = SimpleDateFormat(type)
        try {
            return format.format(format.parse(date))
        } catch (e: ParseException) {
            e.printStackTrace()
            return ""
        }

    }

    /**
     * 时间转化
     *
     * @param date Date
     * @param type 格式
     * @return String的时间
     */
    @SuppressLint("SimpleDateFormat")
    fun getDataTime(date: Date, type: String): Date {
        val format = SimpleDateFormat(type)
        try {
            return format.parse(format.format(date))
        } catch (e: ParseException) {
            e.printStackTrace()
            return Date()
        }

    }
}
