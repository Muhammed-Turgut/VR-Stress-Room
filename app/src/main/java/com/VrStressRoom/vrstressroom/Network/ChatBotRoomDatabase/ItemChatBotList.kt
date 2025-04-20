package com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "chatBotAllMessage")
data class ItemChatBotList(
    @ColumnInfo(name = "sender")
    val senderMessege:String?,
    @ColumnInfo(name = "contentsMessage")
    val contentsMessage:String?,

    @ColumnInfo(name = "dateMessage")
    val dateTime:String?

) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}