package com.VrStressRoom.vrstressroom.CameraTestScreen

import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.lifecycle.compose.LocalLifecycleOwner

@Composable
fun CameraPreview(
    controller: LifecycleCameraController,
    modifier: Modifier = Modifier
) {
    val lifecycleOwner = LocalLifecycleOwner.current

    Box(
        modifier = modifier
            .padding(horizontal = 4.dp) // <- Buraya taşıyoruz
            .clip(RoundedCornerShape(40.dp))
            .border(1.dp, Color(0xFFEEEEEE), RoundedCornerShape(40.dp))
    ) {
        AndroidView(
            factory = {
                PreviewView(it).apply {
                    this.controller = controller
                    controller.bindToLifecycle(lifecycleOwner)

                    clipToOutline = true
                    clipToPadding = true
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(600.dp)
        )

        // Overlay'i üstten geçiriyoruz
        MovingLineOverlay(
            modifier = Modifier
                .matchParentSize()
                .padding(horizontal = 4.dp) // İsteğe bağlı overlay paddingle sınırlanabilir
        )
    }
}


@Composable
fun MovingLineOverlay(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "lineAnimation")

    // offset değeri ekran boyuna göre 0f - 1f arasında olacak
    val offsetRatio by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 2000, easing = LinearEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "lineOffset"
    )

    Canvas(
        modifier = modifier
            .fillMaxWidth()
            .fillMaxHeight()
            .padding(start = 8.dp, end = 8.dp)// Tam ekran boyu
    ) {
        val yPos = size.height * offsetRatio

        drawLine(
            color = Color.White,
            start = Offset(0f, yPos),
            end = Offset(size.width, yPos),
            strokeWidth = 4f
        )
    }
}
