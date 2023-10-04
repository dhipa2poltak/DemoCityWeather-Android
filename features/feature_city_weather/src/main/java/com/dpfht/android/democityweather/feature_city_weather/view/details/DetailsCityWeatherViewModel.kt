package com.dpfht.android.democityweather.feature_city_weather.view.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastHourlyVWEntity
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastVWEntity
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastWeeklyVWEntity
import com.dpfht.android.democityweather.domain.usecase.GetCurrentWeatherUseCase
import com.dpfht.android.democityweather.domain.usecase.GetForecastUseCase
import com.dpfht.android.democityweather.domain.util.WeatherUtil
import com.dpfht.android.democityweather.feature_city_weather.util.ResourceUtil
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.HourlyAdapter
import com.dpfht.android.democityweather.feature_city_weather.view.details.adapter.WeeklyAdapter
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

  lateinit var cityWeather: CityWeatherEntity

  private val hourlyVWEntities = arrayListOf<ForecastHourlyVWEntity>()
  private val weeklyVWEntities = arrayListOf<ForecastWeeklyVWEntity>()

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
      animationId = ResourceUtil.getAnimationResourceForWeatherDescription(description)
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

  private fun onSuccessGetForecast(forecastVWEntity: ForecastVWEntity) {
    for (hourly in forecastVWEntity.hourlyEntities) {
      if (hourly.description.isNotEmpty()) {
        val animationId = ResourceUtil.getAnimationResourceForWeatherDescription(hourly.description)
        hourly.animationId = animationId
      }

      this.hourlyVWEntities.add(hourly)
      this.hourlyAdapter.notifyItemInserted(hourlyVWEntities.size - 1)
    }

    for (weekly in forecastVWEntity.weeklyEntities) {
      weekly.animationId = ResourceUtil.getAnimationResourceForWeatherDescription(weekly.maxDesciption)

      this.weeklyVWEntities.add(weekly)
      this.weeklyAdapter.notifyItemInserted(weeklyVWEntities.size - 1)
    }

    _isShowDialogLoading.value = false
  }

  private fun onErrorGetForecast(message: String) {
    _modalMessage.value = message
    _modalMessage.value = ""
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
