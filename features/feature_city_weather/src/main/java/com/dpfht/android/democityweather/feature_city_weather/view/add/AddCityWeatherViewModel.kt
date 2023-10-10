package com.dpfht.android.democityweather.feature_city_weather.view.add

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.usecase.GetAllCityUseCase
import com.dpfht.android.democityweather.feature_city_weather.view.add.adapter.AddCityAdapter
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AddCityWeatherViewModel @Inject constructor(
  private val getAllCityUseCase: GetAllCityUseCase,
  private val cities: ArrayList<CityEntity>,
  val addCityAdapter: AddCityAdapter
) : ViewModel() {

  private val _isShowDialogLoading = MutableLiveData<Boolean>()
  val isShowDialogLoading: LiveData<Boolean> = _isShowDialogLoading

  private val _toastMessage = MutableLiveData<String>()
  val toastMessage: LiveData<String> = _toastMessage

  private val _modalMessage = MutableLiveData<String>()
  val modalMessage: LiveData<String> = _modalMessage

  init {
    addCityAdapter.cities = cities
    addCityAdapter.scope = viewModelScope
  }

  fun start() {
    _isShowDialogLoading.postValue(true)

    viewModelScope.launch {
      when (val result = getAllCityUseCase()) {
        is Result.Success -> {
          onSuccessGetAllCity(result.value)
        }
        is Result.ErrorResult -> {
          onErrorGetAllCity(result.message)
        }
      }
    }
  }

  private fun onSuccessGetAllCity(cities: List<CityEntity>) {
    _isShowDialogLoading.postValue(false)
    this.cities.clear()
    this.cities.addAll(cities)
    this.addCityAdapter.notifyDataSetChanged()
  }

  private fun onErrorGetAllCity(message: String) {
    _isShowDialogLoading.value = false
    _toastMessage.value = message
    _toastMessage.postValue("")
  }
}
