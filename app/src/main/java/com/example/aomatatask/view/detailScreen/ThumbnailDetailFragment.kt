package com.example.aomatatask.view.detailScreen

import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.findNavController
import com.example.aomatatask.R
import com.example.aomatatask.base.BaseFragment
import com.example.aomatatask.databinding.FragmentDetailThumbnailBinding
import com.example.aomatatask.util.loadImg
import com.example.aomatatask.view.activity.SingleActivity

class ThumbnailDetailFragment :
    BaseFragment(R.layout.fragment_detail_thumbnail) {
    private lateinit var binding: FragmentDetailThumbnailBinding
    private lateinit var thumbUrl: String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(ThumbnailDetailFragmentArgs.fromBundle(requireArguments())) {
            thumbUrl = this.thumbnailUrl
        }

        binding.thumbnailImg.loadImg(thumbUrl)

    }

    override fun initView() {
        binding = FragmentDetailThumbnailBinding.bind(requireView())
        (requireActivity() as SingleActivity).hideDefaultActionBar()
    }

    override fun initClickListener() {
        with(binding) {
            backArrow.setOnClickListener(this@ThumbnailDetailFragment)
        }
    }

    override fun attachViewModel() {

    }

    override fun onClick(v: View?) {
        when (v) {
            binding.backArrow -> {
                findNavController().popBackStack()
            }
        }
    }

}