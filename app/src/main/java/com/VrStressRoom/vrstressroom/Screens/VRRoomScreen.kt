package com.VrStressRoom.vrstressroom.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.VrStressRoom.vrstressroom.Activity.ui.theme.cabinBold
import com.VrStressRoom.vrstressroom.R

@Composable
fun VRRoomScreen(){
    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White))
    {

        Column(modifier = Modifier.padding(16.dp)){

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp, top = 12.dp, bottom = 12.dp)
                    .clip(CircleShape)
                    .background(Color(0xFFFF5F9E)),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {

                Text(
                    text = "VR Stres Odaları",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    fontFamily = cabinBold,
                    color = Color.White,
                    modifier = Modifier
                        .padding(top = 10.dp, bottom = 10.dp)
                )
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(191.dp) // Yükseklik görselle uyumlu
            ) {
                Image(
                    painter = painterResource(R.drawable.muzikvemeditasyonodasiimage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Görseli kutuyu kaplayacak şekilde kırp
                    modifier = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, bottom = 16.dp), // Yazıları sola al ve biraz boşluk bırak
                    verticalArrangement = Arrangement.Bottom, // İçeriği altta hizala
                    horizontalAlignment = Alignment.Start // Yazıları sola hizala
                ) {
                    Text(
                        text = "Müzik ile\n" +
                                "Meditasyon Odası",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp, // Biraz daha küçülttüm çünkü çok büyük duruyordu
                        lineHeight = 28.sp // Satır aralığını biraz açtım
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Yazıyla buton arasına boşluk

                    Button(
                        onClick = {
                            // Tıklama işlevi burada tanımlanabilir
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier
                            .width(100.dp)
                            .height(36.dp)
                    ) {
                        Text(
                            text = "Odaya Gir",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }


            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(191.dp) // Yükseklik görselle uyumlu
            ) {
                Image(
                    painter = painterResource(R.drawable.vrpsikologimage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Görseli kutuyu kaplayacak şekilde kırp
                    modifier = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, bottom = 16.dp), // Yazıları sola al ve biraz boşluk bırak
                    verticalArrangement = Arrangement.Bottom, // İçeriği altta hizala
                    horizontalAlignment = Alignment.Start // Yazıları sola hizala
                ) {
                    Text(
                        text = "Psikoterapi Odası",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp, // Biraz daha küçülttüm çünkü çok büyük duruyordu
                        lineHeight = 28.sp // Satır aralığını biraz açtım
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Yazıyla buton arasına boşluk

                    Button(
                        onClick = {
                            // Tıklama işlevi burada tanımlanabilir
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier
                            .width(100.dp)
                            .height(36.dp)
                    ) {
                        Text(
                            text = "Odaya Gir",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp)
                    .height(191.dp) // Yükseklik görselle uyumlu
            ) {
                Image(
                    painter = painterResource(R.drawable.toprakdesarjimage),
                    contentDescription = null,
                    contentScale = ContentScale.Crop, // Görseli kutuyu kaplayacak şekilde kırp
                    modifier = Modifier.fillMaxSize()
                        .clip(RoundedCornerShape(24.dp))
                )

                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(start = 16.dp, bottom = 16.dp), // Yazıları sola al ve biraz boşluk bırak
                    verticalArrangement = Arrangement.Bottom, // İçeriği altta hizala
                    horizontalAlignment = Alignment.Start // Yazıları sola hizala
                ) {
                    Text(
                        text = "Toprak Deşarj\nOdası",
                        color = Color.White,
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp, // Biraz daha küçülttüm çünkü çok büyük duruyordu
                        lineHeight = 28.sp // Satır aralığını biraz açtım
                    )

                    Spacer(modifier = Modifier.height(8.dp)) // Yazıyla buton arasına boşluk

                    Button(
                        onClick = {
                            // Tıklama işlevi burada tanımlanabilir
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                        shape = RoundedCornerShape(8.dp),
                        contentPadding = PaddingValues(horizontal = 12.dp, vertical = 4.dp),
                        modifier = Modifier
                            .width(100.dp)
                            .height(36.dp)
                    ) {
                        Text(
                            text = "Odaya Gir",
                            color = Color.Black,
                            fontWeight = FontWeight.Bold,
                            fontSize = 12.sp
                        )
                    }
                }
            }


        }
    }
}

@Preview(showBackground = true)
@Composable
fun selam(){
    VRRoomScreen()
}