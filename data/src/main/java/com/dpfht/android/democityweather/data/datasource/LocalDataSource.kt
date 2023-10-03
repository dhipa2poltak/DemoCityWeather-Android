package com.dpfht.android.democityweather.data.datasource

import com.dpfht.android.democityweather.domain.entity.CityEntity
import com.dpfht.android.democityweather.domain.entity.CityWeatherEntity
import com.dpfht.android.democityweather.domain.entity.CountryEntity
import com.dpfht.android.democityweather.domain.entity.Result
import com.dpfht.android.democityweather.domain.entity.VoidResult
import io.reactivex.rxjava3.core.Observable

interface LocalDataSource {

  suspend fun getAllCity(): Result<List<CityEntity>>
  suspend fun getCountry(countryCode: String): Result<List<CountryEntity>>
  suspend fun saveCountry(countryEntity: CountryEntity): VoidResult
  fun getStreamIsDBInitialized(): Observable<Boolean>
  suspend fun getAllCityWeather(): Result<List<CityWeatherEntity>>
  suspend fun addCityWeather(cityEntity: CityEntity): Result<CityWeatherEntity>
  suspend fun deleteCityWeather(cityWeatherEntity: CityWeatherEntity): VoidResult
}
