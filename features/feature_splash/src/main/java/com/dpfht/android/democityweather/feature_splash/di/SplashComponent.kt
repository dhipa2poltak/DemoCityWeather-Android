package com.dpfht.android.democityweather.feature_splash.di

import android.content.Context
import com.dpfht.android.democityweather.feature_splash.view.SplashFragment
import com.dpfht.android.democityweather.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface SplashComponent {

  fun inject(splashFragment: SplashFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): SplashComponent
  }
}
