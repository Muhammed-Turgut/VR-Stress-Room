package com.VrStressRoom.vrstressroom.VideoCalling.Video

sealed interface VideoCallAction {
    data object OnDisconnectClick: VideoCallAction
    data object JoinCall: VideoCallAction
}