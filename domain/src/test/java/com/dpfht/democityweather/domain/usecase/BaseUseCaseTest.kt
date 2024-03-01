package com.dpfht.democityweather.domain.usecase

import com.dpfht.democityweather.domain.repository.AppRepository
import org.mockito.Mock

open class BaseUseCaseTest {

  @Mock
  protected lateinit var appRepository: AppRepository
}
