package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetCountryUseCaseImpl(
  private val appRepository: AppRepository
): GetCountryUseCase {

  override suspend operator fun invoke(countryCode: String): Result<CountryEntity> {
    return try {
      Result.Success(appRepository.getCountry(countryCode))
    } catch (e: Exception) {
      Result.ErrorResult(e.message ?: "")
    }
  }
}
