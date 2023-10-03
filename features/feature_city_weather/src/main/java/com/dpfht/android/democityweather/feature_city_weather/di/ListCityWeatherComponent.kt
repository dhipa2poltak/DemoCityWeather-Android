package com.dpfht.android.democityweather.feature_city_weather.di

import android.content.Context
import com.dpfht.android.democityweather.feature_city_weather.view.list.ListCityWeatherFragment
import com.dpfht.android.democityweather.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface ListCityWeatherComponent {

  fun inject(listCityWeatherFragment: ListCityWeatherFragment)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): ListCityWeatherComponent
  }
}
