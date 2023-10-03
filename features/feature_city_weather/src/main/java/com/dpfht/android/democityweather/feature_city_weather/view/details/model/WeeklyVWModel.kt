package com.dpfht.android.democityweather.feature_city_weather.view.details.model

data class WeeklyVWModel(
  val day: String = "",
  var minTemperature: Double = -999.0,
  var maxTemperature: Double = -999.0,
  var animationId: Int = -1,
  val mapDesc: MutableMap<String, Int> = mutableMapOf()
)
