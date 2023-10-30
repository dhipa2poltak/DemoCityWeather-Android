package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class AddCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): AddCityWeatherUseCase {

  override suspend operator fun invoke(cityEntity: CityEntity): Result<CityWeatherEntity> {
    return try {
      Result.Success(appRepository.addCityWeather(cityEntity))
    } catch (e: Exception) {
      Result.ErrorResult(e.message ?: "")
    }
  }
}
