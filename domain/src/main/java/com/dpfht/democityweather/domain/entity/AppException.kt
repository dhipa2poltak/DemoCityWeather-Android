package com.dpfht.democityweather.domain.entity

class AppException(
    override val message: String = ""
): Exception(message)
