package com.VrStressRoom.vrstressroom.VideoCalling


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
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel
import com.VrStressRoom.vrstressroom.R



@Composable
fun ConnectScreen(
    state: ConnectState,
    onAction: (ConnectAction) -> Unit){
    var searchText by remember { mutableStateOf("") }
    val auth: AuthViewModel

    Box(modifier = Modifier
        .fillMaxSize()
        .background(Color.White)){
        Column(modifier = Modifier
            .fillMaxSize()
            .padding(start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally){

            Text(text = "Psikologlar",
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 24.sp,
                fontWeight = FontWeight.SemiBold)

            Spacer(modifier = Modifier.height(16.dp))

            OutlinedTextField(
                value = searchText,
                onValueChange = { searchText = it },
                label = { Text("Psikolog ara...") },
                shape = CircleShape,
                placeholder = { Text("Psikolog ismi girin") },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp, end = 16.dp),
                singleLine = true,
                leadingIcon = {
                    Icon(imageVector = Icons.Default.Search, contentDescription = "Search Icon")
                }
            )

            Text(text = "Aktif Psikologlar",
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold)

            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                items(psychologistsList) { item->
                    ActivePsychologistsRow(item, onAction = onAction, state = state)
                }
            }

            Text(text = "Randevulu Psikologlar",
                modifier = Modifier.padding(top = 16.dp),
                fontSize = 18.sp,
                fontWeight = FontWeight.SemiBold)

            LazyColumn( modifier = Modifier
                .fillMaxWidth()
                .padding(top = 12.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)) {

                items(psychologistsList) {item ->
                    AppointmentPsychologistsRow(item,onAction=onAction, state = state)
                }

            }




        }

    }
}

@Composable
fun ActivePsychologistsRow(
    item: Psychologists,
    state: ConnectState,
    onAction: (ConnectAction) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(12.dp))
            .background(Color(0xFFF3F3F3))
            .widthIn(min = 140.dp)
            .padding(12.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        // Psikolog Görseli
        Image(
            painter = painterResource(R.drawable.userimage),
            contentDescription = "Psikolog Fotoğrafı",
            modifier = Modifier
                .size(60.dp)
                .padding(bottom = 8.dp)
        )

        // İsim
        Text(
            text = "Psk. ${item.namePsychologists}",
            fontSize = 14.sp,
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.SemiBold,
            lineHeight = 18.sp,
            modifier = Modifier.fillMaxWidth()
        )

        // Yıldızlar ve Puan
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(top = 6.dp)
        ) {
            repeat(5) { index ->
                val icon = if (index < item.rating) R.drawable.selectedstaricon else R.drawable.defaultstaricon
                Image(
                    painter = painterResource(id = icon),
                    contentDescription = "Yıldız",
                    modifier = Modifier.size(12.dp)
                )
            }

            Text(
                text = "${item.rating},0",
                fontSize = 10.sp,
                fontWeight = FontWeight.Medium,
                modifier = Modifier.padding(start = 4.dp)
            )
        }

        // Buton
        Button(
            onClick = {
                onAction(ConnectAction.OnNameChange("Muhammed"))
                onAction(ConnectAction.OnConnectClick)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFF060047)),
            shape = RoundedCornerShape(6.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 10.dp)
                .height(36.dp),
            contentPadding = PaddingValues(horizontal = 4.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Görüşme Başlat",
                color = Color.White,
                fontSize = 10.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun AppointmentPsychologistsRow(item: Psychologists,state: ConnectState,
                                onAction: (ConnectAction) -> Unit) {
    Row(
        modifier = Modifier
            .background(Color(0xFFFFFFFF))
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Image(
            painter = painterResource(R.drawable.userimage),
            contentDescription = null,
            modifier = Modifier
                .size(50.dp)
                .padding(end = 12.dp)
        )

        Column(modifier = Modifier.weight(1f)) { // Column genişliğini alıyor, Spacer gerek kalmıyor
            Text(
                text = "Psk. "+"${item.namePsychologists}",
                fontSize = 16.sp,
                fontWeight = FontWeight.SemiBold,
                lineHeight = 10.sp
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(top = 4.dp)
            ) {
                repeat(5) { index ->
                    val icon =
                        if (index < item.rating) R.drawable.selectedstaricon else R.drawable.defaultstaricon
                    Image(
                        painter = painterResource(id = icon),
                        contentDescription = null,
                        modifier = Modifier.size(11.dp)
                    )
                }
                Text(
                    text = "${item.rating}.0",
                    fontSize = 10.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(start = 4.dp)
                )
            }
        }

        Button(
            onClick = {
                onAction(ConnectAction.OnNameChange("Muhammed"))
                onAction(ConnectAction.OnConnectClick)
            },
            colors = ButtonDefaults.buttonColors(containerColor = Color(0xFFFF5F9E)),
            shape = RoundedCornerShape(4.dp),
            modifier = Modifier
                .height(36.dp)
                .widthIn(min = 100.dp),
            contentPadding = PaddingValues(horizontal = 8.dp, vertical = 4.dp)
        ) {
            Text(
                text = "Randevu Al",
                color = Color.White,
                fontSize = 12.sp,
                fontWeight = FontWeight.SemiBold
            )
        }
    }
}


@Composable
fun ConnectScreenn(
    state: ConnectState,
    onAction: (ConnectAction) -> Unit){

    Column(Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text="Choose a name",
            fontSize = 18.sp)
        Spacer(modifier = Modifier.height(16.dp))

        TextField(value = state.name,
            onValueChange = {
                onAction(ConnectAction.OnNameChange(it))
            },
            placeholder = {
                Text(text = "Name")
            },
            modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(16.dp))

        Button(onClick = {
            onAction(ConnectAction.OnConnectClick)
        }, modifier = Modifier.align(Alignment.End)) {
            Text("Connect")
        }

        Spacer(modifier = Modifier.height(16.dp))

        if(state.errorMessage != null){
            Text(text = state.errorMessage,
                color = Color.Red
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun ConnectScreenPreview(){
    ConnectScreen(state = ConnectState(), onAction = {})
}

class Psychologists(val namePsychologists:String, val rating: Int,val imageUser:Int )
val psychologistsList =
    listOf(
        Psychologists("Ayşe Uğurlareli",4,R.drawable.userimage),
        Psychologists("Uğur Keskin",3,R.drawable.userimage),
        Psychologists("Meltem Kuran",4,R.drawable.userimage),
        Psychologists("Fatma Şahin",5,R.drawable.userimage))