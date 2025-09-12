package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.model.vw_model.ForecastVWModel

interface GetForecastUseCase {
  suspend operator fun invoke(cityWeather: CityWeather): Result<ForecastVWModel>
}
