package com.VrStressRoom.vrstressroom.Screens

import android.R.attr.elevation
import android.R.attr.shape
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.VrStressRoom.vrstressroom.Activity.ConnectivityViewModel
import com.VrStressRoom.vrstressroom.Activity.ErorPages.NoInternetScreen
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel
import com.VrStressRoom.vrstressroom.R

@Composable
fun ProfilDetailsScreen(authViewModel: AuthViewModel,navController: NavController, connectViewModel: ConnectivityViewModel = viewModel()){

    Box(modifier = Modifier.fillMaxSize()
        .background(Color.White)){
        val isConnected by connectViewModel.isConnected

        if(isConnected){
            Column(modifier = Modifier
                .fillMaxSize()
                .padding(start = 16.dp, end = 16.dp, top = 32.dp),
                horizontalAlignment = Alignment.CenterHorizontally){

                Text(text = "Profile",
                    textAlign = TextAlign.Center,
                    fontSize =24.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = Color.Black)

                Image(painter = painterResource(R.drawable.userimage),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 12.dp)
                        .size(104.dp)
                        .border(2.dp, Color(0xFFB3005E), CircleShape)
                )

                Text(text = "Kullanıcı",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.Gray,
                    modifier = Modifier.padding(top = 4.dp),
                    lineHeight = 10.sp)

                Text(text = "Muhammed Turgut",
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontSize = 18.sp,
                    modifier = Modifier.padding(top = 4.dp),
                    lineHeight = 10.sp)

                Text(text = "muhammedtur20@gmail.com",
                    modifier = Modifier.padding(top = 4.dp),
                    fontSize = 12.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    fontWeight = FontWeight.SemiBold,
                    fontStyle = FontStyle.Normal,
                    lineHeight = 10.sp)

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .graphicsLayer {
                            shadowElevation = 3.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                        }
                        .background(Color(0xFFF4F4F4)),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(painter = painterResource(R.drawable.profileediticon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))

                    Text("Profile Düzenle",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        color = Color(0xFF3E3E3E),
                        lineHeight = 10.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                            .weight(1f))

                    Image(painter = painterResource(R.drawable.chevronrighticon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .graphicsLayer {
                            shadowElevation = 3.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                        }
                        .background(Color(0xFFF4F4F4)),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(painter = painterResource(R.drawable.subscribesicon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))

                    Text("Abonelik Paketi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        color = Color(0xFF3E3E3E),
                        lineHeight = 10.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                            .weight(1f))

                    Image(painter = painterResource(R.drawable.chevronrighticon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .graphicsLayer {
                            shadowElevation = 3.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                        }
                        .background(Color(0xFFF4F4F4)),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(painter = painterResource(R.drawable.languageicon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))

                    Text("Dil",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        color = Color(0xFF3E3E3E),
                        lineHeight = 10.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                            .weight(1f))

                    Image(painter = painterResource(R.drawable.chevronrighticon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .graphicsLayer {
                            shadowElevation = 3.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                        }
                        .background(Color(0xFFF4F4F4)),
                    verticalAlignment = Alignment.CenterVertically
                ){
                    Image(painter = painterResource(R.drawable.helpcentericon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(start = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))

                    Text("Yardım Merkezi",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        color = Color(0xFF3E3E3E),
                        lineHeight = 10.sp,
                        modifier = Modifier
                            .padding(start = 10.dp, top = 10.dp, bottom = 10.dp)
                            .weight(1f))

                    Image(painter = painterResource(R.drawable.chevronrighticon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                        .clickable{
                            authViewModel.signout()
                            navController.navigate("Login")
                        }
                        .graphicsLayer {
                            shadowElevation = 3.dp.toPx()
                            shape = RoundedCornerShape(8.dp)
                            clip = true
                        }
                        .background(Color(0xFFF4F4F4)),
                    verticalAlignment = Alignment.CenterVertically
                ){

                    Text("Çıkış Yap",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        color = Color(0xFFFF0000),
                        lineHeight = 10.sp,
                        modifier = Modifier
                            .padding(start = 16.dp, top = 10.dp, bottom = 10.dp)
                            .weight(1f))

                    Image(painter = painterResource(R.drawable.logouticon),
                        contentDescription = null,
                        modifier = Modifier
                            .padding(end = 16.dp,top = 10.dp, bottom = 10.dp)
                            .size(24.dp))
                }


            }
        }else {
            NoInternetScreen(onRetry = {
                connectViewModel.checkConnection()
            })
        }
    }
}

@Preview(showBackground = true)
@Composable
fun dispalyProfile(){

}