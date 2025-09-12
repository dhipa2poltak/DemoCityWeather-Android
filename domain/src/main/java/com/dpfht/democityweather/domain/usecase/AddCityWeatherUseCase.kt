package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Result

interface AddCityWeatherUseCase {

  suspend operator fun invoke(cityEntity: City): Result<CityWeather>
}
