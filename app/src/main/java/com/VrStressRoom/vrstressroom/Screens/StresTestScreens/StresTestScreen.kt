package com.VrStressRoom.vrstressroom.Screens.StresTestScreens



import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.VrStressRoom.vrstressroom.R
import kotlinx.coroutines.delay

@Composable
fun StresTestScreen(navController: NavController){
    val cabinBold = FontFamily(
        Font(R.font.cabinbold, FontWeight.Bold)
    )

    val cabinRegular = FontFamily(
        Font(R.font.cabinregular, FontWeight.Normal)
    )

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)) {

        Column {
            Row(modifier = Modifier
                .fillMaxWidth()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center)
            {
                Column {
                    Text(text = "Son stres testi\n" +
                            "sonucu",
                        fontSize = 32.sp,
                        fontWeight = FontWeight.Bold,
                        fontFamily = cabinBold,
                        lineHeight = 24.sp,
                        color = Color.Black
                    )

                    Text(modifier = Modifier.padding(top = 8.dp), text = "Stres seviyeniz iyi \n" +
                            "durumda",
                        fontWeight =FontWeight.Normal,
                        fontFamily = cabinRegular,
                        fontSize = 20.sp,
                        color = Color.Black)
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
                                Color(0xFF060047),
                                Color(0xFFB3005E)
                            )
                        ),
                        shape = RoundedCornerShape(4.dp) // Butonun köşelerini yuvarlama
                    )
                    .fillMaxWidth(), // Buton boyutu
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Transparent // Arka planı şeffaf yapıyoruz çünkü background ile boyadık
                )
            ) {
                Text(text = "Stres Seviyesi Ölç",
                    color = Color.White,
                    fontSize = 16.sp)
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
                color = if (stressLevel < 40) Color.Green else if (stressLevel < 70) Color.Yellow else Color(0xFFFF5F9E),
                startAngle = -90f,
                sweepAngle = sweepAngle,
                useCenter = false,
                style = Stroke(width = strokeWidth, cap = StrokeCap.Round)
            )
        }

        // Ortadaki metin
        Column(horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center) {
            Text(
                text = "$stressLevel%",
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold,color = Color.Black

            )
            Text(
                text = "12.04.2025",
                fontSize = 10.sp,
                fontWeight = FontWeight.Bold,
                color = Color.Black
            )
        }
    }
}


@Preview(showBackground = true)
@Composable
fun displayStresTestApp(){
    val navController = rememberNavController()
    StresTestScreen(navController = navController)
}