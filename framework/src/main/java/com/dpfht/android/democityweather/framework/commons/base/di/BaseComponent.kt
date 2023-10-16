package com.dpfht.android.democityweather.framework.commons.base.di

import android.content.Context
import androidx.databinding.ViewDataBinding
import com.dpfht.android.democityweather.framework.commons.base.BaseFragment
import com.dpfht.android.democityweather.framework.di.dependency.NavigationServiceDependency
import dagger.BindsInstance
import dagger.Component

@Component(dependencies = [NavigationServiceDependency::class])
interface BaseComponent {

  fun inject(baseFragment: BaseFragment<ViewDataBinding>)

  @Component.Builder
  interface Builder {
    fun context(@BindsInstance context: Context): Builder
    fun navDependency(navigationServiceDependency: NavigationServiceDependency): Builder
    fun build(): BaseComponent
  }
}
