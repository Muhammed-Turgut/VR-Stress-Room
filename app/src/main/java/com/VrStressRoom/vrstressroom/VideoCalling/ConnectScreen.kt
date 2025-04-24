package com.VrStressRoom.vrstressroom.VideoCalling

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.lang.reflect.Modifier

@Composable
fun ConnectScreen(
    state: ConnectState,
    onAction: (ConnectAction) -> Unit){

    Column(androidx.compose.ui.Modifier
        .fillMaxSize()
        .padding(16.dp)) {
        Text(text="Choose a name",
            fontSize = 18.sp)
        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

        TextField(value = state.name,
            onValueChange = {
                onAction(ConnectAction.OnNameChange(it))
            },
            placeholder = {
                Text(text = "Name")
            },
            modifier = androidx.compose.ui.Modifier.fillMaxWidth())
        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

        Button(onClick = {
            onAction(ConnectAction.OnConnectClick)
        }, modifier = androidx.compose.ui.Modifier.align(Alignment.End)) {
            Text("Connect")
        }

        Spacer(modifier = androidx.compose.ui.Modifier.height(16.dp))

        if(state.errorMessage != null){
            Text(text = state.errorMessage,
                color = Color.Red
            )
        }
    }


}

@Preview(showBackground = true)
@Composable
private fun ConnectScreenPreview(){
    ConnectScreen(state = ConnectState(),
        onAction = {})
}