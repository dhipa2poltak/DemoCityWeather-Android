package com.dpfht.android.democityweather.domain.entity

data class CurrentWeatherDomain(
  val weathers: List<WeatherEntity> = listOf(),
  val main: MainEntity? = null,
  val wind: WindEntity? = null,
  val name: String = ""
)
