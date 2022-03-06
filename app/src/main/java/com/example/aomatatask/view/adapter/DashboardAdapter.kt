package com.example.aomatatask.view.adapter

import com.example.aomatatask.R
import com.example.aomatatask.base.BaseAdapter
import com.example.aomatatask.databinding.ItemDashboardBinding
import com.example.aomatatask.network.networkApiResponse.PixabayPhotoResponse
import com.example.aomatatask.util.loadImg

class DashboardAdapter(
    private var galleryContentList: MutableList<PixabayPhotoResponse>,
    private var dashBoardActivityLamda: (selectedIndex: Int) -> Unit
) : BaseAdapter(R.layout.item_dashboard, galleryContentList) {

    private lateinit var binding: ItemDashboardBinding

    override fun onBind(holder: BaseViewHolder, position: Int) {
        binding = ItemDashboardBinding.bind(holder.view)
        val mediaUrl = galleryContentList[position]

        with(binding) {
            thumbnail.loadImg(mediaUrl.previewURL)

            holder.itemView.setOnClickListener {
                dashBoardActivityLamda.invoke(position)
            }
        }
    }
}