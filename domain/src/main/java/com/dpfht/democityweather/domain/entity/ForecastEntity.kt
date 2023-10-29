package com.dpfht.democityweather.domain.entity

data class ForecastEntity(
  val dt: Long = 0L,
  val main: MainEntity? = null,
  val weathers: List<WeatherEntity> = listOf(),
  val wind: WindEntity? = null,
  val dtTxt: String = ""
)
