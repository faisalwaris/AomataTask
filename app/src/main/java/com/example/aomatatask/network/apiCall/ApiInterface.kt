package com.example.aomatatask.network.apiCall

import com.example.aomatatask.network.networkApiResponse.PixabayPhotosParentResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("api")
    suspend fun getPixabayImages(
        @Query("key") accountKey: String,
        @Query("q") query: String,
        @Query("image_type") imageType: String,
        @Query("pretty") quality: String

    ): PixabayPhotosParentResponse
}