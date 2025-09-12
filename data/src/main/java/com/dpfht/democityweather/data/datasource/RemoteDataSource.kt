package com.dpfht.democityweather.data.datasource

import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.ForecastModel

interface RemoteDataSource {

  suspend fun getCountryInfo(countryCode: String): List<Country>
  suspend fun getCurrentWeather(cityWeather: CityWeather): CurrentWeatherModel
  suspend fun getForecast(cityWeather: CityWeather): ForecastModel
}
