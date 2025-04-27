package com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork

data class StressRequest(
    val age: Int,
    val gender: String,
    val emotion: String,
    val stressAnswers: List<Int>
)

data class StressResponse(
    val stress_score: Int,
    val stress_percentage: Double,
    val stress_level: String,
    val advice: String
)