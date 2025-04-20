package com.VrStressRoom.vrstressroom.CameraTestScreen.PhotoAISend

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object CameraBotRetrofitInstances {
    private const val BASE_URL = "https://koesan-piskochatboot.hf.space/"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val api: CameraAIService by lazy {
        retrofit.create(CameraAIService::class.java)
    }
}