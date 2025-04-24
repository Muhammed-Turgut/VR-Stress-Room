package com.VrStressRoom.vrstressroom.VideoCalling

import android.app.Application
import io.getstream.video.android.core.StreamVideo
import io.getstream.video.android.core.StreamVideoBuilder
import io.getstream.video.android.model.User
import io.getstream.video.android.model.UserType

class VideoCallingApp:Application(){
    private var currentName:String? = null
    var client:StreamVideo? =null

    fun initVideoClint(userName:String){
        if(client == null || userName != currentName){
            StreamVideo.removeClient()
            currentName = userName

            client = StreamVideoBuilder(
                context = this,
                apiKey = "d9hztjevfyer",
                user=User(id = userName,
                    name = userName,
                    type = UserType.Guest
                )
            ).build()
        }
    }
}