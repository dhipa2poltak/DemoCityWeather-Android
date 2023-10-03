package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.repository.AppRepository

class GetForecastUseCaseImpl(
  private val appRepository: AppRepository
): GetForecastUseCase {

  override suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<ForecastDomain> {
    return appRepository.getForecast(cityWeather)
  }
}
