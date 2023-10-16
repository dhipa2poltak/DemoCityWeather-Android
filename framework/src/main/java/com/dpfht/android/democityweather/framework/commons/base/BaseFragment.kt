package com.dpfht.android.democityweather.framework.commons.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import com.dpfht.android.democityweather.framework.commons.base.di.DaggerBaseComponent
import com.dpfht.android.democityweather.framework.di.dependency.NavigationServiceDependency
import com.dpfht.android.democityweather.framework.navigation.NavigationService
import dagger.hilt.android.EntryPointAccessors
import javax.inject.Inject

open class BaseFragment<VDB: ViewDataBinding>(
  @LayoutRes protected val contentLayouId: Int
): Fragment() {

  protected lateinit var binding: VDB

  @Inject
  protected lateinit var navigationService: NavigationService

  override fun onAttach(context: Context) {
    super.onAttach(context)

    DaggerBaseComponent.builder()
      .context(requireContext())
      .navDependency(EntryPointAccessors.fromActivity(requireActivity(), NavigationServiceDependency::class.java))
      .build()
      .inject(this as BaseFragment<ViewDataBinding>)
  }

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, contentLayouId, container, false)

    return binding.root
  }
}
