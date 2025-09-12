package com.dpfht.democityweather.domain.repository

import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.ForecastModel
import com.dpfht.democityweather.domain.model.LocalMessage
import io.reactivex.rxjava3.core.Observable

interface AppRepository {

  suspend fun getAllCity(): List<City>
  suspend fun getCountry(countryCode: String): Country
  suspend fun saveCountry(countryEntity: Country)
  fun getStreamIsDBInitialized(): Observable<Boolean>
  suspend fun getAllCityWeather(): List<CityWeather>
  suspend fun addCityWeather(cityEntity: City): CityWeather
  suspend fun deleteCityWeather(cityWeatherEntity: CityWeather)
  suspend fun getCurrentWeather(cityWeather: CityWeather): CurrentWeatherModel
  suspend fun getForecast(cityWeather: CityWeather): ForecastModel
  fun getLocalMessage(localMessage: LocalMessage): String
}
