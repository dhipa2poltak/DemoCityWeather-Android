package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.Result

interface GetAllCityWeatherUseCase {

  suspend operator fun invoke(): Result<List<CityWeatherEntity>>
}
