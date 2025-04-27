// CameraScreen.kt
package com.VrStressRoom.vrstressroom.CameraTestScreen


import android.content.Context
import android.graphics.Bitmap
import androidx.activity.compose.BackHandler
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.input.pointer.motionEventSpy
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.NavHostController
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

    val botResponse by viewModel.aiResponse.collectAsState()
    val lastCapturedBitmap by viewModel.lastCapturedBitmap.collectAsState()
    val aiResponse by viewModel.aiResponse.collectAsState()
    var isLoading by remember { mutableStateOf(false) }
    val cabinBold = FontFamily(
        Font(R.font.cabinbold, FontWeight.Bold)
    )
    BackHandler {

    }

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
            .padding(start = 8.dp, end = 8.dp, top = 90.dp, bottom = 50.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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
                .zIndex(2f) // Yüksek z-index
        )

        Text(text = "Yüzünüzün Analizi Yapılıyor",
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold,
            color = Color.White,
            fontFamily = cabinBold,
            modifier = Modifier.padding(bottom = 10.dp))
        Box(
            modifier = Modifier.fillMaxWidth(),
            contentAlignment = Alignment.Center
        ) {
            if (lastCapturedBitmap != null) {
                // Görüntü gösteriliyor
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp)
                ) {
                    Icon(
                        imageVector = Icons.Default.Close,
                        contentDescription = "Kapat",
                        tint = Color.White,
                        modifier = Modifier
                            .align(Alignment.TopEnd)
                            .size(32.dp)
                            .clickable {
                                viewModel.clearLastCapturedBitmap()
                                isLoading=!isLoading
                            }
                            .padding(4.dp)
                            .zIndex(2f) // Yüksek z-index
                    )

                    Image(
                        bitmap = lastCapturedBitmap!!.asImageBitmap(),
                        contentDescription = "Çekilen Fotoğraf",
                        contentScale = ContentScale.Crop,
                        modifier = Modifier
                            .align(Alignment.Center)
                            .height(600.dp)
                            .width(380.dp)
                            .border(
                                width = 2.dp,
                                color = if (isLoading) Color.Green else Color.White,
                                shape = RectangleShape
                            )
                            .zIndex(1f) // Daha düşük z-index
                    )
                }



            } else {
                // Kamera aktif
                CameraPreview(
                    controller = controller,
                    modifier = Modifier
                        .height(600.dp)
                        .width(380.dp)
                )
            }

        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 20.dp),
            contentAlignment = Alignment.Center
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                // Sağdaki Kamera Değiştir Butonu
                Box(
                    modifier = Modifier
                        .weight(1f),
                    contentAlignment = Alignment.CenterEnd
                ) {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .clickable {
                                controller.cameraSelector =
                                    if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA)
                                        CameraSelector.DEFAULT_FRONT_CAMERA
                                    else
                                        CameraSelector.DEFAULT_BACK_CAMERA
                            },
                        contentAlignment = Alignment.Center
                    ) {
                        Image(
                            painter = painterResource(R.drawable.camerarotate),
                            contentDescription = "Kamera Değiştir",
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }

                // Ortadaki Fotoğraf Çek Butonu
                Box(
                    modifier = Modifier
                        .size(60.dp)
                        .align(Alignment.CenterVertically)
                        .clickable {
                            viewModel.takePhoto(controller = controller, context = context)
                        },
                    contentAlignment = Alignment.Center
                ) {
                    Image(
                        painter = painterResource(R.drawable.takepohotobutton),
                        contentDescription = "Fotoğraf Çek",
                        modifier = Modifier.fillMaxSize()
                    )
                }
                val bitmap=lastCapturedBitmap //burda mantıken tür dönüşümü yaptık.
                // Soldaki Check Icon (Varsa)

                LaunchedEffect(aiResponse) {
                    if (!aiResponse.isNullOrEmpty()) {
                        isLoading=true
                    }
                }

                if (bitmap != null) {

                    Box(
                        modifier = Modifier
                            .weight(1f),
                        contentAlignment = Alignment.CenterStart
                    ) {
                        Box(
                            modifier = Modifier
                                .size(45.dp)
                                .clickable {
                                    viewModel.onTakePhoto(bitmap)
                                    if (isLoading){
                                        navController.navigate("AiQuizScreen")
                                        viewModel.saveResponseToPreferences(context, response = aiResponse.toString())
                                    }

                                },
                            contentAlignment = Alignment.Center
                        ) {
                            Image(
                                painter = painterResource(R.drawable.checkicon),
                                contentDescription = "Devam Et",
                                modifier = Modifier.fillMaxSize()
                            )
                        }
                    }
                } else {
                    Spacer(modifier = Modifier.weight(1f)) // Yer tutucu
                }


            }
        }




    }
}







