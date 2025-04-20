// CameraScreen.kt
package com.VrStressRoom.vrstressroom.CameraTestScreen

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.camera.core.CameraSelector
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.VrStressRoom.vrstressroom.CameraTestScreen.CameraViewModel.Companion.CameraX_PERMISSION
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreen() {
    val context = LocalContext.current
    val cameraViewModel: CameraViewModel = viewModel()
    val viewModel: CameraViewModel = viewModel()
    val botYaniti by viewModel.aiResponse.collectAsState()
    val controller = remember { LifecycleCameraController(context) }
    val bitmaps by cameraViewModel.bitmaps.collectAsState()

    println("bot yanıtı: ${botYaniti}")
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

    CameraScreenPage(
        controller = controller,
        context = context,
        viewModel = cameraViewModel,
        bitmaps = bitmaps
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CameraScreenPage(
    controller: LifecycleCameraController,
    context: Context,
    viewModel: CameraViewModel,
    bitmaps: List<Bitmap>
) {
    val scope = rememberCoroutineScope()
    val scaffoldState = rememberBottomSheetScaffoldState()

    BottomSheetScaffold(
        scaffoldState = scaffoldState,
        sheetPeekHeight = 0.dp,
        sheetContent = {
            PhotoBottomSheetContent(
                bitmaps = bitmaps,
                modifier = Modifier.fillMaxWidth()
            )
        }
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            CameraPreview(
                controller = controller,
                modifier = Modifier.fillMaxSize()
            )



            // Toggle camera button
            IconButton(
                onClick = {
                    controller.cameraSelector = if (controller.cameraSelector == CameraSelector.DEFAULT_BACK_CAMERA) {
                        CameraSelector.DEFAULT_FRONT_CAMERA
                    } else {
                        CameraSelector.DEFAULT_BACK_CAMERA
                    }
                },
                modifier = Modifier.offset(16.dp, 16.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Phone,
                    contentDescription = "Switch Camera"
                )
            }

            // Bottom controls
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                // Gallery button
                IconButton(
                    onClick = {
                        scope.launch {
                            scaffoldState.bottomSheetState.expand()
                        }
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountBox,
                        contentDescription = "Open Gallery"
                    )
                }

                // Capture button
                IconButton(
                    onClick = {
                        viewModel.takePhoto(
                            controller = controller,
                            context = context
                        )
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.AccountCircle,
                        contentDescription = "Take Photo"
                    )
                }
            }
        }
    }
}