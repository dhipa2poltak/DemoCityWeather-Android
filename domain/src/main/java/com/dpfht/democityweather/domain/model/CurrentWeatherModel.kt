package com.dpfht.democityweather.domain.model

data class CurrentWeatherModel(
  val weathers: List<Weather> = listOf(),
  val main: Main? = null,
  val wind: Wind? = null,
  val name: String = ""
)
