package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.VoidResult

interface DeleteCityWeatherUseCase {

  suspend operator fun invoke(cityWeatherEntity: CityWeatherEntity): VoidResult
}