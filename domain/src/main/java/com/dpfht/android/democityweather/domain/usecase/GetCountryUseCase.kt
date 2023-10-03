package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.Result

interface GetCountryUseCase {

  suspend operator fun invoke(countryCode: String): Result<CountryEntity>
}
