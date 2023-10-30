package com.dpfht.democityweather.data.datasource

import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.ForecastDomain

interface RemoteDataSource {

  suspend fun getCountryInfo(countryCode: String): List<CountryEntity>
  suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): CurrentWeatherDomain
  suspend fun getForecast(cityWeather: CityWeatherEntity): ForecastDomain
}
