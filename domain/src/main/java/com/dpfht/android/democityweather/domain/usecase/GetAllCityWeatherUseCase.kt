package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.Result

interface GetAllCityWeatherUseCase {

  suspend operator fun invoke(): Result<List<CityWeatherEntity>>
}
