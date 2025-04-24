package com.VrStressRoom.vrstressroom.Activity

import android.os.Bundle
import android.view.ViewGroup
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.VrStressRoom.vrstressroom.Activity.ui.theme.VRStressRoomTheme
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel
import com.VrStressRoom.vrstressroom.LoginSignup.LoginScreenNavigation




class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val authViewModel: AuthViewModel by viewModels()


        setContent {
            VRStressRoomTheme {

                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    LoginScreenNavigation(
                        modifier = Modifier.padding(innerPadding),
                        authViewModel = authViewModel
                    )
                }
            }
        }


    }
}

