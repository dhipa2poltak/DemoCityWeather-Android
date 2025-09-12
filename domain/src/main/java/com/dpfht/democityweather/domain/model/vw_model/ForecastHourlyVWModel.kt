package com.dpfht.democityweather.domain.model.vw_model

data class ForecastHourlyVWModel(
  val strTime: String = "",
  val description: String = "",
  var animationId: Int = -1,
  val strTemperature: String = ""
)
