package com.VrStressRoom.vrstressroom.VideoCalling

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.AndroidViewModel

class ConnectViewModel(
    private val app: Application):AndroidViewModel(app) {

        var state by mutableStateOf(ConnectState())
            private set

    fun onAction(action: ConnectAction){
        when(action){
            ConnectAction.OnConnectClick ->{
               connectToRoom()
            }
            is ConnectAction.OnNameChange ->{
                 state = state.copy(name = action.name)
            }
        }
    }

    private fun connectToRoom() {
        state = state.copy(errorMessage = null)
        if(state.name.isBlank()){
            state = state.copy(
                errorMessage = "The username can't be blank."
            )
            return
        }
        (app as VideoCallingApp).initVideoClint(state.name)

        state = state.copy(isConnecting = true)

    }
}