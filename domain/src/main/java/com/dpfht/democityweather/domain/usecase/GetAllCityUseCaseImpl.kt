package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetAllCityUseCaseImpl(
  private val appRepository: AppRepository
): GetAllCityUseCase {

  override suspend operator fun invoke(): Result<List<CityEntity>> {
    return appRepository.getAllCity()
  }
}
