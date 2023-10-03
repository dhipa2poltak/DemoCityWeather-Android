package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.entity.vw_entity.ForecastVWEntity

interface GetForecastUseCase {
  suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<ForecastVWEntity>
}
