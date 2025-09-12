package com.dpfht.democityweather.domain.model

data class Weather(
  val id: Long = 0L,
  val main: String = "",
  val description: String = "",
  val icon: String = ""
)
