package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.VoidResult
import com.dpfht.democityweather.domain.repository.AppRepository

class DeleteCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): DeleteCityWeatherUseCase {

  override suspend operator fun invoke(cityWeatherEntity: CityWeather): VoidResult {
    return try {
      appRepository.deleteCityWeather(cityWeatherEntity)

      VoidResult.Success
    } catch (e: AppException) {
      VoidResult.Error(e.message)
    }
  }
}
