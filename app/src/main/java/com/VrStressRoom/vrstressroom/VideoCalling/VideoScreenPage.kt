package com.VrStressRoom.vrstressroom.VideoCalling

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.VrStressRoom.vrstressroom.VideoCalling.Video.CallState
import com.VrStressRoom.vrstressroom.VideoCalling.Video.VideoCallScreen
import com.VrStressRoom.vrstressroom.VideoCalling.Video.VideoCallViewModel
import io.getstream.video.android.compose.theme.VideoTheme
import kotlinx.serialization.Serializable
import org.koin.androidx.compose.koinViewModel

@Composable
fun VideoScreenPage(){
    Scaffold(modifier = Modifier.fillMaxSize()){innerPadding->
        val navController = rememberNavController()
        NavHost(navController=navController,
            startDestination = ConnectRoute,
            modifier = Modifier.padding(innerPadding)){
            composable<ConnectRoute>{
                val viewModel = koinViewModel<ConnectViewModel>()
                val state = viewModel.state
                LaunchedEffect(key1 = state.isConnecting) {
                    if(state.isConnecting){
                        navController.navigate(VideoCallRoute){
                            popUpTo(ConnectRoute){
                                inclusive = true
                            }
                        }
                    }
                }
                ConnectScreen(state=state, onAction = viewModel::onAction)
            }
            composable<VideoCallRoute> {
                val viewModel = koinViewModel<VideoCallViewModel>()
                val state = viewModel.state

                LaunchedEffect(key1 = state.callState) {
                    if(state.callState == CallState.ENDED){
                        navController.navigate(ConnectRoute){
                            popUpTo(VideoCallRoute){
                                inclusive = true
                            }
                        }
                    }
                }
                VideoTheme{
                    VideoCallScreen(state=state, onAction = viewModel::onAction)
                }

            }
        }

    }
}

@Serializable
data object  ConnectRoute

@Serializable
data object VideoCallRoute