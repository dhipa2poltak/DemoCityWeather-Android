package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.VoidResult

interface DeleteCityWeatherUseCase {

  suspend operator fun invoke(cityWeatherEntity: CityWeatherEntity): VoidResult
}
