package com.VrStressRoom.vrstressroom.VideoCalling.Video

import java.lang.Error


data class VideoCallState(
    val call: io.getstream.video.android.core.Call,
    val callState: CallState? = null,
    val error: String? = null
)

enum class CallState{
    JOINING,
    ACTIVE,
    ENDED
}