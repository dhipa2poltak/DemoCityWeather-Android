package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetCurrentWeatherUseCaseImpl(
  private val appRepository: AppRepository
): GetCurrentWeatherUseCase {

  override suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain> {
    return try {
      Result.Success(appRepository.getCurrentWeather(cityWeather))
    } catch (e: Exception) {
      Result.ErrorResult(e.message ?: "")
    }
  }
}
