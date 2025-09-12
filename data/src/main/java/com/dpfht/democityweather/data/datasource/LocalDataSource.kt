package com.dpfht.democityweather.data.datasource

import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.LocalMessage
import io.reactivex.rxjava3.core.Observable

interface LocalDataSource {

  suspend fun getAllCity(): List<City>
  suspend fun getCountry(countryCode: String): List<Country>
  suspend fun saveCountry(countryEntity: Country)
  fun getStreamIsDBInitialized(): Observable<Boolean>
  suspend fun getAllCityWeather(): List<CityWeather>
  suspend fun addCityWeather(cityEntity: City): CityWeather
  suspend fun deleteCityWeather(cityWeatherEntity: CityWeather)
  fun getLocalMessage(localMessage: LocalMessage): String
}
