package com.dpfht.android.democityweather.framework.commons.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

open class BaseBottomSheetDialogFragment<VDB: ViewDataBinding>(
  @LayoutRes protected val contentLayouId: Int
): BottomSheetDialogFragment() {

  protected lateinit var binding: VDB

  override fun onCreateView(
    inflater: LayoutInflater,
    container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = DataBindingUtil.inflate(inflater, contentLayouId, container, false)

    return binding.root
  }
}
