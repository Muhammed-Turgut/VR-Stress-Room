package com.VrStressRoom.vrstressroom.VideoCalling


sealed interface ConnectAction {
    data class OnNameChange(val name: String) : ConnectAction
    data object OnConnectClick : ConnectAction
}
