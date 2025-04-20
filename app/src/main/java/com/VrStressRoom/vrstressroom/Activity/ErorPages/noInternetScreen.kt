package com.VrStressRoom.vrstressroom.Activity.ErorPages

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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.VrStressRoom.vrstressroom.R

@Composable
fun NoInternetScreen( onRetry: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // İnternet Yok İkonu
            Image(
                painter = painterResource(R.drawable.nointerneticon),
                contentDescription = "İnternet Yok",
                modifier = Modifier.size(180.dp) // İsteğe bağlı boyutlandırma
            )

            // Açıklama Metni
            Text(
                text = "İnternet bağlantınız yok\nYeniden bağlanmayı deneyin",
                color = Color(0xFF060047),
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                textAlign = TextAlign.Center
            )

            // Yeniden Dene Butonu
            Button(onClick = onRetry,
                colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFB3005E)),
                shape = RoundedCornerShape(8.dp),
                modifier = Modifier
                    .padding(16.dp)
                    .height(48.dp)
                    .widthIn(min = 140.dp)
            ) {
                Text("Yeniden Dene", color = Color.White)
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun displayErorPage(){

}