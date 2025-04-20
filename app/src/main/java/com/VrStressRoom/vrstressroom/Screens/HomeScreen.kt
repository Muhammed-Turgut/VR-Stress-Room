package com.VrStressRoom.vrstressroom.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.VrStressRoom.vrstressroom.Activity.ConnectivityViewModel
import com.VrStressRoom.vrstressroom.Activity.ErorPages.NoInternetScreen
import com.VrStressRoom.vrstressroom.R

@Composable
fun HomeScreen(viewModel: ConnectivityViewModel = viewModel()) {
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        val isConnected by viewModel.isConnected

        LaunchedEffect(Unit) {
            viewModel.checkConnection()
        }

        if (isConnected) {
            Box(modifier = Modifier.fillMaxSize()){
              Column(modifier = Modifier.fillMaxSize()) {
                  HomeScreenHeader()
                  lastTest()
              }
            }

        } else {
            NoInternetScreen(onRetry = {
                viewModel.checkConnection()
            })
        }
    }
}

@Composable
fun HomeScreenHeader() {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 12.dp)
        .clip(RoundedCornerShape(100.dp))
        .background(
            brush = Brush.verticalGradient(
                colors = listOf(
                    Color(0xFFFF5F9E),
                    Color(0xFFB3005E)
                )
            )
        )){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(top = 8.dp, bottom = 8.dp),
            verticalAlignment = Alignment.CenterVertically) {
            // Profil Fotoğrafı
            Image(
                painter = painterResource(R.drawable.userimage),
                contentDescription = null,
                modifier = Modifier
                    .padding(start = 8.dp) // En sola boşluk bırakır
                    .size(48.dp)
                    .clip(CircleShape)
                    .border(2.dp, Color.White, CircleShape)
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp)
            ) {
                Text(
                    text = "Hoş Geldiniz",
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Italic,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 4.sp
                )
                Spacer(modifier = Modifier.height(2.dp)) // Boşluğu azaltmak için 4.dp
                Text(
                    text = "Muhammed Turgut",
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    fontSize = 14.sp,
                    color = Color.White,
                    lineHeight = 4.sp //satır yüksekliği
                )
            }

            // Bildirim İkonu
            Image(
                painter = painterResource(R.drawable.belliconbutton),
                contentDescription = null,
                modifier = Modifier
                    .padding(end = 12.dp)
                    .size(48.dp)
            )
        }
    }
}

@Composable
fun lastTest(){
    Box (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical =8.dp)
        .clip(RoundedCornerShape(8.dp))
        .size(width = 380.dp, height = 158.dp)
        .background(
            brush = Brush.horizontalGradient(
                colors = listOf(
                    Color(0xFFB3005E),// Sağdaki renk
                    Color(0xFF060047)// Soldaki renk
                )
            )
        )
        .padding(start = 16.dp, end = 16.dp, top = 8.dp)
        ){

        Row {
            Column(modifier = Modifier.padding(8.dp)) {
                Text("Son Stres ölçümü",
                    color = Color.White,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
                Text("Şuanda nasıl hisediyorsunuz",
                    color = Color.White,
                    fontWeight = FontWeight.Medium,
                    fontSize = 14.sp,
                    modifier = Modifier.padding(top = 4.dp))

                Button(
                    onClick = {
                        // Tıklama işlevi burada tanımlanabilir
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                    shape = RoundedCornerShape(8.dp),
                    contentPadding = PaddingValues(horizontal = 8.dp, vertical = 2.dp), // İç padding ayarlandı
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .size(width = 108.dp, height = 36.dp) // Buton boyutu
                ) {
                    Text(
                        text = "Yeniden Test Et",
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 12.sp,
                        lineHeight = 4.sp,
                    )
                }
            }

        }


    }

}


@Composable
fun emotionalState() {
    // Seçili butonun indeksini tutar
    var selectedIndex by remember { mutableStateOf(-1) }

    val options = listOf("Çok İyi", "İyi", "Normal", "Kötü", "Çok Kötü")
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp)) {

        Column(modifier = Modifier.fillMaxWidth()) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()  // Genişlik
                    .height(10.dp)
                    .padding(
                        start = 8.dp, end = 8.dp, top = 1.dp, bottom = 1.dp )
                    .clip(RoundedCornerShape(4.dp))
                    .background(Color.Black)


            )
            Row(modifier = Modifier.fillMaxWidth()) {
                options.forEachIndexed { index, label ->
                    Column(
                        modifier = Modifier
                            .weight(1f),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        RadioButton(
                            selected = selectedIndex == index,
                            onClick = { selectedIndex = index }
                        )
                        Text(text = label, fontSize = 12.sp, color = Color.White)
                    }
                }
            }
        }

    }
}


@Preview(showBackground = true)
@Composable
fun displayHomeScreen(){
    lastTest()
}