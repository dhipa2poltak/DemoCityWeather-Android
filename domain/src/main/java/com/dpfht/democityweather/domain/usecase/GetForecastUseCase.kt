package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.entity.vw_entity.ForecastVWEntity

interface GetForecastUseCase {
  suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<ForecastVWEntity>
}
