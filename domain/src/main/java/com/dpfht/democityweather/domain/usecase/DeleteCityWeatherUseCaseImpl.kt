package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.VoidResult
import com.dpfht.democityweather.domain.repository.AppRepository

class DeleteCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): DeleteCityWeatherUseCase {

  override suspend operator fun invoke(cityWeatherEntity: CityWeatherEntity): VoidResult {
    return try {
      appRepository.deleteCityWeather(cityWeatherEntity)

      VoidResult.Success
    } catch (e: Exception) {
      VoidResult.Error(e.message ?: "")
    }
  }
}
