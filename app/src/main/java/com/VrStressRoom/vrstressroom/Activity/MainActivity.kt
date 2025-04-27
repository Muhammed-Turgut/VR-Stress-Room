package com.VrStressRoom.vrstressroom.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import com.VrStressRoom.vrstressroom.Activity.ui.theme.VRStressRoomTheme
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel
import com.VrStressRoom.vrstressroom.LoginSignup.LoginScreenNavigation
import com.VrStressRoom.vrstressroom.Network.ChatViewModel


class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()
        val cameraViewModel: CameraViewModel by viewModels()

        setContent {
            VRStressRoomTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreenNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel,
                        context = this,
                        cameraViewModel = cameraViewModel
                    )
                }
            }
        }
    }
}


