package com.example.aomatatask.util

import android.view.View
import android.widget.ImageView
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.example.aomatatask.R
import com.example.aomatatask.databinding.NoDataFoundBinding
import com.google.android.material.snackbar.Snackbar

fun View.showSnackBarMessage(msg: String) {
    Snackbar.make(this, msg, Snackbar.LENGTH_SHORT).show()
}


fun ImageView.loadImg(
    url: String? = null,
    placeHolder: Int = R.drawable.img_placeholder,
    resourceId: Int? = null
) {
    /*  val circularProgressDrawable = context?.let { CircularProgressDrawable(it) }
      circularProgressDrawable?.apply {
          strokeWidth = 10f
          centerRadius = 50f
      }*/
    context?.color(R.color.red_orange)/*
            ?.let { circularProgressDrawable?.setColorSchemeColors(it) }
        circularProgressDrawable?.start()*/

    /* var requestOptions = RequestOptions()
     requestOptions.apply {
         placeholder(circularProgressDrawable)
         override(480, 320)
         error(placeHolder)
     }*/
    var loadImgUrl = url.takeIf { resourceId == null } ?: resourceId
    Glide
        .with(this.context)
        // .setDefaultRequestOptions(requestOptions)
        .load(loadImgUrl)
        //  .placeholder(circularProgressDrawable)
        .error(R.drawable.img_placeholder)
        .into(this)
}


fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

fun NoDataFoundBinding.showHideNoData(
    title: String,
    imgId: Int,
    listCount: Int? = 0,
    description: String = ""
) {
    if (listCount == null || listCount == 0) {
        root.visible()
        noDataTitle.text = title
        noDataDescription.text = description
        noDataIcon.setImgDrawable(imgId)
    } else {
        root.gone()
    }
}


fun ImageView.setImgDrawable(drawableImg: Int) {
    this.setImageDrawable(ContextCompat.getDrawable(context, drawableImg))
}
