package com.dpfht.democityweather.data.repository

import com.dpfht.democityweather.data.datasource.LocalDataSource
import com.dpfht.democityweather.data.datasource.RemoteDataSource
import com.dpfht.democityweather.domain.model.AppException
import com.dpfht.democityweather.domain.model.City
import com.dpfht.democityweather.domain.model.CityWeather
import com.dpfht.democityweather.domain.model.Country
import com.dpfht.democityweather.domain.model.CurrentWeatherModel
import com.dpfht.democityweather.domain.model.ForecastModel
import com.dpfht.democityweather.domain.model.LocalMessage
import com.dpfht.democityweather.domain.model.LocalMessage.NoCountryFound
import com.dpfht.democityweather.domain.repository.AppRepository
import io.reactivex.rxjava3.core.Observable

class AppRepositoryImpl(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
): AppRepository {

  override suspend fun getAllCity(): List<City> {
    return localDataSource.getAllCity()
  }

  override suspend fun getCountry(countryCode: String): Country {
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

    throw AppException(getLocalMessage(NoCountryFound))
  }

  override suspend fun saveCountry(countryEntity: Country) {
    return localDataSource.saveCountry(countryEntity)
  }

  override fun getStreamIsDBInitialized(): Observable<Boolean> {
    return localDataSource.getStreamIsDBInitialized()
  }

  override suspend fun getAllCityWeather(): List<CityWeather> {
    return localDataSource.getAllCityWeather()
  }

  override suspend fun addCityWeather(cityEntity: City): CityWeather {
    return localDataSource.addCityWeather(cityEntity)
  }

  override suspend fun deleteCityWeather(cityWeatherEntity: CityWeather) {
    return localDataSource.deleteCityWeather(cityWeatherEntity)
  }

  override suspend fun getCurrentWeather(cityWeather: CityWeather): CurrentWeatherModel {
    return remoteDataSource.getCurrentWeather(cityWeather)
  }

  override suspend fun getForecast(cityWeather: CityWeather): ForecastModel {
    return remoteDataSource.getForecast(cityWeather)
  }

  override fun getLocalMessage(localMessage: LocalMessage): String {
    return localDataSource.getLocalMessage(localMessage)
  }
}
