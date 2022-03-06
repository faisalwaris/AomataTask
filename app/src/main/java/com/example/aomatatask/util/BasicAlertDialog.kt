package com.example.aomatatask.util

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import com.example.aomatatask.R
import com.example.aomatatask.base.BaseDialogFragment
import com.example.aomatatask.databinding.BasicAlertDialogBinding

class BasicAlertDialog(private val listener: (ActionButtonClicked) -> Unit) :
    BaseDialogFragment(R.layout.basic_alert_dialog), View.OnClickListener {
    private lateinit var binding: BasicAlertDialogBinding
    private lateinit var alertDataModel: BasicAlertDialogModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.AppBottomSheetDialogTheme)
    }


    fun setAlertData(alertDataModel: BasicAlertDialogModel) {
        this.alertDataModel = alertDataModel
    }

    override fun onStart() {
        super.onStart()
        val width = ViewGroup.LayoutParams.WRAP_CONTENT
        val height = ViewGroup.LayoutParams.WRAP_CONTENT
        dialog?.window?.setLayout(width, height)
    }


    override fun initView() {
        binding = BasicAlertDialogBinding.bind(requireView())
        isCancelable = false
        with(binding) {
            alertDataModel.let {
                if (it.imgIcon != null) {
                    icon.visible()
                    icon.setImageResource(alertDataModel.imgIcon!!)
                } else {
                    icon.gone()
                }

                title.text = alertDataModel.title

                if (it.description != null) {
                    description.visible()
                    description.text = it.description
                } else {
                    description.gone()
                }

                if (it.positiveButtonTitle != null) {
                    positiveButton.visible()
                    positiveButton.text = it.positiveButtonTitle
                } else {
                    positiveButton.gone()
                }

                if (it.negativeButtonTitle != null) {
                    negativeButton.visible()
                    negativeButton.text = it.negativeButtonTitle
                } else {
                    negativeButton.gone()
                }
            }
        }

    }

    override fun initClickListener() {
        with(binding) {
            negativeButton.setOnClickListener(this@BasicAlertDialog)
            positiveButton.setOnClickListener(this@BasicAlertDialog)
        }
    }

    override fun onClick(v: View?) {
        when (v) {
            binding.negativeButton -> {
                dismiss()
            }
            binding.positiveButton -> {
                dismiss()
                listener.invoke(ActionButtonClicked.POSITIVE_BUTTON)
            }
        }
    }

    enum class ActionButtonClicked {
        POSITIVE_BUTTON,
    }
}