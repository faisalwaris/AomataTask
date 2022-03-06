package com.example.aomatatask.network.networkApiResponse

data class PixabayPhotosParentResponse(
    val hits: List<PixabayPhotoResponse>?
): NetworkBaseResponse()