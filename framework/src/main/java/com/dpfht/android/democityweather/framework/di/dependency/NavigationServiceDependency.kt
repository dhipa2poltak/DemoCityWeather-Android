package com.dpfht.android.democityweather.framework.di.dependency

import com.dpfht.android.democityweather.framework.navigation.NavigationService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent

@EntryPoint
@InstallIn(ActivityComponent::class)
interface NavigationServiceDependency {

  fun getNavigationService(): NavigationService
}
