package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.Result

interface GetCountryUseCase {

  suspend operator fun invoke(countryCode: String): Result<Country>
}
