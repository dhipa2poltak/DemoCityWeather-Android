package com.dpfht.democityweather.domain.entity.vw_entity

data class ForecastHourlyVWEntity(
  val strTime: String = "",
  val description: String = "",
  var animationId: Int = -1,
  val strTemperature: String = ""
)
