package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.Result

interface GetCurrentWeatherUseCase {

  suspend operator fun invoke(cityWeather: CityWeather): Result<CurrentWeatherModel>
}
