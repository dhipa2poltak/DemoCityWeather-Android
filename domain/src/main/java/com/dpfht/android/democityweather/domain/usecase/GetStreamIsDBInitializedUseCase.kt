package com.dpfht.android.democityweather.domain.usecase

import io.reactivex.rxjava3.core.Observable

interface GetStreamIsDBInitializedUseCase {

  operator fun invoke(): Observable<Boolean>
}
