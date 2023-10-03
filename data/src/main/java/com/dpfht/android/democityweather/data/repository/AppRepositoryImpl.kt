package com.dpfht.android.democityweather.data.repository

import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.data.datasource.LocalDataSource
import com.dpfht.android.democityweather.data.datasource.RemoteDataSource
import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.VoidResult
import com.dpfht.android.democityweather.domain.repository.AppRepository
import io.reactivex.rxjava3.core.Observable

class AppRepositoryImpl(
  private val localDataSource: LocalDataSource,
  private val remoteDataSource: RemoteDataSource
): AppRepository {

  override suspend fun getAllCity(): Result<List<CityEntity>> {
    return localDataSource.getAllCity()
  }

  override suspend fun getCountry(countryCode: String): Result<CountryEntity> {
    when (val result = localDataSource.getCountry(countryCode)) {
      is Result.Success -> {
        val list = result.value
        if (list.isNotEmpty()) {
          return Result.Success(list.first())
        }
      }
      is Result.ErrorResult -> {
        return result
      }
    }

    when (val result = remoteDataSource.getCountryInfo(countryCode)) {
      is Result.Success -> {
        val list = result.value
        if (list.isNotEmpty()) {
          val countryEntity = list.first()
          saveCountry(countryEntity)

          return Result.Success(countryEntity)
        }
      }
      is Result.ErrorResult -> {
        return result
      }
    }

    return Result.ErrorResult("no country found")
  }

  override suspend fun saveCountry(countryEntity: CountryEntity): VoidResult {
    return localDataSource.saveCountry(countryEntity)
  }

  override fun getStreamIsDBInitialized(): Observable<Boolean> {
    return localDataSource.getStreamIsDBInitialized()
  }

  override suspend fun getAllCityWeather(): Result<List<CityWeatherEntity>> {
    return localDataSource.getAllCityWeather()
  }

  override suspend fun addCityWeather(cityEntity: CityEntity): Result<CityWeatherEntity> {
    return localDataSource.addCityWeather(cityEntity)
  }

  override suspend fun deleteCityWeather(cityWeatherEntity: CityWeatherEntity): VoidResult {
    return localDataSource.deleteCityWeather(cityWeatherEntity)
  }

  override suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain> {
    return remoteDataSource.getCurrentWeather(cityWeather)
  }

  override suspend fun getForecast(cityWeather: CityWeatherEntity): Result<ForecastDomain> {
    return remoteDataSource.getForecast(cityWeather)
  }
}
