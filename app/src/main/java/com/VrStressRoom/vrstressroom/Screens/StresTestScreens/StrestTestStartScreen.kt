package com.VrStressRoom.vrstressroom.Screens.StresTestScreens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.VrStressRoom.vrstressroom.R
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.animateLottieCompositionAsState
import com.airbnb.lottie.compose.rememberLottieComposition
import kotlinx.coroutines.delay

@Composable
fun StresTestStartScreen(navController: NavController) {
    val cabinBold = FontFamily(
        Font(R.font.cabinbold, FontWeight.Bold)
    )
    val cabinSemiBold = FontFamily(
        Font(R.font.cabinsemibold, FontWeight.SemiBold)
    )

    LaunchedEffect(Unit) {
        delay(3000L) // 3000 milisaniye = 3 saniye
        navController.navigate("CameraScreen") // Diğer ekrana geçiş
    }
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
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp)
        )
        {

            Text(
                text = "Stres Testi",
                modifier = Modifier.padding(top = 160.dp),
                fontSize = 36.sp,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontFamily = cabinBold
            )
            CircularCanvasWithImage(R.drawable.facescaningicon)

            Text(text = "Kameraya bakarak\n" +
                    "rahat olun yüz taraması yapılacak.",
                textAlign = TextAlign.Center,
                color = Color.White,
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = cabinSemiBold,
                modifier = Modifier.padding(top = 18.dp))

               WaveAnimation()

            Text(text="test başlıyor...", color = Color.White,
                fontSize =18.sp,
                fontWeight = FontWeight.SemiBold,
                fontFamily = cabinSemiBold)

        }

    }
}

@Composable
fun CircularCanvasWithImage(imageResId: Int) {
    Box(
        modifier = Modifier
            .padding(top = 38.dp)
            .size(200.dp),
        contentAlignment = Alignment.Center
    ) {
        // 1. Daire çizimi
        androidx.compose.foundation.Canvas(modifier = Modifier.matchParentSize()) {
            drawCircle(
                color = Color.White.copy(0.4f),
                radius = size.minDimension / 2,
                center = center
            )
        }

        // 2. Görseli ortala ve daireye sığdır
        Image(
            painter = painterResource(id = imageResId),
            contentDescription = "Profile Image",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(RectangleShape)
        )
    }
} //Burası içinde resim içeren dairemizin alanı.


@Composable
fun WaveAnimation() {
        val composition by rememberLottieComposition(LottieCompositionSpec.Asset("waveanimation.json"))
        val progress by animateLottieCompositionAsState(
            composition,
            iterations = LottieConstants.IterateForever
        )

        LottieAnimation(
            composition = composition,
            progress = progress,
            modifier = Modifier
                .fillMaxWidth()
                .height(120.dp) // ihtiyacına göre değiştir
        )
    }


@Preview(showBackground = true)
@Composable
fun displayStresTest(){

}