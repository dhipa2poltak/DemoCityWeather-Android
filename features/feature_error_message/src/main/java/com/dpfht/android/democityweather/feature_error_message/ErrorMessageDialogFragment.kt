package com.dpfht.android.democityweather.feature_error_message

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.dpfht.android.democityweather.feature_error_message.databinding.FragmentErrorMessageDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class ErrorMessageDialogFragment : BottomSheetDialogFragment() {

  private lateinit var binding: FragmentErrorMessageDialogBinding

  override fun onCreateView(
    inflater: LayoutInflater, container: ViewGroup?,
    savedInstanceState: Bundle?
  ): View {
    binding = FragmentErrorMessageDialogBinding.inflate(inflater, container, false)

    return binding.root
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    super.onViewCreated(view, savedInstanceState)

    arguments?.let {
      val message = it.getString("message")

      binding.tvMessage.text = message
    }

    setListener()
  }

  private fun setListener() {
    binding.btnOk.setOnClickListener {
      dismiss()
    }
  }
}
