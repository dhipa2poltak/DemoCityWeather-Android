package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.repository.AppRepository

class GetCountryUseCaseImpl(
  private val appRepository: AppRepository
): GetCountryUseCase {

  override suspend operator fun invoke(countryCode: String): Result<CountryEntity> {
    return appRepository.getCountry(countryCode)
  }
}
