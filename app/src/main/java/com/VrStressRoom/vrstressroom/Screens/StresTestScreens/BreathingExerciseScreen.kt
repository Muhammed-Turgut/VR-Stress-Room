package com.VrStressRoom.vrstressroom.Screens.StresTestScreens


import android.content.Context
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.VrStressRoom.vrstressroom.R
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import app.rive.runtime.kotlin.RiveAnimationView



@Composable
fun BreathingExerciseScreen(navController: NavController, context: Context){

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){

        RiveAnimationComposable()
    }
}

@Composable
fun RiveAnimationComposable() {
    AndroidView(factory = { context ->
        RiveAnimationView(context).apply {
            setRiveResource(R.raw.breathing_animationtre)
            play()
        }
    })
}

@Preview(showBackground = true)
@Composable
fun showAnimation(){

}