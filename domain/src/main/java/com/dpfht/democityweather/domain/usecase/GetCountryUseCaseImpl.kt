package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetCountryUseCaseImpl(
  private val appRepository: AppRepository
): GetCountryUseCase {

  override suspend operator fun invoke(countryCode: String): Result<Country> {
    return try {
      Result.Success(appRepository.getCountry(countryCode))
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
