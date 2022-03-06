package com.example.aomatatask.view.dashboard

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RadioGroup
import com.example.aomatatask.R
import com.example.aomatatask.base.BaseDialogFragment
import com.example.aomatatask.databinding.AlertImageSelectionBinding

class ImageSelectionAlert(
    private val listener: (String?) -> Unit
) :
    BaseDialogFragment(R.layout.alert_image_selection),
    View.OnClickListener, RadioGroup.OnCheckedChangeListener {
    private lateinit var binding: AlertImageSelectionBinding
    private lateinit var selectedCategory: String

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
        binding = AlertImageSelectionBinding.bind(requireView())

    }

    override fun initClickListener() {
        with(binding) {
            actionButton.setOnClickListener(this@ImageSelectionAlert)
            connectionTypeGroup.setOnCheckedChangeListener(this@ImageSelectionAlert)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.actionButton -> {
                dismiss()
                listener.invoke(selectedCategory)
            }
        }
    }

    override fun onCheckedChanged(group: RadioGroup?, checkedId: Int) {
        when (checkedId) {
            binding.internet.id -> {
                selectedCategory = getString(R.string.internet)
            }

            binding.gallery.id -> {
                selectedCategory = getString(R.string.gallery)
            }

        }
    }
}