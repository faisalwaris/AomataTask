package com.example.aomatatask.base

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.fragment.app.DialogFragment

abstract class BaseDialogFragment(view: Int): DialogFragment(view) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
        initClickListener()
    }

    abstract fun initView()
    abstract fun initClickListener()

}