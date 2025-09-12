package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetAllCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): GetAllCityWeatherUseCase {

  override suspend operator fun invoke(): Result<List<CityWeather>> {
    return try {
      Result.Success(appRepository.getAllCityWeather())
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
