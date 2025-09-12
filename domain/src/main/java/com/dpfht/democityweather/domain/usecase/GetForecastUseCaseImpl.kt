package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.ForecastModel
import com.dpfht.democityweather.domain.model.LocalMessage
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.model.vw_model.ForecastHourlyVWModel
import com.dpfht.democityweather.domain.model.vw_model.ForecastVWModel
import com.dpfht.democityweather.domain.model.vw_model.ForecastWeeklyVWModel
import com.dpfht.democityweather.domain.repository.AppRepository
import com.dpfht.democityweather.domain.util.WeatherUtil
import java.text.SimpleDateFormat
import java.util.Calendar

class GetForecastUseCaseImpl(
  private val appRepository: AppRepository
): GetForecastUseCase {

  private val minHourlyData = 6

  override suspend operator fun invoke(cityWeather: CityWeather): Result<ForecastVWModel> {
    return try {
      val forecast = appRepository.getForecast(cityWeather)

      Result.Success(
        ForecastVWModel(
          hourlyEntities = getHourlyData(forecast),
          weeklyEntities = getWeeklyData(forecast)
        )
      )
    } catch (e: AppException) {
      Result.Error(e.message)
    } catch (e: Exception) {
      val msg = appRepository.getLocalMessage(LocalMessage.ErrorWhenGettingForecastData)

      Result.Error(msg)
    }
  }

  private fun getHourlyData(forecastDomain: ForecastModel): List<ForecastHourlyVWModel> {
    val list = arrayListOf<ForecastHourlyVWModel>()

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

        val hourlyVWModel = ForecastHourlyVWModel(strTime = sTimeForecast, description = description, strTemperature = sTemperature)
        list.add(hourlyVWModel)
      }
    }

    return list
  }

  private fun getWeeklyData(forecastDomain: ForecastModel): List<ForecastWeeklyVWModel> {
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

    val list = arrayListOf<ForecastWeeklyVWModel>()
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
          val newModel = ForecastWeeklyVWModel(day = sDayNameForecast, minTemperature = tempMin, maxTemperature = tempMax)
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
      weekly.maxDescription = maxEntry.key
    }

    return list
  }

  private fun getWeeklyModelByDayName(list: List<ForecastWeeklyVWModel>, dayName: String): ForecastWeeklyVWModel? {
    if (list.isEmpty()) return null

    for (model in list) {
      if (model.day == dayName) {
        return model
      }
    }

    return null
  }
}
