package com.VrStressRoom.vrstressroom.Screens

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.VrStressRoom.vrstressroom.LoginSignup.AuthState
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel
import com.VrStressRoom.vrstressroom.R
import kotlinx.coroutines.delay


@Composable
fun SplashScreenMain(modifier: Modifier=Modifier, navController: NavController, authViewModel: AuthViewModel) {
    val authState =authViewModel.authState.observeAsState()
    val context = LocalContext.current

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFB3005E),
                    Color(0xFF0A007A),
                    Color(0xFFB3005E)
                )
            )
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 200.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(R.drawable.logovr),
                contentDescription = null,
                modifier = Modifier.size(320.dp, 368.dp)
            )

        }
    }

    LaunchedEffect(Unit) {
        delay(3000L)

            when(authState.value){
                is AuthState.Authenticated -> navController.navigate("MainPage")
                is AuthState.Unauthenticated -> navController.navigate("Login")
                is AuthState.Error -> Toast.makeText(context,
                    (authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()

                else -> Unit
            }
    }
}

@Preview(showBackground = true)
@Composable
fun displaySplashScreen(){

}