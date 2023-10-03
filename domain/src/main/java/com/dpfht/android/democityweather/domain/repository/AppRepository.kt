package com.dpfht.android.democityweather.domain.repository

import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.CurrentWeatherDomain
import com.dpfht.android.democityweather.domain.entity.ForecastDomain
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.entity.VoidResult
import io.reactivex.rxjava3.core.Observable

interface AppRepository {

  suspend fun getAllCity(): Result<List<CityEntity>>
  suspend fun getCountry(countryCode: String): Result<CountryEntity>
  suspend fun saveCountry(countryEntity: CountryEntity): VoidResult
  fun getStreamIsDBInitialized(): Observable<Boolean>
  suspend fun getAllCityWeather(): Result<List<CityWeatherEntity>>
  suspend fun addCityWeather(cityEntity: CityEntity): Result<CityWeatherEntity>
  suspend fun deleteCityWeather(cityWeatherEntity: CityWeatherEntity): VoidResult
  suspend fun getCurrentWeather(cityWeather: CityWeatherEntity): Result<CurrentWeatherDomain>
  suspend fun getForecast(cityWeather: CityWeatherEntity): Result<ForecastDomain>
}
