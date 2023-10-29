package com.dpfht.democityweather.domain.entity.vw_entity

data class ForecastVWEntity(
  val hourlyEntities: List<ForecastHourlyVWEntity> = listOf(),
  val weeklyEntities: List<ForecastWeeklyVWEntity> = listOf()
)
