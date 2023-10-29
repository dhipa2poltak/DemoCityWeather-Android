package com.dpfht.democityweather.domain.entity

data class WeatherEntity(
  val id: Long = 0L,
  val main: String = "",
  val description: String = "",
  val icon: String = ""
)
