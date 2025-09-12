package com.dpfht.android.democityweather.framework.navigation

import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.CityWeather

interface NavigationService {

  fun navigateToListOfCityWeather()
  fun navigateToAddCityWeather(onSelectCity: ((cityEntity: City) -> Unit)?)
  fun navigateToDetailsCityWeather(cityWeather: CityWeather)
  fun navigateToErrorMessage(message: String)
}
