package com.dpfht.democityweather.domain.model

data class Forecast(
  val dt: Long = 0L,
  val main: Main? = null,
  val weathers: List<Weather> = listOf(),
  val wind: Wind? = null,
  val dtTxt: String = ""
)
