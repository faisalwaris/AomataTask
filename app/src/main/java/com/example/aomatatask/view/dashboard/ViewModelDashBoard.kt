package com.example.aomatatask.view.dashboard

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.aomatatask.base.BaseViewModel
import com.example.aomatatask.model.appModel.OneShotEvent
import com.example.aomatatask.network.apiCall.NetworkRepository
import com.example.aomatatask.network.networkApiResponse.PixabayPhotoResponse
import com.example.aomatatask.network.networkApiResponse.PixabayPhotosParentResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ViewModelDashBoard @Inject constructor(private val networkRepository: NetworkRepository) :
    BaseViewModel() {
    var pixabayImagesList: MutableLiveData<OneShotEvent<List<PixabayPhotoResponse>>> =
        MutableLiveData()

    init {
        fetchImages()
    }

    fun fetchImages() {
        viewModelScope.launch {
            showProgressBar()
            val networkResponse = networkRepository.getPixabayImages()
            hideProgressBar()
            if (isResponseSuccess(networkResponse)) {
                pixabayImagesList.value =
                    OneShotEvent((networkResponse as PixabayPhotosParentResponse).hits)
            }
        }
    }
}