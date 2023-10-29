package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.Result

interface AddCityWeatherUseCase {

  suspend operator fun invoke(cityEntity: CityEntity): Result<CityWeatherEntity>
}
