package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.Result

interface GetCurrentWeatherUseCase {

  suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain>
}
