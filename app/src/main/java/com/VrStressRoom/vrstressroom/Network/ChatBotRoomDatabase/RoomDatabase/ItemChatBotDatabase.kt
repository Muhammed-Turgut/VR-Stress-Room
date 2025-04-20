package com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.RoomDatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.ItemChatBotList

@Database(entities = [ItemChatBotList::class], version = 1)
abstract class ItemChatBotDatabase : RoomDatabase() {
    abstract fun ItemChatBotDao(): ItemChatBotDao
}