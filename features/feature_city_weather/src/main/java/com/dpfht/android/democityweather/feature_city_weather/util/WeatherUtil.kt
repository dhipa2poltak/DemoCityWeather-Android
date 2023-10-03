package com.dpfht.android.democityweather.feature_city_weather.util

import com.dpfht.android.democityweather.feature_city_weather.R
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

object WeatherUtil {

  fun getFormattedTemperatureString(temp: Double): String {
    return String.format(Locale.getDefault(), "%.0f°C", temp)
  }

  fun getAnimationResourceForWeatherDescription(description: String): Int {
    return when (description) {
      "broken clouds" -> {
        R.raw.broken_clouds
      }
      "light rain" -> {
        R.raw.light_rain
      }
      "haze" -> {
        R.raw.broken_clouds
      }
      "overcast clouds" -> {
        R.raw.overcast_clouds
      }
      "moderate rain" -> {
        R.raw.moderate_rain
      }
      "few clouds" -> {
        R.raw.few_clouds
      }
      "heavy intensity rain" -> {
        R.raw.heavy_intentsity
      }
      "clear sky" -> {
        R.raw.clear_sky
      }
      "scattered clouds" -> {
        R.raw.scattered_clouds
      }
      else -> {
        R.raw.unknown
      }
    }
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
