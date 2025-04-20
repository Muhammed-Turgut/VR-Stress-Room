package com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.RoomDatabase

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.ItemChatBotList

@Dao
interface ItemChatBotDao {
    @Query("SELECT * FROM chatBotAllMessage ORDER BY id DESC")
    suspend fun getItemWithNameAndId(): List<ItemChatBotList>

    @Query("SELECT * FROM chatBotAllMessage WHERE id = :id")
    suspend fun getItemById(id: Int): ItemChatBotList?

    @Insert
    suspend fun insert(item: ItemChatBotList)

    @Delete
    suspend fun delete(item: ItemChatBotList)

    @Update
    suspend fun update(item: ItemChatBotList)

    @Query("DELETE FROM chatBotAllMessage")
    suspend fun deleteAll()  // 🔥 Tüm verileri siler
}