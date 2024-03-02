package com.dpfht.democityweather.domain.entity

sealed class LocalMessage {
  object ErrorWhenGettingForecastData: LocalMessage()
  object GeneralError: LocalMessage()
  object NoCountryFound: LocalMessage()
}
