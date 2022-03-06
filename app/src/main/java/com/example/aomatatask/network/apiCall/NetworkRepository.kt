package com.example.aomatatask.network.apiCall

import com.example.aomatatask.network.networkApiResponse.NetworkBaseResponse
import com.example.aomatatask.network.networkApiResponse.PixabayPhotosParentResponse
import com.example.aomatatask.util.AppConstant
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class NetworkRepository @Inject constructor(private val apiInterface: ApiInterface) {
    private val dispatcher = Dispatchers.IO


    suspend fun getPixabayImages(): NetworkBaseResponse {
        return withContext(dispatcher) {
            try {
                apiInterface.getPixabayImages(
                    accountKey = AppConstant.PIXABAY_KEY,
                    query = AppConstant.QUERY_IMAGE_QUERY,
                    imageType = AppConstant.QUERY_IMAGE_TYPE,
                    quality = "true"
                )
            } catch (exception: Throwable) {
                NetworkErrorParser.parseNetworkError(exception)

            }
        }
    }
}