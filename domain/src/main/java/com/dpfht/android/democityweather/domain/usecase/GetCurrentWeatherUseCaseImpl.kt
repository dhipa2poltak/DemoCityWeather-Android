package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.repository.AppRepository

class GetCurrentWeatherUseCaseImpl(
  private val appRepository: AppRepository
): GetCurrentWeatherUseCase {

  override suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain> {
    return appRepository.getCurrentWeather(cityWeather)
  }
}
