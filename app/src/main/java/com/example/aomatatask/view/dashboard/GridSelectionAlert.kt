package com.example.aomatatask.view.dashboard

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.example.aomatatask.R
import com.example.aomatatask.base.BaseDialogFragment
import com.example.aomatatask.databinding.AlertGridSelectionBinding
import com.example.aomatatask.util.showSnackBarMessage


class GridSelectionAlert(
    private val listener: (Int) -> Unit
) :
    BaseDialogFragment(R.layout.alert_grid_selection),
    View.OnClickListener {
    private lateinit var binding: AlertGridSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.WRAP_CONTENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }


    override fun initView() {
        binding = AlertGridSelectionBinding.bind(requireView())
    }

    override fun initClickListener() {
        with(binding) {
            submit.setOnClickListener(this@GridSelectionAlert)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.submit -> {
                val imm: InputMethodManager =
                    requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
                imm.hideSoftInputFromWindow(binding.reason.windowToken, 0)

                if (binding.reason.text.isNullOrEmpty()) {
                    requireView().showSnackBarMessage(getString(R.string.please_enter_cell_number))
                    return
                } else if (binding.reason.text.toString().toInt() == 0) {
                    requireView().showSnackBarMessage(getString(R.string.invalid_number))
                    return
                }
                dismiss()
                listener.invoke(binding.reason.text.toString().toInt())
            }
        }
    }

}