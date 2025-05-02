package com.VrStressRoom.vrstressroom.Activity

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.VrStressRoom.vrstressroom.Activity.ErorPages.NoInternetScreen
import com.VrStressRoom.vrstressroom.Activity.ui.theme.VRStressRoomTheme
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel
import com.VrStressRoom.vrstressroom.LoginSignup.LoginScreenNavigation

class MainActivity : ComponentActivity() {

    private val authViewModel: AuthViewModel by viewModels()
    private val cameraViewModel: CameraViewModel by viewModels()
    private val connectivityViewModel: ConnectivityViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            VRStressRoomTheme {
                val isConnected by connectivityViewModel.isConnected // Burada bağlantıyı gözlemliyoruz

                if (isConnected) {
                    Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                        LoginScreenNavigation(
                            modifier = Modifier.padding(innerPadding),
                            authViewModel = authViewModel,
                            context = this,
                            cameraViewModel = cameraViewModel
                        )
                    }
                } else {
                    NoInternetScreen(onRetry = {
                        connectivityViewModel.checkConnection()
                    })
                }
            }
        }
    }
}
