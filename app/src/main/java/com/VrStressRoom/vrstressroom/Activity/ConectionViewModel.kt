package com.VrStressRoom.vrstressroom.Activity

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel

class ConnectivityViewModel(application: Application) : AndroidViewModel(application) {
    //Bu sayfa internet bağlantısının olup olmadığını test ettiğimiz sayfa.

    private val _isConnected = mutableStateOf(true)
    val isConnected: State<Boolean> = _isConnected

    fun checkConnection() {
        _isConnected.value = isInternetAvailable(getApplication())
    }

    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}
