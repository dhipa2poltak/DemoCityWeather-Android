package com.dpfht.democityweather.domain.model.vw_model

data class ForecastVWModel(
  val hourlyEntities: List<ForecastHourlyVWModel> = listOf(),
  val weeklyEntities: List<ForecastWeeklyVWModel> = listOf()
)
