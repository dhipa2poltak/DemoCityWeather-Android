package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastHourlyVWEntity
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastVWEntity
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastWeeklyVWEntity
import com.dpfht.android.democityweather.domain.repository.AppRepository
import com.dpfht.android.democityweather.domain.util.WeatherUtil
import java.text.SimpleDateFormat
import java.util.Calendar

class GetForecastUseCaseImpl(
  private val appRepository: AppRepository
): GetForecastUseCase {

  private val minHourlyData = 6

  override suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<ForecastVWEntity> {
    return when (val result = appRepository.getForecast(cityWeather)) {
      is Result.Success -> {
        return Result.Success(
          ForecastVWEntity(
            hourlyEntities = getHourlyData(result.value),
            weeklyEntities = getWeeklyData(result.value)
          )
        )
      }
      is Result.ErrorResult -> {
        result
      }
    }
  }

  private fun getHourlyData(forecastDomain: ForecastDomain): List<ForecastHourlyVWEntity> {
    val list = arrayListOf<ForecastHourlyVWEntity>()

    val now = Calendar.getInstance().time
    val formatDay = SimpleDateFormat("yyyy-MM-dd")
    val sNow = formatDay.format(now)

    val formatDefault = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val formatTimeCustom = SimpleDateFormat("kk:mm")
    for (forecast in forecastDomain.forecasts) {
      val sLocalTimeForecast = WeatherUtil.convertUTCTimeToLocalTime(forecast.dtTxt)
      val timeForecast = formatDefault.parse(sLocalTimeForecast)
      val sDayForecast = formatDay.format(timeForecast)

      if (sDayForecast == sNow || list.size < minHourlyData) {
        val sTimeForecast = formatTimeCustom.format(timeForecast)
        var sTemperature = ""

        forecast.main?.let {
          sTemperature = WeatherUtil.getFormattedTemperatureString(it.temp)
        }

        var description = ""
        if (forecast.weathers.isNotEmpty()) {
          description = forecast.weathers[0].description
        }

        val hourlyVWModel = ForecastHourlyVWEntity(strTime = sTimeForecast, description = description, strTemperature = sTemperature)
        list.add(hourlyVWModel)
      }
    }

    return list
  }

  private fun getWeeklyData(forecastDomain: ForecastDomain): List<ForecastWeeklyVWEntity> {
    val now = Calendar.getInstance().time
    val formatDay = SimpleDateFormat("yyyy-MM-dd")
    val sNow = formatDay.format(now)

    val formatDefault = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val newForecast = forecastDomain.forecasts.filter {
      val sTimeForecast = WeatherUtil.convertUTCTimeToLocalTime(it.dtTxt)
      val timeForecast = formatDefault.parse(sTimeForecast)
      val sDayForecast = formatDay.format(timeForecast)

      sDayForecast != sNow
    }

    val list = arrayListOf<ForecastWeeklyVWEntity>()
    val formatDayName = SimpleDateFormat("EEEE")
    for (forecast in newForecast) {
      val sTimeForecast = WeatherUtil.convertUTCTimeToLocalTime(forecast.dtTxt)
      val timeForecast = formatDefault.parse(sTimeForecast)
      val sDayNameForecast = formatDayName.format(timeForecast)

      if (forecast.main != null) {
        val main = forecast.main

        val tempMin = main.tempMin
        val tempMax = main.tempMax

        val model = getWeeklyModelByDayName(list, sDayNameForecast)
        if (model != null) {
          if (tempMin < model.minTemperature) {
            model.minTemperature = tempMin
          }

          if (tempMax > model.maxTemperature) {
            model.maxTemperature = tempMax
          }
        } else {
          val newModel = ForecastWeeklyVWEntity(day = sDayNameForecast, minTemperature = tempMin, maxTemperature = tempMax)
          list.add(newModel)
        }
      }

      if (forecast.weathers.isNotEmpty()) {
        val description = forecast.weathers[0].description
        val model = getWeeklyModelByDayName(list, sDayNameForecast)
        model?.let {
          if (it.mapDesc.isEmpty() || !it.mapDesc.keys.contains(description)) {
            it.mapDesc.put(description, 1)
          } else {
            it.mapDesc.put(description, it.mapDesc[description]?.plus(1) ?: 1)
          }
        }
      }
    }

    for (weekly in list) {
      val maxEntry = weekly.mapDesc.maxWith { x, y -> x.value.compareTo(y.value) }
      weekly.maxDesciption = maxEntry.key
    }

    return list
  }

  private fun getWeeklyModelByDayName(list: List<ForecastWeeklyVWEntity>, dayName: String): ForecastWeeklyVWEntity? {
    if (list.isEmpty()) return null

    for (model in list) {
      if (model.day == dayName) {
        return model
      }
    }

    return null
  }
}
