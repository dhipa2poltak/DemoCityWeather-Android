package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.Result

interface GetForecastUseCase {

  suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<ForecastDomain>
}
