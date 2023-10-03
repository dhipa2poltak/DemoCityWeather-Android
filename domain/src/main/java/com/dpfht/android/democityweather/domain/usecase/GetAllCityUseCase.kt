package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.Result

interface GetAllCityUseCase {

  suspend operator fun invoke(): Result<List<CityEntity>>

}
