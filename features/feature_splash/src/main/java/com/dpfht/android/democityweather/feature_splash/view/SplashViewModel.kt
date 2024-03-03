package com.dpfht.android.democityweather.feature_splash.view

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.dpfht.android.democityweather.framework.Constants
import com.dpfht.democityweather.domain.usecase.GetStreamIsDBInitializedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
  private val getStreamIsDBInitializedUseCase: GetStreamIsDBInitializedUseCase
): ViewModel() {

  private val _isInitializationDone = MutableLiveData<Boolean>()
  val isInitializationDone: LiveData<Boolean> = _isInitializationDone

  var isDelayDone = false
  private var isInitDBDone = false

  private val compositeDisposable = CompositeDisposable()

  fun start() {
    viewModelScope.launch {
      delay(Constants.DELAY_SPLASH)
      isDelayDone = true
      _isInitializationDone.postValue(isInitDBDone)
    }

    compositeDisposable.add(
      getStreamIsDBInitializedUseCase()
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe { isDone ->
          isInitDBDone = isDone
          _isInitializationDone.postValue(isDelayDone && isInitDBDone)
        }
    )
  }
}
