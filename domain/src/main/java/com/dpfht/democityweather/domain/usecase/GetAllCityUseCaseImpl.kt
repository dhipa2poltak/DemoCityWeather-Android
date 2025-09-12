package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.Result
import com.dpfht.democityweather.domain.repository.AppRepository

class GetAllCityUseCaseImpl(
  private val appRepository: AppRepository
): GetAllCityUseCase {

  override suspend operator fun invoke(): Result<List<City>> {
    return try {
      Result.Success(appRepository.getAllCity())
    } catch (e: AppException) {
      Result.Error(e.message)
    }
  }
}
