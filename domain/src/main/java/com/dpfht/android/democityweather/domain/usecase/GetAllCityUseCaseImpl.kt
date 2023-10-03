package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.repository.AppRepository

class GetAllCityUseCaseImpl(
  private val appRepository: AppRepository
): GetAllCityUseCase {

  override suspend operator fun invoke(): Result<List<CityEntity>> {
    return appRepository.getAllCity()
  }
}
