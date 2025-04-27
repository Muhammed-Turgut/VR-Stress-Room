package com.VrStressRoom.vrstressroom.VideoCalling.di

import androidx.lifecycle.viewmodel.compose.viewModel
import com.VrStressRoom.vrstressroom.VideoCalling.ConnectViewModel
import com.VrStressRoom.vrstressroom.VideoCalling.Video.VideoCallViewModel
import com.VrStressRoom.vrstressroom.VideoCalling.VideoCallingApp
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module{

    single {
        val app = androidContext().applicationContext as VideoCallingApp
        app.client
    }
    viewModelOf(::ConnectViewModel)
    viewModelOf(::VideoCallViewModel)
}