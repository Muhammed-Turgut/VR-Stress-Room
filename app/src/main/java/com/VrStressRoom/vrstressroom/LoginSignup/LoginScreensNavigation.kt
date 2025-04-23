package com.VrStressRoom.vrstressroom.LoginSignup

import android.content.Context
import androidx.camera.view.LifecycleCameraController
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraScreen
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel
import com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.ItemChatBotList
import com.VrStressRoom.vrstressroom.Screens.SplashScreenMain
import com.VrStressRoom.vrstressroom.Network.ChatBotScreenPage
import com.VrStressRoom.vrstressroom.Network.ChatViewModel
import com.VrStressRoom.vrstressroom.Screens.MainPage
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StresTestStartScreen


@Composable
fun LoginScreenNavigation(modifier: Modifier=Modifier, authViewModel: AuthViewModel){
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

        composable("Signup"){
            SignupScreen(modifier,navController,authViewModel)
        }
        composable("CameraScreen"){
           CameraScreen(navController)
        }


        composable("AıChatBot"){
            ChatBotScreenPage(navController=navController)
        }



    })
}