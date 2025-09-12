package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetCurrentWeatherUseCaseImpl(
  private val appRepository: AppRepository
): GetCurrentWeatherUseCase {

  override suspend operator fun invoke(cityWeather: CityWeather): Result<CurrentWeatherModel> {
    return try {
      Result.Success(appRepository.getCurrentWeather(cityWeather))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
