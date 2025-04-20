package com.VrStressRoom.vrstressroom.Network

import BotRequest
import android.app.Application
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.room.Room
import com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.ItemChatBotList
import com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.RoomDatabase.ItemChatBotDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ChatViewModel(application: Application) : AndroidViewModel(application){

    private val db = Room.databaseBuilder(getApplication(),
        ItemChatBotDatabase::class.java,
        "ChatMessage").build()

    private val itemChatBotDao = db.ItemChatBotDao()
    private val _itemChatBotList = MutableStateFlow<List<ItemChatBotList>>(emptyList())
    val itemListChatBot : StateFlow<List<ItemChatBotList>> = _itemChatBotList
    val selectedItem = mutableStateOf(ItemChatBotList("","",""))


    init {
        getItemList()
    }

    //Bu verileri listeleme methodu
     fun getItemList() {
        viewModelScope.launch(Dispatchers.IO) {
            try{
                val items=itemChatBotDao.getItemWithNameAndId()
                _itemChatBotList.value=items
            }catch (e:Exception){
                println("Eror:${e.message}")
            }
        }
    }

    //Bu verileri Kaydetme fonksiyonu
     fun saveItem(item:ItemChatBotList){
        viewModelScope.launch(Dispatchers.IO) {
            itemChatBotDao.insert(item)
            val updateItems=itemChatBotDao.getItemWithNameAndId()
            _itemChatBotList.value=updateItems
        }
    }



     fun deleteAllItem(){
        viewModelScope.launch(Dispatchers.IO) {
            itemChatBotDao.deleteAll()
            val updateItems=itemChatBotDao.getItemWithNameAndId()
            _itemChatBotList.value=updateItems
        }
    }

    var messages = mutableStateListOf<Pair<String, String>>() // Pair<kullanıcı, bot>
        private set

    fun sendMessageToBot(userMessage: String) {
        viewModelScope.launch {
            try {
                // Kullanıcı mesajını listeye ekle
                messages.add(Pair("user", userMessage))

                // Önceki mesajları bir listeye dönüştür
                val previousMessages = messages.map {
                    mapOf("user" to it.first, "bot" to it.second)
                }

                // BotRequest içeriğini hazırlayın (önceki mesajlar dahil)
                val request = BotRequest(previousMessages = previousMessages, message = userMessage)
                val response = BotRetrofitInstance.api.sendMessage(request)

                // Bot cevabını listeye ekle
                response.response?.let {
                    saveItem(ItemChatBotList("Bot",it,getCurrentTime()))
                    messages.add(Pair("bot", it))
                } ?: run {
                    messages.add(Pair("bot", "Bot bir cevap üretemedi."))
                }
            } catch (e: Exception) {
                messages.add(Pair("bot", "Hata oluştu: ${e.localizedMessage}"))
            }
        }
    }

    //Saat bilgisini alıyor.
    fun getCurrentTime(): String {
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("HH:mm") // veya "yyyy-MM-dd HH:mm:ss"
        return current.format(formatter)
    }
}
