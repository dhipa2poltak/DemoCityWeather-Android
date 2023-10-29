package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.Result

interface GetCountryUseCase {

  suspend operator fun invoke(countryCode: String): Result<CountryEntity>
}
