package com.dpfht.android.democityweather.framework.data.datasource.remote

import com.dpfht.android.democityweather.data.datasource.RemoteDataSource
import com.dpfht.android.democityweather.data.model.remote.response.toDomain
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.RestService
import com.dpfht.android.democityweather.framework.data.datasource.remote.rest.safeApiCall
import kotlinx.coroutines.Dispatchers

class RemoteDataSourceImpl(
  private val restService: RestService
): RemoteDataSource {

  override suspend fun getCountryInfo(countryCode: String): Result<List<CountryEntity>> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getCountry(countryCode) }) {
      is Result.Success -> {
        val list = result.value.map { CountryEntity(countryCode = countryCode, countryName = it.name?.common ?: "") }

        Result.Success(list)
      }
      is Result.ErrorResult -> {
        result
      }
    }
  }

  override suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getCurrentWeather(cityWeather.lat, cityWeather.lon) }) {
      is Result.Success -> {
        Result.Success(result.value.toDomain())
      }
      is Result.ErrorResult -> {
        result
      }
    }
  }

  override suspend fun getForecast(cityWeather: CityWeatherEntity): Result<ForecastDomain> {
    return when (val result = safeApiCall(Dispatchers.IO) { restService.getForecast(cityWeather.lat, cityWeather.lon) }) {
      is Result.Success -> {
        Result.Success(result.value.toDomain())
      }
      is Result.ErrorResult -> {
        result
      }
    }
  }
}
