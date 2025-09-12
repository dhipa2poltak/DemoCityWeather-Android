package com.dpfht.democityweather.domain.model

class AppException(
    override val message: String = ""
): Exception(message)
