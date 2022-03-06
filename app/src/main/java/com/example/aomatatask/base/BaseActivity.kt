package com.example.aomatatask.base

import android.app.AlertDialog
import android.content.Context
import android.graphics.Point
import android.graphics.drawable.ColorDrawable
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkRequest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.example.aomatatask.R

abstract class BaseActivity : AppCompatActivity() {
    private var progressBar: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setBinding()
        initView()
        initClickListener()
        attachViewModel()
    }


    open fun setBinding() {}
    open fun initView() {}
    open fun initClickListener() {}
    open fun attachViewModel() {}

    fun getProgressDialog(): AlertDialog {
        if (progressBar == null) {
            val progressDialogBuilder = AlertDialog.Builder(this@BaseActivity)
            val inflater = getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
            val dialogView = inflater.inflate(R.layout.progress_dialog, null)
            progressDialogBuilder.setView(dialogView)
            progressBar = progressDialogBuilder.create()
            with(progressBar!!) {
                window!!.setBackgroundDrawable(ColorDrawable(android.graphics.Color.TRANSPARENT))
                setCancelable(false)
                window?.setBackgroundDrawableResource(android.R.color.transparent)
            }
            val size = Point()
            progressBar?.window?.windowManager?.defaultDisplay?.getSize(size)
            progressBar?.window?.setLayout(
                (size.x * 0.8).toInt(),
                WindowManager.LayoutParams.WRAP_CONTENT
            )
        }
        return progressBar!!
    }


}