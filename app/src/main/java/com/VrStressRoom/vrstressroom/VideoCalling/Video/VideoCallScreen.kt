package com.VrStressRoom.vrstressroom.VideoCalling.Video

import android.Manifest
import android.R
import android.os.Build
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.VrStressRoom.vrstressroom.Screens.StresTestScreens.WaveAnimation
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import io.getstream.video.android.compose.permission.rememberCallPermissionsState
import io.getstream.video.android.compose.ui.components.call.activecall.CallContent
import io.getstream.video.android.compose.ui.components.call.controls.actions.DefaultOnCallActionHandler
import io.getstream.video.android.compose.ui.components.call.controls.actions.LeaveCallAction
import io.getstream.video.android.compose.ui.components.video.VideoRenderer
import io.getstream.video.android.core.call.state.LeaveCall

@Composable
fun VideoCallScreen(
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
            Column(modifier = Modifier
                .fillMaxSize()
                .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB3005E),
                        Color(0xFF0A007A),
                        Color(0xFFB3005E)
                    )
                )),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center){

                RelaxAnimation()

                Text(text = "Psikolog ile bağlantı \n" +
                        " sağlanıyor", color = Color.White,
                    lineHeight = 32.sp,
                    textAlign = TextAlign.Center,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.SemiBold)

                WaveAnimation()

                Text(text = "Bağlanılıyor...",
                    modifier = Modifier.padding(top = 16.dp),
                    fontSize = 20.sp,
                    color = Color.White)

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
                    ), onCallAction = {action->
                        if(action== LeaveCall){
                            onAction(VideoCallAction.OnDisconnectClick)
                        }
                    DefaultOnCallActionHandler.onCallAction(state.call,action)
                },
                onBackPressed = {
                    onAction(VideoCallAction.OnDisconnectClick)
                }
            )
        }
    }
}

@Composable
fun RelaxAnimation() {
    val composition by rememberLottieComposition(LottieCompositionSpec.Asset("relaxanimation.json"))
    val progress by animateLottieCompositionAsState(
        composition,
        iterations = LottieConstants.IterateForever
    )

    LottieAnimation(
        composition = composition,
        progress = progress,
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // ihtiyacına göre değiştir
    )
} //Ekrandaki sandalyede salanma animasyonu.