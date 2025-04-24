package com.VrStressRoom.vrstressroom.VideoCalling.Video


import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.getstream.video.android.core.StreamVideo
import kotlinx.coroutines.launch

class VideoCallViewModel(
    private val videoClient:StreamVideo
) : ViewModel(){

    var state by mutableStateOf(VideoCallState(
        call = videoClient.call(type = "default", id = "main-room"))
    )
        private set

    fun onAction(action: VideoCallAction){
        when(action){
            VideoCallAction.JoinCall -> {

            }
            VideoCallAction.OnDisconnectClick -> {

            }
        }
    }

    private fun joinCall(){
        if(state.callState == CallState.ACTIVE){
            return
        }
        viewModelScope.launch {
            state = state.copy(callState = CallState.JOINING)
            val sholudCreate = videoClient.queryCalls(filters = emptyMap())
                .getOrNull()?.calls?.isEmpty() ==true

            //Burda kaldım 40.39

            state.call.join(create = sholudCreate).onSuccess {
                state = state.copy(callState = CallState.ACTIVE,
                    error = null)
            }.onError {
                state = state.copy(
                    error = it.message,
                    callState = null
                )
            }
        }
    }

}