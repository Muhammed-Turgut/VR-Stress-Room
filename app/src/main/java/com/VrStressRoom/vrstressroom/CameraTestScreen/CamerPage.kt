// CameraScreen.kt
package com.VrStressRoom.vrstressroom.CameraTestScreen


import android.content.Context
import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel.Companion.CameraX_PERMISSION
import com.VrStressRoom.vrstressroom.R

@Composable
fun CameraScreen(navController: NavController) {
    val context = LocalContext.current
    val cameraViewModel: CameraViewModel = viewModel()
    val viewModel: CameraViewModel = viewModel()
    val controller = remember { LifecycleCameraController(context) }
    val bitmaps by cameraViewModel.bitmaps.collectAsState()


    // Permission launcher
    val permissionLauncher = rememberLauncherForActivityResult(
        ActivityResultContracts.RequestMultiplePermissions()
    ) { permissions ->
        val allGranted = permissions.values.all { it }
        if (!allGranted) {
            // Handle permission denial
        }
    }


    // Check and request permissions
    LaunchedEffect(Unit) {
        if (!cameraViewModel.hasRequiredPermission(context)) {
            permissionLauncher.launch(CameraX_PERMISSION)
        }
    }

    Box(modifier = Modifier.fillMaxSize()){

        CameraScreenPage(
            controller = controller,
            context = context,
            viewModel = cameraViewModel,
            bitmaps = bitmaps,
            navController = navController
        )
    }

}


@Composable
fun CameraScreenPage(
    controller: LifecycleCameraController,
    context: Context,
    viewModel: CameraViewModel,
    navController: NavController,
    bitmaps: List<Bitmap>
) {
    BackHandler {}

    val lastCapturedBitmap by viewModel.lastCapturedBitmap.collectAsState()
    val aiResponse by viewModel.aiResponse.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val cabinBold = FontFamily(Font(R.font.cabinbold, FontWeight.Bold))

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFFB3005E),
                        Color(0xFF0A007A),
                        Color(0xFFB3005E)
                    )
                )
            )
            .padding(start = 8.dp, end = 8.dp, top = 50.dp, bottom = 20.dp), // padding altı küçültüldü
        verticalArrangement = Arrangement.SpaceBetween // <- değiştirildi
    ) {
        // Üstteki Close Icon ve Başlık
        Column {
            Icon(
                imageVector = Icons.Default.Close,
                contentDescription = "Kapat",
                tint = Color.White,
                modifier = Modifier
                    .size(32.dp)
                    .clickable {
                        navController.navigate("MainPage")
                    }
                    .align(Alignment.Start)
                    .padding(4.dp)
            )
            Text(
                text = "Yüzünüzün Analizi Yapılıyor",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = Color.White,
                fontFamily = cabinBold,
                modifier = Modifier.padding(bottom = 10.dp)
            )
        }

        // Orta alan: Kamera veya Fotoğraf
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f), // <- yüksekliği esnetir
            contentAlignment = Alignment.Center
        ) {
            if (lastCapturedBitmap != null) {
                Image(
                    bitmap = lastCapturedBitmap!!.asImageBitmap(),
                    contentDescription = "Çekilen Fotoğraf",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .height(400.dp) // <-- yüksekliği azaltıldı
                        .fillMaxWidth()
                        .border(
                            width = 2.dp,
                            color = if (isLoading) Color.Green else Color.White,
                            shape = RectangleShape
                        )
                )
            } else {
                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .height(400.dp)
                        .fillMaxWidth()
                )
            }
        }

        // Alt: Butonlar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 10.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Kamera Değiştir
                Box(
                    modifier = Modifier.weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Image(
                        painter = painterResource(R.drawable.camerarotate),
                        contentDescription = "Kamera Değiştir",
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {
                                controller.cameraSelector =
                                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    else
                                        CameraSelector.DEFAULT_BACK_CAMERA
                            }
                    )
                }

                // Fotoğraf Çek
                Image(
                    painter = painterResource(R.drawable.takepohotobutton),
                    contentDescription = "Fotoğraf Çek",
                    modifier = Modifier
                        .size(60.dp)
                        .clickable {
                            viewModel.takePhoto(controller, context)
                        }
                )

                // Devam Et Butonu
                val bitmap = lastCapturedBitmap
                if (bitmap != null) {
                    Box(
                        modifier = Modifier.weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Image(
                            painter = painterResource(R.drawable.checkicon),
                            contentDescription = "Devam Et",
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    viewModel.onTakePhoto(bitmap)
                                    if (isLoading) {
                                        navController.navigate("AiQuizScreen")
                                        viewModel.saveResponseToPreferences(
                                            context,
                                            response = aiResponse.toString()
                                        )
                                    }
                                }
                        )
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }

        // AI yanıt geldiğinde yükleme başlasın
        LaunchedEffect(aiResponse) {
            if (!aiResponse.isNullOrEmpty()) {
                isLoading = true
            }
        }
    }
}








