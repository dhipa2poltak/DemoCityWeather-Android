package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.Result

interface GetCurrentWeatherUseCase {

  suspend operator fun invoke(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain>
}
