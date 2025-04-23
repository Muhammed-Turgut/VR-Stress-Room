package com.VrStressRoom.vrstressroom.CameraTestScreen

import android.content.Context
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Matrix
import android.util.Log
import androidx.camera.core.ImageCapture.OnImageCapturedCallback
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.ImageProxy
import androidx.camera.view.LifecycleCameraController
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.VrStressRoom.vrstressroom.CameraTestScreen.PhotoAISend.CameraBotRetrofitInstances
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

class CameraViewModel : ViewModel() {

    private val _bitmaps = MutableStateFlow<List<Bitmap>>(emptyList())
    val bitmaps = _bitmaps.asStateFlow()

    private val _aiResponse = MutableStateFlow<String?>(null)
    val aiResponse = _aiResponse.asStateFlow()

    private val _lastCapturedBitmap = MutableStateFlow<Bitmap?>(null)
    val lastCapturedBitmap: StateFlow<Bitmap?> = _lastCapturedBitmap


    fun onTakePhoto(bitmap: Bitmap) {
        _bitmaps.value += bitmap
        _lastCapturedBitmap.value = bitmap
        sendImageToServer(bitmap)
    }

    //Son çekilen fotoğrafın alanını temizler.
    fun clearLastCapturedBitmap() {
        _lastCapturedBitmap.value = null
    }

    fun takePhoto(
        controller: LifecycleCameraController,
        context: Context,
    ) {
        controller.takePicture(
            ContextCompat.getMainExecutor(context),
            object : OnImageCapturedCallback() {
                override fun onCaptureSuccess(image: ImageProxy) {
                    super.onCaptureSuccess(image)
                    val matrix = Matrix().apply {
                        postRotate(image.imageInfo.rotationDegrees.toFloat())
                        postScale(-1f, 1f) // Ön kamera için yansıtma
                    }

                    val rotatedBitmap = Bitmap.createBitmap(
                        image.toBitmap(),
                        0, 0,
                        image.width,
                        image.height,
                        matrix,
                        true
                    )
                    image.close()
                    onTakePhoto(rotatedBitmap)
                }

                override fun onError(exception: ImageCaptureException) {
                    super.onError(exception)
                    Log.e("Camera", "Couldn't take photo:", exception)
                }
            }
        )
    }

     fun sendImageToServer(bitmap: Bitmap) {
        viewModelScope.launch {
            val file = bitmapToFile(bitmap) ?: return@launch

            val requestFile = file.asRequestBody("image/jpg".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            try {
                val response = CameraBotRetrofitInstances.api.sendImage(body)
                if (response.isSuccessful) {
                    _aiResponse.value = response.body()?.response
                } else {
                    Log.e("AI", "Response failed: ${response.errorBody()?.string()}")
                    _aiResponse.value = "Sunucu hatası: ${response.code()}"
                }
            } catch (e: Exception) {
                e.printStackTrace()
                _aiResponse.value = "Bağlantı hatası: ${e.localizedMessage}"
            }
        }
    }

    private fun bitmapToFile(bitmap: Bitmap): File? {
        return try {
            val file = File.createTempFile("image", ".jpg")
            val out = FileOutputStream(file)
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out)
            out.flush()
            out.close()
            file
        } catch (e: IOException) {
            e.printStackTrace()
            null
        }
    }

    fun hasRequiredPermission(context: Context): Boolean {
        return CameraX_PERMISSION.all {
            ContextCompat.checkSelfPermission(context, it) == PackageManager.PERMISSION_GRANTED
        }
    }

    companion object {
        val CameraX_PERMISSION = arrayOf(
            android.Manifest.permission.CAMERA,
            android.Manifest.permission.RECORD_AUDIO,
        )
    }
}
