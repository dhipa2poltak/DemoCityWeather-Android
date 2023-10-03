package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.VoidResult
import com.dpfht.android.democityweather.domain.repository.AppRepository

class DeleteCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): DeleteCityWeatherUseCase {

  override suspend operator fun invoke(cityWeatherEntity: CityWeatherEntity): VoidResult {
    return appRepository.deleteCityWeather(cityWeatherEntity)
  }
}
