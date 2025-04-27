package com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object StressTestRetrofit {
    private const val BASE_URL = "https://koesan-piskochatboot.hf.space/"
    val instance: StressApi by lazy {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        retrofit.create(StressApi::class.java)
    }
}
