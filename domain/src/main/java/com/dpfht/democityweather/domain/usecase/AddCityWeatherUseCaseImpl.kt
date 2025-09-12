package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class AddCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): AddCityWeatherUseCase {

  override suspend operator fun invoke(cityEntity: City): Result<CityWeather> {
    return try {
      Result.Success(appRepository.addCityWeather(cityEntity))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
