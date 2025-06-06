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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.IconButton
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
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
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
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
fun ChatBotScreenPage(viewModel: ChatViewModel= viewModel(), navController: NavController) {
    //Önceden göderilen mesajlaarın listelendiği kısım.
    LaunchedEffect(Unit) {
        viewModel.getItemList()
    }
    val itemList by viewModel.itemListChatBot.collectAsState(initial = emptyList())

    BackHandler {
        // Geri tuşuna basıldığında hiçbir şey yapma
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
    ){

            Image(
                painter = painterResource(R.drawable.vrstreslogoroom),
                contentDescription = null,
                modifier = Modifier
                    .size(width = 320.dp, height = 368.dp)
                    .alpha(0.5f)
                    .align(Alignment.Center), // SADECE Image'ı ortala!
            )

            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(bottom = 50.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp, top = 30.dp)
                        .clip(CircleShape)
                        .background( Color(0xFFFF5F9E)),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    IconButton(
                        onClick = {
                            navController.navigate("MainPage")
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.backchatboticon),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
                    }

                    Text(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = 10.dp, bottom = 10.dp),
                        text = "AI Chat Bot",
                        fontSize = 24.sp,
                        color = Color.White,
                        fontWeight = FontWeight.SemiBold,
                        textAlign = TextAlign.Center
                    )

                    IconButton(
                        onClick = {
                            viewModel.deleteAllItem()
                        }
                    ) {
                        Image(
                            painter = painterResource(R.drawable.trashiconbutton),
                            contentDescription = null,
                            modifier = Modifier.size(32.dp)
                        )
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
                        MessageBubble(
                            sender = message.senderMessege ?: "gelmedi",
                            text = message.contentsMessage ?: "gelmedi",
                            dateTime = message.dateTime ?: "00:00"
                        )
                    }
                }

                MessageInput(
                    onMessageSend = { viewModel.sendMessageToBot(it) },
                    saveItem = { item -> viewModel.saveItem(item) },
                    dateTime = { viewModel.getCurrentTime() }
                )
            }
    }
}



    @Composable
fun MessageInput(onMessageSend: (String) -> Unit,saveItem : (ItemChatBotList) -> Unit,dateTime : () -> String) {
    var message by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 8.dp),
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
                .clip(RoundedCornerShape(4.dp)),

            colors = OutlinedTextFieldDefaults.colors(
                focusedTextColor = Color.Black,
                unfocusedTextColor = Color(0xFFB7B7B7),
                cursorColor = Color.White,
                focusedBorderColor = Color(0xFF9F009B),
                unfocusedBorderColor = Color(0xFF9F009B),
                focusedLeadingIconColor = Color.Black,
                unfocusedLeadingIconColor = Color(0xFFB7B7B7),
                focusedLabelColor = Color.White,
                unfocusedLabelColor = Color(0xFFB7B7B7)
            )
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
                painter = painterResource(R.drawable.chatbotsendicon),
                contentDescription = "Gönder",
                modifier = Modifier.size(width = 32.dp, height = 27.dp)
            )
        }
    }
}



@Composable
fun MessageBubble(sender: String, text: String, dateTime: String) {
    val isUser = sender == "isUser"
    val bubbleColor = if (isUser) Color(0xFF9F009B) else Color(0xFFFFDDEF)
    val textColor = if (isUser) Color.White else  Color.Black
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
