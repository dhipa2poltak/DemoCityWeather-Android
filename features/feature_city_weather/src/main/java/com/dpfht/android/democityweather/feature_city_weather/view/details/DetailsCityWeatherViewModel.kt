package com.dpfht.android.democityweather.feature_city_weather.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.model.vw_model.ForecastHourlyVWModel
import com.dpfht.democityweather.domain.model.vw_model.ForecastVWModel
import com.dpfht.democityweather.domain.model.vw_model.ForecastWeeklyVWModel
import com.dpfht.democityweather.domain.usecase.GetCurrentWeatherUseCase
import com.dpfht.democityweather.domain.usecase.GetForecastUseCase
import com.dpfht.democityweather.domain.util.WeatherUtil
import com.dpfht.android.democityweather.feature_city_weather.util.ResourceUtil
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.HourlyAdapter
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.WeeklyAdapter
import com.dpfht.democityweather.domain.model.Result.Error
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
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

  lateinit var cityWeather: CityWeather

  private val hourlyVWEntities = arrayListOf<ForecastHourlyVWModel>()
  private val weeklyVWEntities = arrayListOf<ForecastWeeklyVWModel>()

  private val isRefreshingEnable = true

  init {
    hourlyAdapter.hourlyVWEntities = hourlyVWEntities
    weeklyAdapter.weeklyVWEntities = weeklyVWEntities
  }

  fun start() {
    hourlyVWEntities.clear()
    weeklyVWEntities.clear()

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
        is Error -> {
          onErrorGetCurrentWeather(result.message)
        }
      }
    }
  }

  private fun onSuccessGetCurrentWeather(currentWeather: CurrentWeatherModel) {
    var animationId = -1

    val temp = currentWeather.main?.temp ?: 0.0
    val strTemp = WeatherUtil.getFormattedTemperatureString(temp)

    if (currentWeather.weathers.isNotEmpty()) {
      val description = currentWeather.weathers[0].description
      animationId = ResourceUtil.getAnimationResourceForWeatherDescription(description)
    }

    _tempData.value = Pair(strTemp, animationId)
  }

  private fun onErrorGetCurrentWeather(message: String) {
    _modalMessage.value = message
    _modalMessage.postValue("")
  }

  private fun getForecast() {
    viewModelScope.launch {
      when (val result = getForecastUseCase(cityWeather)) {
        is Result.Success -> {
          onSuccessGetForecast(result.value)
        }
        is Error -> {
          onErrorGetForecast(result.message)
        }
      }
    }
  }

  private fun onSuccessGetForecast(forecastVWEntity: ForecastVWModel) {
    for (hourly in forecastVWEntity.hourlyEntities) {
      if (hourly.description.isNotEmpty()) {
        val animationId = ResourceUtil.getAnimationResourceForWeatherDescription(hourly.description)
        hourly.animationId = animationId
      }

      this.hourlyVWEntities.add(hourly)
      this.hourlyAdapter.notifyItemInserted(hourlyVWEntities.size - 1)
    }

    for (weekly in forecastVWEntity.weeklyEntities) {
      weekly.animationId = ResourceUtil.getAnimationResourceForWeatherDescription(weekly.maxDescription)

      this.weeklyVWEntities.add(weekly)
      this.weeklyAdapter.notifyItemInserted(weeklyVWEntities.size - 1)
    }

    _isShowDialogLoading.value = false
  }

  private fun onErrorGetForecast(message: String) {
    _modalMessage.value = message
    _modalMessage.postValue("")
    _isShowDialogLoading.value = false
  }

  fun onRefreshing() {
    if (!isRefreshingEnable) {
      _isShowDialogLoading.postValue(false)
      return
    }

    start()
  }
}
