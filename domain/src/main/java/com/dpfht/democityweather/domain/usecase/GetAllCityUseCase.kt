package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.Result

interface GetAllCityUseCase {

  suspend operator fun invoke(): Result<List<CityEntity>>

}
