package com.dpfht.android.democityweather.feature_city_weather.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.usecase.GetCurrentWeatherUseCase
import com.dpfht.android.democityweather.domain.usecase.GetForecastUseCase
import com.dpfht.android.democityweather.feature_city_weather.util.WeatherUtil
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.HourlyAdapter
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.WeeklyAdapter
import com.dpfht.android.democityweather.feature_city_weather.view.details.model.HourlyVWModel
import com.dpfht.android.democityweather.feature_city_weather.view.details.model.WeeklyVWModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import javax.inject.Inject

@HiltViewModel
class DetailsCityWeatherViewModel @Inject constructor(
  private val getCurrentWeatherUseCase: GetCurrentWeatherUseCase,
  private val getForecastUseCase: GetForecastUseCase,
  val hourlyAdapter: HourlyAdapter,
  val weeklyAdapter: WeeklyAdapter
): ViewModel() {

  private val _isShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = _isShowDialogLoading

  private val _toastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = _toastMessage

  private val _modalMessage = MutableLiveData<String>()
  val modalMessage: LiveData<String> = _modalMessage

  private val _tempData = MutableLiveData<Pair<String, Int>>()
  val tempData: LiveData<Pair<String, Int>> = _tempData

  lateinit var cityWeather: CityWeatherEntity

  private val hourlyModels = arrayListOf<HourlyVWModel>()
  private val weeklyModels = arrayListOf<WeeklyVWModel>()

  private val isRefreshingEnable = true
  private val MAX_HOURLY_DATA = 6

  init {
    hourlyAdapter.hourlyModels = hourlyModels
    weeklyAdapter.weeklyModels = weeklyModels
  }

  fun start() {
    hourlyModels.clear()
    weeklyModels.clear()

    hourlyAdapter.notifyDataSetChanged()
    weeklyAdapter.notifyDataSetChanged()

    getCurrentWeather()
    getForecast()
  }

  private fun getCurrentWeather() {
    _isShowDialogLoading.postValue(true)

    viewModelScope.launch {
      when (val result = getCurrentWeatherUseCase(cityWeather)) {
        is Result.Success -> {
          onSuccessGetCurrentWeather(result.value)
        }
        is Result.ErrorResult -> {
          onErrorGetCurrentWeather(result.message)
        }
      }
    }
  }

  private fun onSuccessGetCurrentWeather(currentWeather: CurrentWeatherDomain) {
    var animationId = -1

    val temp = currentWeather.main?.temp ?: 0.0
    val strTemp = WeatherUtil.getFormattedTemperatureString(temp)

    if (currentWeather.weathers.isNotEmpty()) {
      val description = currentWeather.weathers[0].description
      animationId = WeatherUtil.getAnimationResourceForWeatherDescription(description)
    }

    _tempData.value = Pair(strTemp, animationId)
  }

  private fun onErrorGetCurrentWeather(message: String) {
    _modalMessage.value = message
    _modalMessage.value = ""
  }

  private fun getForecast() {
    viewModelScope.launch {
      when (val result = getForecastUseCase(cityWeather)) {
        is Result.Success -> {
          onSuccessGetForecast(result.value)
        }
        is Result.ErrorResult -> {
          onErrorGetForecast(result.message)
        }
      }
    }
  }

  private fun onSuccessGetForecast(forecastDomain: ForecastDomain) {
    initHourlyData(forecastDomain)
    initWeeklyData(forecastDomain)
    _isShowDialogLoading.value = false
  }

  private fun onErrorGetForecast(message: String) {
    _modalMessage.value = message
    _modalMessage.value = ""
    _isShowDialogLoading.value = false
  }

  private fun initHourlyData(forecastDomain: ForecastDomain) {
    val now = Calendar.getInstance().time
    val formatDay = SimpleDateFormat("yyyy-MM-dd")
    val sNow = formatDay.format(now)

    val formatDefault = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val formatTimeCustom = SimpleDateFormat("kk:mm")
    for (forecast in forecastDomain.forecasts) {
      val sTimeForecast = WeatherUtil.convertUTCTimeToLocalTime(forecast.dtTxt)
      val timeForecast = formatDefault.parse(sTimeForecast)
      val sDayForecast = formatDay.format(timeForecast)

      if (sDayForecast == sNow || hourlyModels.size < MAX_HOURLY_DATA) {
        val sTimeForecast = formatTimeCustom.format(timeForecast)
        var animationId = -1
        var sTemperatur = ""

        forecast.main?.let {
          sTemperatur = WeatherUtil.getFormattedTemperatureString(it.temp)
        }

        if (forecast.weathers.isNotEmpty()) {
          animationId = WeatherUtil.getAnimationResourceForWeatherDescription(forecast.weathers[0].description)
        }

        val hourlyVWModel = HourlyVWModel(sTimeForecast, animationId, sTemperatur)
        this.hourlyModels.add(hourlyVWModel)
        this.hourlyAdapter.notifyItemInserted(this.hourlyModels.size - 1)
      }
    }
  }

  private fun initWeeklyData(forecastDomain: ForecastDomain) {
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

    val list = arrayListOf<WeeklyVWModel>()
    val formatDayName = SimpleDateFormat("EEEE")
    for (forecast in newForecast) {
      val sTimeForecast = WeatherUtil.convertUTCTimeToLocalTime(forecast.dtTxt)
      val timeForecast = formatDefault.parse(sTimeForecast)
      val sDayNameForecast = formatDayName.format(timeForecast)

      if (forecast.main != null) {
        val main = forecast.main!!

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
          val newModel = WeeklyVWModel(day = sDayNameForecast, minTemperature = tempMin, maxTemperature = tempMax)
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

    for (model in list) {
      val maxEntry = model.mapDesc.maxWith { x, y -> x.value.compareTo(y.value) }
      model.animationId = WeatherUtil.getAnimationResourceForWeatherDescription(maxEntry.key)
    }

    this.weeklyModels.addAll(list)
    this.weeklyAdapter.notifyDataSetChanged()
  }

  private fun getWeeklyModelByDayName(list: List<WeeklyVWModel>, dayName: String): WeeklyVWModel? {
    if (list.isEmpty()) return null

    for (model in list) {
      if (model.day == dayName) {
        return model
      }
    }

    return null
  }

  fun onRefreshing() {
    if (!isRefreshingEnable) {
      _isShowDialogLoading.postValue(false)
      return
    }

    start()
  }
}
