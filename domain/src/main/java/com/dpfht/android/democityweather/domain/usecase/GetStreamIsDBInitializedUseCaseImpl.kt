package com.dpfht.android.democityweather.domain.usecase

import com.dpfht.android.democityweather.domain.repository.AppRepository
import io.reactivex.rxjava3.core.Observable

class GetStreamIsDBInitializedUseCaseImpl(
  private val appRepository: AppRepository
): GetStreamIsDBInitializedUseCase {

  override operator fun invoke(): Observable<Boolean> {
    return appRepository.getStreamIsDBInitialized()
  }
}
