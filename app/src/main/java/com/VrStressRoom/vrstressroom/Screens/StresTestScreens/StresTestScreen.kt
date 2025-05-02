package com.VrStressRoom.vrstressroom.Screens.StresTestScreens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.credentials.provider.CredentialEntry
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.VrStressRoom.vrstressroom.Activity.ui.theme.cabinBold
import com.VrStressRoom.vrstressroom.Activity.ui.theme.cabinRegular
import com.VrStressRoom.vrstressroom.R

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun StresTestScreen(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        CompositionLocalProvider(LocalOverscrollConfiguration provides null) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState()),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(
                            brush = Brush.verticalGradient(
                                colors = listOf(
                                    Color(0xFFB3005E),
                                    Color(0xFF060047),
                                )
                            ),
                            shape = RoundedCornerShape(bottomStart = 24.dp, bottomEnd = 24.dp)
                        )
                ) {

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 30.dp)
                            .clip(CircleShape)
                            .background(Color.White),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {

                        Text(
                            text = "Stres Test",
                            fontSize = 24.sp,
                            fontWeight = FontWeight.Bold,
                            fontFamily = cabinBold,
                            color = Color.Black,
                            modifier = Modifier
                                .padding(top = 10.dp, bottom = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(start = 16.dp, end = 16.dp, top = 8.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    )
                    {
                        Column {
                            Text(
                                text = "Son stres testi\n" +
                                        "sonucu",
                                fontSize = 32.sp,
                                fontWeight = FontWeight.Bold,
                                fontFamily = cabinBold,
                                lineHeight = 24.sp,
                                color = Color.White
                            )

                            Text(
                                modifier = Modifier.padding(top = 8.dp),
                                text = "Stres seviyeniz iyi \n" +
                                        "durumda",
                                fontWeight = FontWeight.Normal,
                                fontFamily = cabinRegular,
                                fontSize = 20.sp,
                                color = Color.White
                            )
                        }
                        Spacer(modifier = Modifier.width(16.dp))
                        StressLevelIndicator(stressLevel = 80)
                    }
                    Button(
                        onClick = {
                            navController.navigate("StresTestStartScreen")
                        },
                        modifier = Modifier
                            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
                            .background(
                                brush = Brush.verticalGradient(
                                    colors = listOf(
                                        Color(0xFFFF5F9E),
                                        Color(0xFFF8B600)
                                    )
                                ),
                                shape = RoundedCornerShape(16.dp) // Butonun köşelerini yuvarlama
                            )
                            .fillMaxWidth(), // Buton boyutu
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.Transparent // Arka planı şeffaf yapıyoruz çünkü background ile boyadık
                        )
                    ) {
                        Text(
                            text = "Stres Seviyesi Ölç",
                            color = Color.White,
                            fontSize = 16.sp
                        )
                    }
                    Spacer(modifier = Modifier.height(18.dp))
                }

                Text(
                    text = "Haftalık Stres Göstergesi",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(top = 16.dp),
                    fontFamily = cabinBold
                )

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp)
                ) {

                    Text(
                        text = "Stres seviyesi",
                        fontSize = 16.sp,
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinBold,
                        modifier = Modifier
                            .padding(top = 8.dp)
                            .weight(1f)
                    )

                    Image(
                        painter = painterResource(R.drawable.calendar),
                        contentDescription = null,
                        modifier = Modifier.size(28.dp)
                    )

                }

                StressLevelWeek()

                BreathingExercisesStressTest(navController)
            }
        }
    }
}

@Composable
fun StressLevelIndicator(
    modifier: Modifier = Modifier,
    stressLevel: Int, // 0 ile 100 arasında
    maxLevel: Int = 100
) {
    val sweepAngle = (stressLevel / maxLevel.toFloat()) * 360f
    val strokeWidth = 20f

    Box(
        modifier = modifier
            .size(140.dp)
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            // Arka plan çemberi (gri)
            drawArc(
                color = Color.LightGray,
                startAngle = -90f,
                sweepAngle = 360f,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )

            // Öndeki çember (stres seviyesi kadar)
            drawArc(
                color = if (stressLevel < 40) Color.Green else if (stressLevel < 70) Color.Yellow else Color(
                    0xFFFF5F9E
                ),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Ortadaki metin
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "$stressLevel%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold, color = Color.White

            )
            Text(
                text = "12.04.2025",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
        }
    }
 }

@Composable
fun StressLevelWeek() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 0.dp, vertical = 24.dp),
        verticalAlignment = Alignment.Bottom
    ) {
        repeat(7) { index ->
            val progress = (index + 1) / 7f
            Box(
                modifier = Modifier
                    .weight(1f) // Otomatik genişlik ve eşit boşluk
                    .padding(horizontal = 4.dp), // Çubuklar arası hafif boşluk
                contentAlignment = Alignment.BottomCenter
            ) {
                VerticalLevelBar(progress = progress)
            }
        }
    }
}

@Composable
fun VerticalLevelBar(
    modifier: Modifier = Modifier,
    progress: Float // 0.0f - 1.0f arası
) {
    Column(modifier= Modifier.width(20.dp)){
        Box(
            modifier = modifier
                .width(20.dp)
                .height(200.dp)
                .clip(RoundedCornerShape(topStart = 12.dp, topEnd = 12.dp, bottomStart = 0.dp, bottomEnd = 0.dp))
                .background( Color(0xFFEBEBEB)),
            contentAlignment = Alignment.BottomCenter
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(progress.coerceIn(0f, 1f)) // progress oranı kadar yükseklik
                    .background(Color(0xFFFF69B4)) // Pembe renk (hot pink gibi)
            )
        }

        Text(text = "Pzt",
            fontSize = 12.sp,
            fontWeight = FontWeight.Bold,
            fontFamily = cabinBold,
            modifier= Modifier.padding(top = 4.dp),
            color = Color(0xFF707070)
        )
    }

}

@Composable
fun BreathingExercisesStressTest(navController: NavController){
    Row(
        modifier = Modifier
            .padding(start = 16.dp, end = 16.dp, top = 8.dp)
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp)) // Daha yumuşak köşeler
            .background(Color.White)
            .padding(16.dp), // İç boşluk
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween // Boşlukları daha dengeli dağıtır
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // İçeriğin fazla yer kaplamasını sağlar
                .padding(end = 12.dp) // Image'den biraz uzak durur
        ) {
            Text(
                text = "Nefes Egzersizi\nYap ve Rahatla",
                color = Color.Black,
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                lineHeight = 30.sp
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Nefes egzersizleri, stresi\nazaltıp" +
                        "zihni sakinleştirerek bedenin doğal dengesini\n" +
                        "korumaya yardımcı olur.",
                color = Color.Gray,
                fontSize = 14.sp,
                lineHeight = 16.sp
            )
            Spacer(modifier = Modifier.height(16.dp))
            Button(
                onClick = {
                    navController.navigate("BreathingExerciseScreen")
                },
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF5F9E),
                    contentColor = Color.White
                ),
                shape = RoundedCornerShape(12.dp),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 4.dp
                ),
                modifier = Modifier
                    .height(48.dp)
                    .defaultMinSize(minWidth = 140.dp)
            ) {
                Text(
                    text = "Başla",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
        Image(
            painter = painterResource(id = R.drawable.nefesegzersizi),
            contentDescription = "Nefes Egzersizi",
            contentScale = ContentScale.Crop, // Daha iyi görüntü oturması için
            modifier = Modifier
                .size(120.dp) // Biraz daha küçük, tasarıma uyumlu
                .clip(RoundedCornerShape(12.dp)) // Hafif arka plan opsiyonel
        )
    }

}



@Preview(showBackground = true)
@Composable
fun displayStresTestApp(){
    val navController = rememberNavController()
    StresTestScreen(navController = navController)
}