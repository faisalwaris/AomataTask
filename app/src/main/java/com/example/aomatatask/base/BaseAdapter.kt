package com.example.aomatatask.base

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter constructor(
    private val layoutId: Int,
    private var itemList: List<Any>?
) :
    RecyclerView.Adapter<BaseAdapter.BaseViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder {
        return BaseViewHolder(LayoutInflater.from(parent.context).inflate(layoutId, parent, false))
    }

    override fun onBindViewHolder(holder: BaseViewHolder, position: Int) {
        onBind(holder, holder.adapterPosition)
    }

    override fun getItemCount(): Int {
        return itemList?.size!!
    }

    class BaseViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    abstract fun onBind(holder: BaseViewHolder, position: Int)


}