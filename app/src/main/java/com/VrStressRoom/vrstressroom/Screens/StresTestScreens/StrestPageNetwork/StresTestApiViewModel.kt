package com.VrStressRoom.vrstressroom.Screens.StresTestScreens.StrestPageNetwork

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException

class StressViewModel : ViewModel() {

    private val _stressResult = MutableLiveData<Result<StressResponse>>()
    val stressResult: LiveData<Result<StressResponse>> = _stressResult

    private val exceptionHandler = CoroutineExceptionHandler { _, throwable ->
        _stressResult.postValue(Result.failure(throwable))
    }

    var isRequestCompleted by mutableStateOf(false)
        private set

    fun evaluateStress(request: StressRequest) {
        viewModelScope.launch(Dispatchers.IO + exceptionHandler) {
            try {
                val response = StressTestRetrofit.instance.evaluateStress(request).execute()
                if (response.isSuccessful) {
                    val body = response.body()
                    if (body != null) {
                        _stressResult.postValue(Result.success(body))
                    } else {
                        _stressResult.postValue(Result.failure(Exception("Boş Cevap Geldi")))
                    }
                } else {
                    val errorBody = response.errorBody()?.string()
                    _stressResult.postValue(Result.failure(Exception("Hata: $errorBody")))
                }
            } catch (e: Exception) {
                _stressResult.postValue(Result.failure(e))
            } finally {
                isRequestCompleted = true
            }
        }
    }

    // Ekstra yardımcı fonksiyonlar
    fun extractFirstNumber(text: String): Int? {
        val regex = Regex("\\d+")
        val matchResult = regex.find(text)
        return matchResult?.value?.toInt()
    }

    fun parseGender(text: String): String {
        return when {
            text.contains("Man", ignoreCase = true) -> "Erkek"
            text.contains("Woman", ignoreCase = true) -> "Kadın"
            else -> "Bilinmiyor"
        }
    }

    fun parseEmotion(text: String): String {
        return when {
            text.contains("happy", ignoreCase = true) -> "mutlu"
            text.contains("sad", ignoreCase = true) -> "üzgün"
            text.contains("neutral", ignoreCase = true) -> "nötr"
            text.contains("angry", ignoreCase = true) -> "sinirli"
            text.contains("surprised", ignoreCase = true) -> "şaşkın"
            else -> "belirsiz"
        }
    }
}
