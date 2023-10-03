package com.dpfht.android.democityweather.domain.entity

data class MainEntity(
  val temp: Double = 0.0,
  val feelsLike: Double = 0.0,
  val tempMin: Double = 0.0,
  val tempMax: Double = 0.0,
  val pressure: Double = 0.0,
  val humidity: Double = 0.0,
  val seaLevel: Double = 0.0,
  val groundLevel: Double = 0.0
)
