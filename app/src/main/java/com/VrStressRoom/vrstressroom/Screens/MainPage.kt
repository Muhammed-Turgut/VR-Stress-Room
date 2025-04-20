package com.VrStressRoom.vrstressroom.Screens

import android.content.Context
import androidx.activity.compose.BackHandler
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraScreen
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraScreenPage

import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel
import com.VrStressRoom.vrstressroom.LoginSignup.AuthViewModel
import com.VrStressRoom.vrstressroom.R
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@Composable
fun MainPage(modifier: Modifier = Modifier, navControllerer: NavController, authViewModel: AuthViewModel){
    BackHandler {
        // Boş bırak -> hiçbir şey yapmaz geri tuşuna basıldığında geri gelmez.
        //Bu kısmı telefonun geri tuşundan etkilenmez.
    }

    val navController= rememberNavController()

    Scaffold(modifier=Modifier.fillMaxSize(),
        bottomBar = {
            NavigationBottomBar(navController)
        }){ paddingValues ->
        NavHost(
            navController=navController,
            startDestination = "HomeScreen",
            modifier = Modifier.fillMaxSize().padding(paddingValues))
        {
          composable ("HomeScreen"){
                 HomeScreen()
          }

            composable ("StressTest"){

                StresTestScreen()

            }

            composable ("VRRoom"){
                 VRRoomScreen()
            }

            composable ("ChatBot"){
                navControllerer.navigate("AıChatBot")
            }

            composable ("Profil"){
                  ProfilDetailsScreen()
            }


        }

    }
}

//Navigation Bottom bar
@Composable
fun NavigationBottomBar(navController: NavHostController) {
    val systemUiController = rememberSystemUiController()

    LaunchedEffect(Unit) {
        systemUiController.setNavigationBarColor(Color.White) // veya Color.Transparent
    }

    val items = listOf(
        BottomNavigationItem(
            title = "Ana Sayfa",
            selectedIcon = R.drawable.homeiconselected,
            unSelectedIcon = R.drawable.homeicondefault,
            route = "HomeScreen",
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Stres Test",
            selectedIcon = R.drawable.strestesticonselected,
            unSelectedIcon = R.drawable.strestesticondefault,
            route = "StressTest",
            hasNews = false,

            ),
        BottomNavigationItem(
            title = "VR Odaları",
            selectedIcon = R.drawable.vrroomiconselcted,
            unSelectedIcon = R.drawable.vrroomicondefault,
            route = "VRRoom",
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Chat BOT",
            selectedIcon = R.drawable.chatboticonselected,
            unSelectedIcon = R.drawable.chatboticondefault,
            route = "ChatBot",
            hasNews = false
        ),
        BottomNavigationItem(
            title = "Profil",
            selectedIcon = R.drawable.usericonselected,
            unSelectedIcon = R.drawable.usericondefault,
            route = "Profil",
            hasNews = false
        )
    )

    var selectedItemIndex by remember { mutableStateOf(0) }
    NavigationBar(
        containerColor = Color.White,
        modifier = Modifier
            .background(Color.White)
            .padding(start = 8.dp, end = 8.dp),
    ) {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                selected = selectedItemIndex == index,
                onClick = {
                    selectedItemIndex = index
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                label = {
                    Text(text = item.title, fontSize = 10.sp, color = Color(0xFFB3005E))
                },
                alwaysShowLabel = false,
                icon = {
                    BadgedBox(badge = {
                        if (item.badgeCount != null) {
                            Badge {
                                Text(text = item.badgeCount.toString())
                            }
                        } else if (item.hasNews) {
                            Badge()
                        }
                    }) {
                        Image(
                            painter = painterResource(id = if (selectedItemIndex == index) item.selectedIcon else item.unSelectedIcon),
                            contentDescription = item.title,
                            modifier = Modifier.size(
                                when (item.route) {
                                    "MainPage" -> 28.dp
                                    "VRRoom" -> 42.dp // VRRoom için özel boyut
                                    else -> 24.dp
                                }
                            )
                        )
                    }
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedTextColor = MaterialTheme.colorScheme.tertiaryContainer
                )
            )
        }
    }
}