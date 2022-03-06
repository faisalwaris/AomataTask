package com.example.aomatatask.view.activity

import android.view.View
import com.example.aomatatask.base.BaseActivity
import com.example.aomatatask.databinding.ActivitySingleBinding
import com.example.aomatatask.databinding.MainActionbarBinding
import com.example.aomatatask.util.gone
import com.example.aomatatask.util.visible
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SingleActivity : BaseActivity() {
    private lateinit var binding: ActivitySingleBinding
    private lateinit var actionBar: MainActionbarBinding

    override fun setBinding() {
        super.setBinding()
        binding = ActivitySingleBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    override fun initView() {
        actionBar = binding.actionBar
    }

    override fun initClickListener() {
    }

    fun getGridCellView(): View = actionBar.gridSelection

    fun getImageSelectionView(): View = actionBar.imgSelectionChoice

    fun setTopTitle(title: String) {
        actionBar.root.visible()
        actionBar.title.text = title
    }

    fun hideDefaultActionBar() {
        actionBar.root.gone()
    }
}