package com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface StressApi {
    @Headers("Content-Type: application/json")
    @POST("stress_evaluation")
    fun evaluateStress(@Body request: StressRequest): Call<StressResponse> // Buradaki `Any` yerine response modelin varsa onu koyabilirsin.
}
