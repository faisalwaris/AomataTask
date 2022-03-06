package com.example.aomatatask.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aomatatask.model.appModel.OneShotEvent
import com.example.aomatatask.network.networkApiResponse.NetworkBaseResponse
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL
import javax.net.ssl.HttpsURLConnection

abstract class BaseViewModel : ViewModel() {
    var progressBar: MutableLiveData<OneShotEvent<Boolean>> = MutableLiveData()
    var networkErrorResponse: MutableLiveData<OneShotEvent<NetworkBaseResponse>> = MutableLiveData()

    fun showProgressBar() {
        progressBar.value = OneShotEvent(true)
    }

    fun hideProgressBar() {
        progressBar.value = OneShotEvent(false)
    }

    fun isResponseSuccess(networkBaseResponse: NetworkBaseResponse): Boolean {
        return if (networkBaseResponse.statusCode == null) {
            true
        } else {
            networkErrorResponse.value = OneShotEvent(networkBaseResponse)
            false
        }
    }
}