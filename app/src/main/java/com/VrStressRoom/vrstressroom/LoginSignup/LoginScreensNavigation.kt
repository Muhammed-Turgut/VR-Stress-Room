package com.VrStressRoom.vrstressroom.LoginSignup

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.VrStressRoom.vrstressroom.Activity.ConnectivityViewModel
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraScreen
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel
import com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.ItemChatBotList
import com.VrStressRoom.vrstressroom.Screens.SplashScreenMain
import com.VrStressRoom.vrstressroom.Network.ChatBotScreenPage
import com.VrStressRoom.vrstressroom.Network.ChatViewModel
import com.VrStressRoom.vrstressroom.Screens.MainPage
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.AIQuizStartScreen
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.AiQuizScreen
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.BreathingExerciseScreen
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StresTestStartScreen
import com.VrStressRoom.vrstressroom.VideoCalling.VideoScreenPage


@Composable
fun LoginScreenNavigation(modifier: Modifier=Modifier, authViewModel: AuthViewModel,
                          context: Context,
                          cameraViewModel: CameraViewModel){
    val navController = rememberNavController()


    NavHost(navController=navController, startDestination = "SplashScreen", builder = {

        composable("MainPage"){
            MainPage(modifier,navController,authViewModel)
        }

        composable("SplashScreen"){
            SplashScreenMain(modifier,navController,authViewModel)
        }

        composable("Login"){
            LoginScreen(modifier,navController,authViewModel)
        }

        composable("StresTestStartScreen"){
            StresTestStartScreen(navController)
        }

        composable("VideoScreenPage") {
             VideoScreenPage()
        }

        composable("Signup"){
            SignupScreen(modifier,navController,authViewModel)
        }
        composable("CameraScreen"){
           CameraScreen(navController)
        }
        composable("AiQuizScreen"){
            AiQuizScreen(navController,context, cameraViewModel = cameraViewModel)
        }

        composable("AıChatBot"){
            ChatBotScreenPage(navController=navController)
        }

        composable("BreathingExerciseScreen"){
            BreathingExerciseScreen(navController=navController,context)
        }



    })
}