package com.dpfht.android.democityweather.domain.entity.vw_entity
data class ForecastWeeklyVWEntity(
  val day: String = "",
  var minTemperature: Double = -999.0,
  var maxTemperature: Double = -999.0,
  var animationId: Int = -1,
  val mapDesc: MutableMap<String, Int> = mutableMapOf()
)
