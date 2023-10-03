package com.dpfht.android.democityweather.domain.entity.vw_entity

data class ForecastVWEntity(
  val hourlyEntities: List<ForecastHourlyVWEntity> = listOf(),
  val weeklyEntities: List<ForecastWeeklyVWEntity> = listOf()
)
