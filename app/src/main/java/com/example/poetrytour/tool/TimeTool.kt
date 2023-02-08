package com.example.poetrytour.tool

import java.text.SimpleDateFormat
import java.util.*

object TimeTool {

    fun getTimeString(dt: Date?, pattern: String?): String {
        try {
            val sdf: SimpleDateFormat = SimpleDateFormat(pattern)
            sdf.setTimeZone(TimeZone.getDefault())
            return sdf.format(dt)
        } catch (e: java.lang.Exception) {
            return ""
        }
    }

    fun dateToString(dt:Date):String{
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        sdf.setTimeZone(TimeZone.getDefault())
        return sdf.format(dt)
    }

    fun stringToDate(str :String):Date{
        val sdf: SimpleDateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
        return sdf.parse(str)
    }

    fun getShortByString(strTime: String):String{
        val time= stringToDate(strTime)
        return getShortByDate(time)
    }

    fun getShortByDate(time :Date): String{
        var str= getTimeString(time, "HH:mm")
        val current=GregorianCalendar()
        current.time=Date()
        val currentYear=current.get(GregorianCalendar.YEAR)
        val currentMonth=current.get(GregorianCalendar.MONTH)+1
        val currentDay=current.get(GregorianCalendar.DAY_OF_MONTH)

        val target=GregorianCalendar()
        target.time=time
        val year=target.get(GregorianCalendar.YEAR)
        val month=target.get(GregorianCalendar.MONTH)+1
        val day=target.get(GregorianCalendar.DAY_OF_MONTH)

        if (currentYear==year){
            val currentTimestamp = current.getTimeInMillis()
            val targetTimestamp = target.getTimeInMillis()

            val del=currentTimestamp-targetTimestamp
            if (currentMonth==month && currentDay==day){
                if (del< 60* 1000){
                    str="刚刚"
                }else{
                    str = getTimeString(time, "HH:mm")
                }
            }else {
                val yesterday=GregorianCalendar()
                yesterday.add(GregorianCalendar.DAY_OF_MONTH,-1)

                val beforeYesterday=GregorianCalendar()
                beforeYesterday.add(GregorianCalendar.DAY_OF_MONTH,-2)

                if (month== (yesterday.get(GregorianCalendar.MONTH)+1)
                    && day == yesterday.get(GregorianCalendar.DAY_OF_MONTH)) {
                    str="昨天 "+getTimeString(time, "HH:mm")
                } else if (month == (beforeYesterday.get(GregorianCalendar.MONTH)+1)
                    && day == beforeYesterday.get(GregorianCalendar.DAY_OF_MONTH)) {
                    str="前天 "+getTimeString(time, "HH:mm")
                }else {
                    val delHour=(del/(3600* 1000))
                    if (delHour<7*24){
                        val weekday = arrayOf("星期日","星期一","星期二","星期三","星期四","星期五","星期六")
                        val weedayDesc = weekday[target.get(GregorianCalendar.DAY_OF_WEEK)-1]
                        str=weedayDesc+" "+getTimeString(time, "HH:mm")
                    }else {
                        str= getTimeString(time,"yyyy/M/d")
                    }
                }
            }
        }
        else{
            str= getTimeString(time,"yyyy/M/d")
        }
        return  str
    }
}


