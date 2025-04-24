package com.VrStressRoom.vrstressroom.VideoCalling.Video

import android.Manifest
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.video.VideoRenderer

@Composable
fun VideoCallState(
    state: VideoCallState,
    onAction: (VideoCallAction) -> Unit
){
    when{
        state.error != null ->{
            Box(modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center){

                Text(text = state.error,
                    color = Color.Red
                )
            }
        }

        state.callState == CallState.JOINING ->{
            Column(modifier = Modifier.fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){
                CircularProgressIndicator()
                Text(text = "Jonining...")

            }
        }

        else->{
            val basePermissions= listOf(Manifest.permission.CAMERA,
                Manifest.permission.RECORD_AUDIO)

            val bluetoothConnect = if (Build.VERSION.SDK_INT >=31) {
              listOf(Manifest.permission.BLUETOOTH_CONNECT)
            }else{
                emptyList()
            }

            val notificationPermission = if (Build.VERSION.SDK_INT >=33) {
                listOf(Manifest.permission.POST_NOTIFICATIONS)
            }else{
                emptyList()
            }

        val context = LocalContext.current
            CallContent(call = state.call,
                modifier = Modifier.fillMaxSize(),
                permissions = rememberCallPermissionsState(
                    call = state.call,
                    permissions = basePermissions + bluetoothConnect+ notificationPermission,
                    onPermissionsResult = { permission ->
                       if(permission.values.contains(false)){
                           Toast.makeText(context,"Please grant all permission to use this app",
                               Toast.LENGTH_LONG).show()
                       }
                        else{
                            onAction(VideoCallAction.JoinCall)
                       }
                    }
                    )
            )
        }
    }
}