package com.dpfht.democityweather.domain.model

sealed class LocalMessage {
  object ErrorWhenGettingForecastData: LocalMessage()
  object GeneralError: LocalMessage()
  object NoCountryFound: LocalMessage()
}
