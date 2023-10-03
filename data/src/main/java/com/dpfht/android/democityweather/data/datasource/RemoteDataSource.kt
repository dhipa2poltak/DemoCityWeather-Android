package com.dpfht.android.democityweather.data.datasource

import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.ForecastDomain

interface RemoteDataSource {

  suspend fun getCountryInfo(countryCode: String): Result<List<CountryEntity>>
  suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain>
  suspend fun getForecast(cityWeather: CityWeatherEntity): Result<ForecastDomain>
}
