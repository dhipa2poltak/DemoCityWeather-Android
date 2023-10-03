package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.Result

interface AddCityWeatherUseCase {

  suspend operator fun invoke(cityEntity: CityEntity): Result<CityWeatherEntity>
}
