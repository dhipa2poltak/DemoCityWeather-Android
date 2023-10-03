package com.dpfht.android.democityweather.feature_city_weather.di

import android.content.Context
import com.dpfht.android.democityweather.feature_city_weather.view.details.DetailsCityWeatherFragment
import com.dpfht.android.democityweather.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface DetailsCityWeatherComponent {

  fun inject(detailsCityWeatherFragment: DetailsCityWeatherFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): DetailsCityWeatherComponent
  }
}