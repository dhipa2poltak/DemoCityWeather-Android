package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.AppException
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetAllCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): GetAllCityWeatherUseCase {

  override suspend operator fun invoke(): Result<List<CityWeatherEntity>> {
    return try {
      Result.Success(appRepository.getAllCityWeather())
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
