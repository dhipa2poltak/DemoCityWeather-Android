package com.dpfht.android.democityweather.domain.util

import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object WeatherUtil {

  fun getFormattedTemperatureString(temp: Double): String {
    return String.format(Locale.getDefault(), "%.0f°C", temp)
  }

  fun convertUTCTimeToLocalTime(utcTime: String): String {
    val utcFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    utcFormatter.timeZone = TimeZone.getTimeZone("UTC")
    var gpsUTCDate: Date? = null //from  ww  w.j  a va 2 s  . c  o  m

    try {
      gpsUTCDate = utcFormatter.parse(utcTime)
    } catch (e: ParseException) {
      e.printStackTrace()
    }

    val localFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
    localFormatter.timeZone = TimeZone.getDefault()

    return localFormatter.format(gpsUTCDate?.time)
  }
}
