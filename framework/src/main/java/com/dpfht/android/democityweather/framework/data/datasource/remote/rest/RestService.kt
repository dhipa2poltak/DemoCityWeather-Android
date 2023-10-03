package com.dpfht.android.democityweather.framework.data.datasource.remote.rest

import com.dpfht.android.democityweather.data.model.remote.response.CountryInfo
import com.dpfht.android.democityweather.data.model.remote.response.CurrentWeatherResponse
import com.dpfht.android.democityweather.data.model.remote.response.ForecastResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface RestService {

  @GET("https://restcountries.com/v3.1/alpha/{country_code}")
  suspend fun getCountry(@Path("country_code") countryCode: String): List<CountryInfo>

  @GET("weather")
  suspend fun getCurrentWeather(
    @Query("lat") lat: Double,
    @Query("lon") lon: Double
  ): CurrentWeatherResponse

  @GET("forecast")
  suspend fun getForecast(
    @Query("lat") lat: Double,
    @Query("lon") lon: Double
  ): ForecastResponse
}
