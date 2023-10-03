package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.repository.AppRepository

class AddCityWeatherUseCaseImpl(
  private val appRepository: AppRepository
): AddCityWeatherUseCase {

  override suspend operator fun invoke(cityEntity: CityEntity): Result<CityWeatherEntity> {
    return appRepository.addCityWeather(cityEntity)
  }
}
