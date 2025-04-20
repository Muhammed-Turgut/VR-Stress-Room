package com.VrStressRoom.vrstressroom.CameraTestScreen.PhotoAISend

import okhttp3.MultipartBody
import retrofit2.Response
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

interface CameraAIService {
    @Multipart
    @POST("/chat")
    suspend fun sendImage(
        @Part image: MultipartBody.Part
    ): Response<CameraServiceModel>
}