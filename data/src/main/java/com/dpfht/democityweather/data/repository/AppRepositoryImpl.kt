package com.dpfht.democityweather.data.repository

import com.dpfht.democityweather.data.datasource.LocalDataSource
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.democityweather.domain.entity.CityEntity
import com.dpfht.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.democityweather.domain.entity.CountryEntity
import com.dpfht.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.democityweather.domain.entity.ForecastDomain
import com.dpfht.democityweather.domain.repository.AppRepository
import io.reactivex.rxjava3.core.Observable

class AppRepositoryImpl(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
): AppRepository {

  override suspend fun getAllCity(): List<CityEntity> {
    return localDataSource.getAllCity()
  }

  override suspend fun getCountry(countryCode: String): CountryEntity {
    try {
      val list = localDataSource.getCountry(countryCode)
      if (list.isNotEmpty()) {
        return list.first()
      }
    } catch (_: Exception) {}

    val listCountry = remoteDataSource.getCountryInfo(countryCode)
    if (listCountry.isNotEmpty()) {
      val countryEntity = listCountry.first()

      try {
        saveCountry(countryEntity)
      } catch (_: Exception) {}

      return countryEntity
    }

    throw Exception("no country found")
  }

  override suspend fun saveCountry(countryEntity: CountryEntity) {
    return localDataSource.saveCountry(countryEntity)
  }

  override fun getStreamIsDBInitialized(): Observable<Boolean> {
    return localDataSource.getStreamIsDBInitialized()
  }

  override suspend fun getAllCityWeather(): List<CityWeatherEntity> {
    return localDataSource.getAllCityWeather()
  }

  override suspend fun addCityWeather(cityEntity: CityEntity): CityWeatherEntity {
    return localDataSource.addCityWeather(cityEntity)
  }

  override suspend fun deleteCityWeather(cityWeatherEntity: CityWeatherEntity) {
    return localDataSource.deleteCityWeather(cityWeatherEntity)
  }

  override suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): CurrentWeatherDomain {
    return remoteDataSource.getCurrentWeather(cityWeather)
  }

  override suspend fun getForecast(cityWeather: CityWeatherEntity): ForecastDomain {
    return remoteDataSource.getForecast(cityWeather)
  }
}
