package com.dpfht.democityweather.data.datasource

import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.LocalMessage
import io.reactivex.rxjava3.core.Observable

interface LocalDataSource {

  suspend fun getAllCity(): List<CityEntity>
  suspend fun getCountry(countryCode: String): List<CountryEntity>
  suspend fun saveCountry(countryEntity: CountryEntity)
  fun getStreamIsDBInitialized(): Observable<Boolean>
  suspend fun getAllCityWeather(): List<CityWeatherEntity>
  suspend fun addCityWeather(cityEntity: CityEntity): CityWeatherEntity
  suspend fun deleteCityWeather(cityWeatherEntity: CityWeatherEntity)
  fun getLocalMessage(localMessage: LocalMessage): String
}
