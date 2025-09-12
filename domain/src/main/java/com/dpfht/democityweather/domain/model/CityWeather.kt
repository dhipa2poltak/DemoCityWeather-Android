package com.dpfht.democityweather.domain.model

import java.io.Serializable

data class CityWeather(
  val id: Long = 0L,
  val idCity: Long = 0L,
  val countryCode: String = "",
  val cityName: String = "",
  val lat: Double = 0.0,
  val lon: Double = 0.0
): Serializable
