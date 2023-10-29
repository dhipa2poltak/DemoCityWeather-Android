package com.dpfht.democityweather.domain.entity

import java.io.Serializable

data class CityWeatherEntity(
  val id: Long = 0L,
  val idCity: Long = 0L,
  val countryCode: String = "",
  val cityName: String = "",
  val lat: Double = 0.0,
  val lon: Double = 0.0
): Serializable
