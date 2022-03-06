package com.example.aomatatask.base

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

abstract class BaseFragment(view: Int) : Fragment(view), View.OnClickListener {
    protected var progressDialog: AlertDialog? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        progressDialog = (requireActivity() as BaseActivity).getProgressDialog()
        initView()
        initClickListener()
        attachViewModel()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        setViewFromActivity()
    }

    abstract fun initView()
    abstract fun initClickListener()
    abstract fun attachViewModel()
    open fun setViewFromActivity() {}

    fun setViewModel(viewModel: BaseViewModel) {
        with(viewModel) {
            //observer for error
            networkErrorResponse.observe(viewLifecycleOwner) {
                it.getData()?.let {
                    showSnakeBar(it.message)
                }
            }

            //observer for progress bar
            progressBar.observe(viewLifecycleOwner) {
                it.getData()?.let {
                    if (it) {
                        progressDialog?.show()
                    } else {
                        progressDialog?.dismiss()
                    }
                }
            }

        }
    }

    fun showSnakeBar(message: String?) {
        message?.let { msg ->
            Snackbar.make(requireView(), msg, Snackbar.LENGTH_SHORT).show()
        }
    }

}