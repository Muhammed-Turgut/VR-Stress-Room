package com.VrStressRoom.vrstressroom.LoginSignup

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
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

@Composable
fun LoginScreen(modifier: Modifier = Modifier,
                navController: NavController, // Kullanılacak NavController
                authViewModel: AuthViewModel
) {

    BackHandler {
        // Boş bırak -> hiçbir şey yapmaz geri tuşuna basıldığında geri gelmez.
        //Bu kısmı telefonun geri tuşundan etkilenmez.
    }

    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedLoginOption by remember { mutableStateOf(true) }

    val authState =authViewModel.authState.observeAsState()
    val context = LocalContext.current
    val cabinFontFamily = FontFamily(
        Font(R.font.cabinbold, FontWeight.Bold)
    )

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("MainPage")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message, Toast.LENGTH_SHORT).show()

            else -> Unit
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF060047))
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                modifier = Modifier.padding(top = 64.dp),
                textAlign = TextAlign.Center,
                text = "VR Stres\nOdası",
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 40.sp,
                lineHeight = 48.sp
            )

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    .clip(RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp))
                    .background(
                        brush = Brush.verticalGradient(
                            colors = listOf(
                                Color(0xFF060047),
                                Color(0xFFB3005E)
                            )
                        ),
                        shape = RoundedCornerShape(topStart = 16.dp, topEnd = 16.dp)
                    )
            ) {


                Row(
                    modifier = Modifier.fillMaxWidth().padding(start = 16.dp, top = 16.dp, end = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {
                            selectedLoginOption = !selectedLoginOption
                        },
                        shape = RectangleShape,
                        modifier = Modifier
                            .padding(end = 4.dp) // Sağ tarafa 4.dp boşluk
                            .size(126.dp, 40.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        // Köşeleri yuvarlatılmış
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(selectedLoginOption) Color.White else Color.Transparent,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Kullanıcı", color = if(selectedLoginOption) Color.Black else Color.White)
                    }

                    Button(
                        shape = RectangleShape,
                        onClick = {
                            selectedLoginOption = !selectedLoginOption
                        },
                        modifier = Modifier
                            .padding(start = 4.dp) // Sol tarafa 4.dp boşluk
                            .size(126.dp, 40.dp)
                            .clip(RoundedCornerShape(8.dp)),
                        // Köşeleri yuvarlatılmış
                        colors = ButtonDefaults.buttonColors(
                            containerColor = if(selectedLoginOption) Color.Transparent else Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Psikolog", color = if(selectedLoginOption) Color.White else Color.Black)
                    }
                }


                Image(
                    painter = painterResource(R.drawable.loginicon),
                    contentDescription = null,
                    modifier = Modifier.padding(top=32.dp).size(32.dp))
                Text(
                    "Giriş Yap",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontFamily = cabinFontFamily,
                    modifier = Modifier.padding(top = 8.dp)
                )
                Text(text="Hesabınıza giriş yapın",
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color.White,
                    modifier = Modifier.padding(top = 12.dp)
                )

                /*Image(
                    painter = painterResource(R.drawable.googlelogoicon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 32.dp)
                        .size(54.dp, 54.dp)
                )
                Text(
                    text = "veya",
                    color = Color.White,
                    modifier = Modifier.padding(top = 8.dp),
                    fontSize = 13.sp
                )*/


                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text("E-mail", color = Color.White)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Email,
                            contentDescription = "Email Icon",
                            tint = Color.White
                        )
                    },colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color(0xFFB7B7B7),
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color(0xFFB7B7B7),
                        focusedLeadingIconColor = Color.White,
                        unfocusedLeadingIconColor = Color(0xFFB7B7B7),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color(0xFFB7B7B7)
                    )

                )

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    value = password,
                    onValueChange = { password = it },
                    label = {
                        Text("Şifreniz", color = Color.White)
                    },
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Default.Lock,
                            contentDescription = "Lock Icon",
                            tint = Color.White
                        )
                    },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = Color.White,
                        unfocusedTextColor = Color(0xFFB7B7B7),
                        cursorColor = Color.White,
                        focusedBorderColor = Color.White,
                        unfocusedBorderColor = Color(0xFFB7B7B7),
                        focusedLeadingIconColor = Color.White,
                        unfocusedLeadingIconColor = Color(0xFFB7B7B7),
                        focusedLabelColor = Color.White,
                        unfocusedLabelColor = Color(0xFFB7B7B7)
                    )
                )


                Button(
                            onClick = {
                                authViewModel.login(email,password)
                                 if(AuthState.Authenticated != null){
                                     navController.navigate("MainPage")
                                 }
                            },
                            shape = RectangleShape,
                            modifier = Modifier.padding(start = 32.dp, top = 24.dp, end = 32.dp).clip(RoundedCornerShape(4.dp)).fillMaxWidth(),// <-- Tüm köşeleri düz yapar
                            colors = ButtonDefaults.buttonColors(
                                containerColor = Color.White,
                                contentColor = Color.Black
                            )
                        ) {
                            Text(text = "Giriş Yap")
                        }




                TextButton(onClick = {
                    navController.navigate("Signup")
                }, modifier = Modifier.padding(top = 16.dp)) {
                    Text("Bir hesabın yok mu? Hesap Oluştur", modifier = Modifier, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }
    }
}


@Preview(showBackground = true)
@Composable
fun dispalyLogin(){

}
