package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.Result

interface GetAllCityUseCase {

  suspend operator fun invoke(): Result<List<City>>

}
