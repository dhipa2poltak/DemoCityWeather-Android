package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Result

interface GetAllCityWeatherUseCase {

  suspend operator fun invoke(): Result<List<CityWeather>>
}
