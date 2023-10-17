package com.dpfht.android.democityweather.framework.navigation

import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity

interface NavigationService {

  fun navigateToListOfCityWeather()
  fun navigateToAddCityWeather(onSelectCity: ((cityEntity: CityEntity) -> Unit)?)
  fun navigateToDetailsCityWeather(cityWeather: CityWeatherEntity)
  fun navigateToErrorMessage(message: String)
}
