package com.dpfht.android.democityweather.feature_city_weather.view.list

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.VoidResult
import com.dpfht.democityweather.domain.usecase.AddCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.DeleteCityWeatherUseCase
import com.dpfht.democityweather.domain.usecase.GetAllCityWeatherUseCase
import com.dpfht.android.democityweather.feature_city_weather.view.list.adapter.CityWeatherAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ListCityWeatherViewModel @Inject constructor(
  private val getAllCityWeatherUseCase: GetAllCityWeatherUseCase,
  private val addCityWeatherUseCase: AddCityWeatherUseCase,
  private val deleteCityWeatherUseCase: DeleteCityWeatherUseCase,
  val cityWeathers: ArrayList<CityWeatherEntity>,
  val cityWeatherAdapter: CityWeatherAdapter
): ViewModel() {

  private val _toastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = _toastMessage

  private val _isNoData = MutableLiveData<Boolean>()
  val isNoData: LiveData<Boolean> = _isNoData

  private val _isRefreshing = MutableLiveData<Boolean>()
  val isRefreshing: LiveData<Boolean> = _isRefreshing

  private val _navigateToWeatherDetails = MutableLiveData<CityWeatherEntity?>()
  val navigateToWeatherDetails: LiveData<CityWeatherEntity?> = _navigateToWeatherDetails

  private var isRefreshingDataEnabled = true

  init {
    cityWeatherAdapter.cityWeathers = cityWeathers
    cityWeatherAdapter.scope = viewModelScope
    cityWeatherAdapter.onClickRowCityWeather = this::onClickCityWeather
    cityWeatherAdapter.onDeleteCityWeather = this::deleteCityWeather
  }

  fun start() {
    getAllCityWeather()
  }

  private fun getAllCityWeather() {
    _isRefreshing.postValue(true)

    viewModelScope.launch {
      when (val result = getAllCityWeatherUseCase()) {
        is Result.Success -> {
          onSuccessGetAllCityWeather(result.value)
        }
        is Result.ErrorResult -> {
          onErrorGetAllCityWeather(result.message)
        }
      }
    }
  }

  private fun onSuccessGetAllCityWeather(cityWeathers: List<CityWeatherEntity>) {
    this.cityWeathers.clear()
    this.cityWeatherAdapter.notifyDataSetChanged()
    this.cityWeathers.addAll(cityWeathers)
    this.cityWeatherAdapter.notifyDataSetChanged()
    _isNoData.postValue(this.cityWeathers.isEmpty())
    _isRefreshing.postValue(false)
  }

  private fun onErrorGetAllCityWeather(message: String) {
    _toastMessage.value = message
    _toastMessage.postValue("")
    _isNoData.value = this.cityWeathers.isEmpty()
    _isRefreshing.postValue(false)
  }

  fun addCityWeather(cityEntity: CityEntity) {
    _isRefreshing.postValue(true)

    viewModelScope.launch {
      when (val result = addCityWeatherUseCase(cityEntity)) {
        is Result.Success -> {
          onSuccessAddCityWeather(result.value)
        }
        is Result.ErrorResult -> {
          onErrorAddCityWeather(result.message)
        }
      }
    }
  }

  private fun onSuccessAddCityWeather(cityWeather: CityWeatherEntity) {
    _isRefreshing.postValue(false)
    this.cityWeathers.add(cityWeather)
    this.cityWeatherAdapter.notifyItemInserted(this.cityWeathers.size - 1)
    _isNoData.postValue(this.cityWeathers.isEmpty())
  }

  private fun onErrorAddCityWeather(message: String) {
    _isRefreshing.postValue(false)
    _toastMessage.value = message
    _toastMessage.postValue("")
    _isNoData.value = this.cityWeathers.isEmpty()
  }

  private fun deleteCityWeather(cityWeather: CityWeatherEntity) {
    _isRefreshing.postValue(true)

    viewModelScope.launch {
      when (val result = deleteCityWeatherUseCase(cityWeather)) {
        VoidResult.Success -> {
          onSuccessDeleteCityWeather(cityWeather)
        }
        is VoidResult.Error -> {
          onErrorDeleteCityWeather(result.message)
        }
      }
    }
  }

  private fun onSuccessDeleteCityWeather(cityWeather: CityWeatherEntity) {
    _isRefreshing.postValue(false)
    val position = this.cityWeathers.indexOf(cityWeather)
    this.cityWeathers.remove(cityWeather)
    this.cityWeatherAdapter.notifyItemRemoved(position)
    _isNoData.value = this.cityWeathers.isEmpty()
  }

  private fun onErrorDeleteCityWeather(message: String) {
    _isRefreshing.postValue(false)
    _toastMessage.value = message
    _toastMessage.postValue("")
    _isNoData.value = this.cityWeathers.isEmpty()
  }

  fun onRefreshingData() {
    if (!isRefreshingDataEnabled) {
      _isRefreshing.postValue(false)
      return
    }

    getAllCityWeather()
  }

  private fun onClickCityWeather(cityWeather: CityWeatherEntity) {
    _navigateToWeatherDetails.value = cityWeather
    _navigateToWeatherDetails.postValue(null)
  }
}
