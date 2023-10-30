package com.dpfht.democityweather.domain.repository

import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.ForecastDomain
import io.reactivex.rxjava3.core.Observable

interface AppRepository {

  suspend fun getAllCity(): List<CityEntity>
  suspend fun getCountry(countryCode: String): CountryEntity
  suspend fun saveCountry(countryEntity: CountryEntity)
  fun getStreamIsDBInitialized(): Observable<Boolean>
  suspend fun getAllCityWeather(): List<CityWeatherEntity>
  suspend fun addCityWeather(cityEntity: CityEntity): CityWeatherEntity
  suspend fun deleteCityWeather(cityWeatherEntity: CityWeatherEntity)
  suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): CurrentWeatherDomain
  suspend fun getForecast(cityWeather: CityWeatherEntity): ForecastDomain
}
