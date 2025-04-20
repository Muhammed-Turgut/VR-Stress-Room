package com.VrStressRoom.vrstressroom.Network

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.VrStressRoom.vrstressroom.Activity.ConnectivityViewModel
import com.VrStressRoom.vrstressroom.Activity.ErorPages.NoInternetScreen
import com.VrStressRoom.vrstressroom.Network.ChatBotRoomDatabase.ItemChatBotList
import com.VrStressRoom.vrstressroom.R
import com.VrStressRoom.vrstressroom.Screens.HomeScreenHeader


@Composable
fun ChatBotScreenPage(viewModel: ChatViewModel= viewModel(), navController: NavController,connectViewModel: ConnectivityViewModel = viewModel()) {


    //Önceden göderilen mesajlaarın listelendiği kısım.
    LaunchedEffect(Unit) {
        viewModel.getItemList()
    }
    val itemList by viewModel.itemListChatBot.collectAsState(initial = emptyList())


    Box(modifier = Modifier.fillMaxSize()) {

        val isConnected by connectViewModel.isConnected

        LaunchedEffect(Unit) {
            connectViewModel.checkConnection()
        }

        if (isConnected) {
            BackHandler {
                // Boş bırak -> hiçbir şey yapmaz geri tuşuna basıldığında geri gelmez.
            }

            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 80.dp)
                    .background(Color.White), // Alt kısım boş kalır (input için)
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Row(modifier = Modifier.fillMaxWidth().padding(top = 64.dp, start = 16.dp, end = 16.dp)){


                    IconButton(
                        onClick = {
                            navController.navigate("MainPage")
                        },
                        modifier = Modifier.align(Alignment.Top)
                    ) {
                        Image(painter = painterResource(R.drawable.backchatboticon),
                            contentDescription = null,
                            alignment = Alignment.TopCenter,
                            modifier = Modifier.size(32.dp))
                    }

                    Text(modifier = Modifier.weight(1f),
                        text = "AI Chat Bot",
                        fontSize = 24.sp,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center

                    )

                    IconButton(
                        onClick = {
                            viewModel.deleteAllItem()
                        },
                        modifier = Modifier.align(Alignment.Top)
                    ) {
                        Image(painter = painterResource(R.drawable.trashiconbutton),
                            contentDescription = null,
                            alignment = Alignment.TopCenter,
                            modifier = Modifier.size(32.dp))
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                LazyColumn(
                    modifier = Modifier
                        .weight(1f)
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp),
                    reverseLayout = true
                ) {
                    items(itemList) { message ->
                        MessageBubble(sender = message.senderMessege?:"gelmedi", text = message.contentsMessage?: "gelmedi", dateTime = message.dateTime?:"00:00")
                    }
                }

                // Alt input alanı
                MessageInput(onMessageSend = {
                    viewModel.sendMessageToBot(it)
                }, saveItem = { item ->
                    viewModel.saveItem(item)
                }, dateTime = {
                    viewModel.getCurrentTime()
                })
            }

        } else {
            NoInternetScreen(onRetry = {
                connectViewModel.checkConnection()
            })
        }
    }
}



@Composable
fun MessageInput(onMessageSend: (String) -> Unit,saveItem : (ItemChatBotList) -> Unit,dateTime : () -> String) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        OutlinedTextField(
            value = message,
            onValueChange = { message = it },
            placeholder = {
                Text(
                    text = "Mesajınızı buraya yazın",
                    color = Color(0xFF6F6F6F),
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Normal
                )
            },
            modifier = Modifier
                .background(Color(0xFFFFDDEF))
                .weight(1f)
                .clip(RoundedCornerShape(4.dp))
        )

        IconButton(
            onClick = {
                onMessageSend(message)
                saveItem(ItemChatBotList("isUser",message, dateTime()))
                message = ""
            },
            modifier = Modifier.align(Alignment.Top)
        ) {
            Image(
                painter = painterResource(R.drawable.messegesendicon),
                contentDescription = "Gönder",
                modifier = Modifier.size(50.dp)
            )
        }
    }
}



@Composable
fun MessageBubble(sender: String, text: String, dateTime: String) {
    val isUser = sender == "isUser"
    val bubbleColor = if (isUser) Color(0xFFFFDDEF) else Color(0xFF9F009B)
    val textColor = if (isUser) Color.Black else Color.White
    val rowAlignment = if (isUser) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = rowAlignment
    ) {
        Column(
            modifier = Modifier
                .background(bubbleColor, RoundedCornerShape(12.dp))
                .padding(start = 12.dp, top=12.dp, end = 12.dp)
                .widthIn(max = 230.dp)
        ) {
            Text(
                text = text,
                color = textColor
            )
            Spacer(modifier = Modifier.height(4.dp))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Text(
                    text = dateTime,
                    color = textColor.copy(alpha = 0.7f),
                    fontSize = 10.sp
                )
            }
        }
    }
}




@Preview(showBackground = true)
@Composable
fun display(){

}