package com.VrStressRoom.vrstressroom.LoginSignup

import android.widget.Toast
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxColors
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import com.VrStressRoom.vrstressroom.R

@Composable
fun SignupScreen(modifier: Modifier=Modifier, navController: NavController, authViewModel: AuthViewModel){

    BackHandler {
        // Boş bırak -> hiçbir şey yapmaz geri tuşuna basıldığında geri gelmez.
        //Bu kısmı telefonun geri tuşundan etkilenmez.
    }

    var name by remember { mutableStateOf("") }
    var lastname by remember { mutableStateOf("") }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    var checkedKVKK by remember { mutableStateOf(false) }
    var checkedScrit by remember { mutableStateOf(false) }

    val authState =authViewModel.authState.observeAsState()
    val context = LocalContext.current

    LaunchedEffect(authState.value) {
        when(authState.value){
            is AuthState.Authenticated -> navController.navigate("MainPage")
            is AuthState.Error -> Toast.makeText(context,
                (authState.value as AuthState.Error).message,Toast.LENGTH_SHORT).show()

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
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 60.dp, start = 16.dp, end = 16.dp)
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
                Text(
                    "Kayıt Ol",
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.padding(top = 18.dp)
                )

                Row(
                    modifier = Modifier.fillMaxWidth().padding(top = 16.dp),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Button(
                        onClick = {},
                        shape = RectangleShape,
                        modifier = Modifier
                            .padding(end = 4.dp) // Sağ tarafa 4.dp boşluk
                            .size(126.dp, 40.dp)
                            .clip(RoundedCornerShape(8.dp)),
                       // Köşeleri yuvarlatılmış
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Kullanıcı")
                    }

                    Button(
                        shape = RectangleShape,
                        onClick = {},
                        modifier = Modifier
                            .padding(start = 4.dp) // Sol tarafa 4.dp boşluk
                            .size(126.dp, 40.dp)
                            .clip(RoundedCornerShape(8.dp)),
                       // Köşeleri yuvarlatılmış
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color.White,
                            contentColor = Color.Black
                        )
                    ) {
                        Text(text = "Psikolog")
                    }
                }

              /*  Image(
                    painter = painterResource(R.drawable.googlelogoicon),
                    contentDescription = null,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .size(54.dp, 54.dp)
                )
                Text(
                    text = "veya",
                    color = Color.White,
                    modifier = Modifier.padding(top = 6.dp),
                    fontSize = 13.sp
                )*/

                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp, start = 32.dp, end = 32.dp),
                    value = name,
                    onValueChange = { name = it },
                    label = {
                        Text("Adınız", color = Color.White, fontSize = 16.sp)
                    }, colors = OutlinedTextFieldDefaults.colors(
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
                        .padding(top = 8.dp, start = 32.dp, end = 32.dp),
                    value = lastname,
                    onValueChange = { lastname = it },
                    label = {
                        Text("Soyadınız", color = Color.White, modifier = Modifier, fontSize = 16.sp)
                    }, colors = OutlinedTextFieldDefaults.colors(
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
                        .padding(top = 8.dp, start = 32.dp, end = 32.dp),
                    value = username,
                    onValueChange = { username = it },
                    label = {
                        Text("Kullanıcı adınız", color = Color.White, fontSize = 16.sp)
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
                OutlinedTextField(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 8.dp, start = 32.dp, end = 32.dp),
                    value = email,
                    onValueChange = { email = it },
                    label = {
                        Text("E-mail", color = Color.White, fontSize = 16.sp)
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

                OutlinedTextField(modifier = Modifier.fillMaxWidth().padding(top = 8.dp, start = 32.dp, end = 32.dp),
                    value = password,
                    onValueChange = {
                        password = it },
                    label = {
                        Text("şifreniz", color = Color.White, fontSize = 16.sp)
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
                Row(modifier=Modifier.fillMaxWidth().padding(start = 28.dp, end = 28.dp),
                    verticalAlignment = Alignment.CenterVertically){
                    Checkbox(
                        checked = checkedScrit,
                        onCheckedChange = { checkedScrit = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black, // Onay kutusu "tikli" olduğunda siyah renk
                            uncheckedColor = Color.Gray // Onay kutusu "tikli değil" olduğunda gri renk
                        )
                    )

                    Text(text = "KVKK metninin okudum ve onaylıyorum", fontSize = 10.sp, color = if(!checkedScrit) Color.Gray else Color.White)
                }

                Row(modifier=Modifier.fillMaxWidth().padding(start = 32.dp, end = 32.dp), verticalAlignment = Alignment.CenterVertically){
                    Checkbox(checked = checkedKVKK,
                        onCheckedChange = { checkedKVKK = it },
                        colors = CheckboxDefaults.colors(
                            checkedColor = Color.Black,
                            uncheckedColor = Color.Gray,
                        )
                    )
                    Text(text = "Gizlilik sözleşmesi ve aydınlatma metnini okudum ve onaylıyorum.", fontSize = 10.sp, color = if(!checkedKVKK) Color.Gray else Color.White)
                }


                Button(
                    onClick = {
                        authViewModel.signup(name = name, lastname = lastname, username = username, email = email, password = password)
                        when(authState.value){
                            is AuthState.Authenticated -> navController.navigate("MainPage")
                            is AuthState.Error -> Toast.makeText(context,
                                "Lütfen gerekli alanları doldurun", Toast.LENGTH_SHORT).show()

                            else -> Unit
                        }
                    },
                    shape = RectangleShape,
                    modifier = Modifier.padding(start = 32.dp, top = 24.dp, end = 32.dp).clip(
                        RoundedCornerShape(4.dp)
                    ).fillMaxWidth(),// <-- Tüm köşeleri düz yapar
                    colors = ButtonDefaults.buttonColors(
                        containerColor = Color.White,
                        contentColor = Color.Black
                    )
                ) {
                    Text(text = "Kayıt Ol")
                }




                TextButton(onClick = {
                    navController.navigate("Login")
                }, modifier = Modifier.padding(top = 16.dp)) {
                    Text("Bir hesabın var mı? Giriş yap", modifier = Modifier, fontSize = 13.sp, fontWeight = FontWeight.SemiBold, color = Color.White)
                }
            }
        }

        if (authState.value is AuthState.Loading) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0x99000000)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    CircularProgressIndicator(color = Color.White)
                    Text(
                        text = "Kayıt olunuyor...",
                        color = Color.White,
                        fontSize = 16.sp,
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun displaySignup(){

}
