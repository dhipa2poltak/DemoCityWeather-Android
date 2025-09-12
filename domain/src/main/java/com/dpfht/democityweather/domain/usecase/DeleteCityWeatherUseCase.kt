package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.VoidResult

interface DeleteCityWeatherUseCase {

  suspend operator fun invoke(cityWeatherEntity: CityWeather): VoidResult
}
