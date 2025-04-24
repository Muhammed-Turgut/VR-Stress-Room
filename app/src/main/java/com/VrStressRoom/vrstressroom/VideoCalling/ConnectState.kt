package com.VrStressRoom.vrstressroom.VideoCalling

data class ConnectState(
    val name:String = "",
    val isConnecting: Boolean = false,
    val errorMessage: String? = null
)
