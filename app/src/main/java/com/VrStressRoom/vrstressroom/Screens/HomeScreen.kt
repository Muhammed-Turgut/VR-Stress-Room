package com.VrStressRoom.vrstressroom.Screens

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.LocalOverscrollConfiguration
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.focusModifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.modifier.modifierLocalMapOf
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.VrStressRoom.vrstressroom.Activity.ConnectivityViewModel
import com.VrStressRoom.vrstressroom.Activity.ErorPages.NoInternetScreen
import com.VrStressRoom.vrstressroom.R

import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController
import com.VrStressRoom.vrstressroom.LoginSignup.AuthState
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun HomeScreen(viewModel: ConnectivityViewModel = viewModel(),navController: NavController,authViewModel: AuthViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ) {
        val isConnected by viewModel.isConnected

        LaunchedEffect(Unit) {
            viewModel.checkConnection()
        }

        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
            ) {
                // Header (Sabit bölüm)
                HomeScreenHeader(authViewModel)

                // Dikey kaydırılabilir alan
                CompositionLocalProvider(
                    LocalOverscrollConfiguration provides null // Overscroll efektini kaldır bu scrool edince sınırlarda oluşan gri efekti kapatrı.
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .verticalScroll(rememberScrollState()) // Kaydırma etkisi
                    ) {
                        lastTest()
                        FindATherapist(navController)
                        RecommendedTherapist(authViewModel)
                        commentField()
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreenHeader(authViewModel: AuthViewModel) {
    val userInfo by authViewModel.userInfo.observeAsState()
    val authState by authViewModel.authState.observeAsState()

    LaunchedEffect(Unit) {
        authViewModel.getAuthUserInformation()
    }

    if (authState is AuthState.Error) {
        Text(
            text = (authState as AuthState.Error).message,
            color = Color.Red,
            modifier = Modifier.padding(16.dp)
        )
    }

    userInfo?.let { user ->
        Box(
            modifier = Modifier
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
                )
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 8.dp, bottom = 8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    painter = painterResource(R.drawable.userimage),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(start = 8.dp)
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
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = "${user.name} ${user.lastname}",
                        fontWeight = FontWeight.SemiBold,
                        fontStyle = FontStyle.Normal,
                        fontSize = 14.sp,
                        color = Color.White,
                        lineHeight = 4.sp
                    )
                }

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
}


@Composable
fun lastTest(){
    Box (modifier = Modifier
        .fillMaxWidth()
        .padding(horizontal = 16.dp, vertical = 8.dp)
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
                    fontSize = 24.sp,
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
fun FindATherapist(navController: NavController) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 10.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
            modifier = Modifier.fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 8.dp, end = 8.dp)
            ) {
                Text(
                    text = "Terapist ile Randevu\nAyarla",
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(
                    text = "“sizin için en uygun terapistler”",
                    modifier = Modifier.padding(top = 12.dp),
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                    color = Color.Black
                )

                Button(
                    onClick = {
                        navController.navigate("VideoScreenPage")
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5F9E)),
                    shape = RoundedCornerShape(8.dp),
                    modifier = Modifier
                        .padding(top = 10.dp)
                        .height(48.dp)
                        .widthIn(min = 140.dp)
                ) {
                    Text("Terapist Bul", color = Color.White, fontSize = 17.58.sp)
                }
            }

            Box(
                modifier = Modifier
                    .size(134.dp, 127.dp)
                    .clip(RoundedCornerShape(24.dp))
                    .background(
                        Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFFF8B600),
                                Color(0xFFFF5F9E)
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    painter = painterResource(R.drawable.findaterapistimage),
                    contentDescription = null,
                    modifier = Modifier.size(104.dp),
                    contentScale = ContentScale.Crop
                )
            }
        }
    }
}

@Composable
fun RecommendedTherapist(authViewModel: AuthViewModel) {
    val rating = 4 // 0-5 arası puan

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 8.dp, end = 8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Önerilenler",
                fontSize = 24.sp,
                color = Color.Black,
                fontWeight = FontWeight.SemiBold
            )

            Spacer(modifier = Modifier.height(12.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Image(
                    painter = painterResource(R.drawable.terapistprofileimage),
                    contentDescription = null,
                    modifier = Modifier
                        .border(1.dp, Color.White, CircleShape)
                        .size(42.dp)
                )

                Column(
                    modifier = Modifier
                        .padding(start = 12.dp)
                        .weight(1f)
                ) {
                    Text(
                        text = "Psk. Mehmet Tutucu",
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium,
                        color = Color.Black
                    )

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier.padding(top = 2.dp)
                    ) {
                        repeat(5) { index ->
                            val icon = if (index < rating) R.drawable.selectedstaricon else R.drawable.defaultstaricon
                            Image(
                                painter = painterResource(id = icon),
                                contentDescription = null,
                                modifier = Modifier.size(18.dp)
                            )
                        }
                        Text(
                            text = "4.0",
                            fontSize = 14.sp,
                            fontWeight = FontWeight.SemiBold,
                            modifier = Modifier.padding(start = 4.dp),
                            color = Color.Black
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            if (TherapyAppointmentDateRowlist.isNotEmpty()) {
                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 8.dp, end = 4.dp)
                ) {
                    items(TherapyAppointmentDateRowlist) { item ->
                        TherapyAppointmentDateRow(item)
                        Spacer(modifier = Modifier.width(12.dp))
                    }
                }
            }
        }
    }
}

@Composable
fun commentField(){
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(start = 16.dp, end = 16.dp, top = 16.dp)){

        Column {
            Text("Yorumlar",
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold,
                color = Color.Black,
                modifier = Modifier.padding(start = 16.dp, bottom = 8.dp))

            LazyColumn(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(135.dp)
                    .padding(start = 8.dp, end = 4.dp)
            ) {
                items(TherapyAppointmentDateRowlist) { item ->
                    commentsRow(item)
                    Spacer(modifier = Modifier.height(4.dp))

                }
            }
        }
    }
}

@Composable
fun TherapyAppointmentDateRow(liste: listDay) {
    val clickRowDay = remember { mutableStateOf(false) }

    Box(
        modifier = Modifier
            .width(80.dp)
            .height(IntrinsicSize.Min)
            .padding(start = 4.dp)
            .clickable {
                clickRowDay.value = !clickRowDay.value // ← burada düzeltildi
            }
            .shadow(
                elevation = 4.dp,
                shape = RoundedCornerShape(4.dp),
                clip = false
            )
            .clip(RoundedCornerShape(4.dp))
            .background(if (clickRowDay.value) Color(0xFFFF5F9E) else Color(0xFFFFFFFF))
    ) {
        Column(
            modifier = Modifier
                .padding(horizontal = 8.dp, vertical = 6.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = liste.day,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = if (clickRowDay.value) Color.White else Color.Black,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                lineHeight = 16.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 2.dp)
            ) {
                Image(
                    painter = painterResource(
                        if (clickRowDay.value)
                            R.drawable.clockicon
                        else
                            R.drawable.unselectedclockicon
                    ),
                    contentDescription = null,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = liste.clock,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.SemiBold,
                    color = if (clickRowDay.value) Color.White else Color.Black,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )
            }
        }
    }
}


@Composable
fun commentsRow(liste: listDay){
    val rating=3
    Box(modifier = Modifier.fillMaxWidth()){

          Row(modifier = Modifier.fillMaxWidth().padding(4.dp)){
          Image(painter = painterResource(R.drawable.userimage),
              contentDescription = null,
              modifier = Modifier.size(24.dp))

              Column(modifier = Modifier.padding(start = 8.dp)) {
                  Row(verticalAlignment = Alignment.CenterVertically){
                      Text(text = "Ayşe Tanmaz",
                          fontSize = 10.sp,
                          color = Color.Black,
                          fontWeight = FontWeight.SemiBold,
                          modifier = Modifier.padding(end = 5.dp))

                      repeat(5) { index ->
                          val icon = if (index < rating) R.drawable.selectedstaricon else R.drawable.defaultstaricon
                          Image(
                              painter = painterResource(id = icon),
                              contentDescription = null,
                              modifier = Modifier.size(9.dp)
                          )
                      }
                      Text(text = "3.0",
                          fontSize = 8.2.sp,
                          fontWeight = FontWeight.SemiBold,
                          color = Color.Black,
                          modifier = Modifier.padding(start = 2.dp))
                  }
                  Text(text = "Lorem ipsum dolor sit amet consectetur. Dapibus nec eleifend nunc praesent sed. Malesuada massa integer.",
                      fontSize = 10.sp,
                      fontWeight = FontWeight.Medium,
                      modifier = Modifier.padding(top = 4.dp),
                      color = Color(0xFF5D5D5D)
                  )
              }
          }

      }
}

class listDay(val day:String, val clock:String)

val TherapyAppointmentDateRowlist = listOf(listDay("Bugün","12.00"),
    listDay("Bugün","13.00"),
    listDay("Bugün","15.00"),
    listDay("Bugün","15.00"),
    listDay("Yarın","10.00"),
    listDay("Yarın","18.00")
)

@Preview(showBackground = true)
@Composable
fun displayHomeScreen(){
    commentField()
}