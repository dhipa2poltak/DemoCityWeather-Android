package com.dpfht.democityweather.domain.model

data class City(
  val idCity: Long = 0L,
  val countryCode: String = "",
  val cityName: String = "",
  val lat: Double = 0.0,
  val lon: Double = 0.0
)
