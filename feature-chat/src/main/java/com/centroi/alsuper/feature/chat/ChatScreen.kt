package com.centroi.alsuper.feature.chat

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.SheetState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.centroi.alsuper.core.ui.Dimens
import com.centroi.alsuper.core.ui.LocalSpacing
import com.centroi.alsuper.core.ui.R
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ChatScreen() {
    val dimens = LocalSpacing.current
    val onShowChat = remember { mutableStateOf(false) }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val messageList = remember { mutableStateListOf<ChatBotMessage>() }
    val coroutineScope = rememberCoroutineScope()
    LaunchedEffect(messageList.size) {
        if (messageList.isNotEmpty() && messageList.last().transmitter == TransmitterMessage.USER) {
            delay(750)
            messageList.add(
                ChatBotMessage(
                    transmitter = TransmitterMessage.BOT,
                    message = "Respuesta automatica del bot..."
                )
            )
        }
    }
    Column(
        modifier = Modifier
            .background(MaterialTheme.colorScheme.background)
            .fillMaxSize()
            .padding(dimens.space4x),
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "Chatea",
            fontSize = 36.sp,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "con nosotros",
            fontSize = 36.sp,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            modifier = Modifier.padding(bottom = dimens.space3x),
            text = "Rápido, simple y confidencial",
            fontSize = 24.sp,
            fontWeight = FontWeight.W400,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Text(
            text = "Nuestro chat te ayudará con consejos en situaciones difíciles. Puedes compartir qué está sucediendo, preguntar cualquier cosa y aprender acerca de recursos disponibles. Todo es completamente anónimo y solo para ti.",
            color = MaterialTheme.colorScheme.onPrimary
        )
        Button(
            modifier = Modifier
                .padding(top = dimens.space4x)
                .fillMaxWidth(),
            onClick = {
                onShowChat.value = true
                coroutineScope.launch { sheetState.expand() }
            }
        ) {
            Text("Comenzar chat")
        }
    }
    if (onShowChat.value) {
        FullSheetChat(
            sheetState,
            onShowChat,
            messageList,
            onSend = { message ->
                messageList.add(
                    ChatBotMessage(
                        transmitter = TransmitterMessage.USER,
                        message = message
                    )
                )
            }
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FullSheetChat(
    sheetState: SheetState,
    onShowChat: MutableState<Boolean>,
    messageList: SnapshotStateList<ChatBotMessage>,
    onSend: (String) -> Unit = {}
) {
    val dimens = LocalSpacing.current
    val isTextFieldFocused = remember { mutableStateOf(false) }
    ModalBottomSheet(
        sheetState = sheetState,
        onDismissRequest = {
            onShowChat.value = false
        },
        modifier = Modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(dimens.space4x),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Image(
                    painter = painterResource(com.centroi.alsuper.core.ui.R.drawable.ic_dark_logo),
                    contentDescription = ""
                )
                Image(
                    modifier = Modifier
                        .size(dimens.space6x)
                        .clickable(
                            onClick = { onShowChat.value = false }
                        ),
                    painter = painterResource(com.centroi.alsuper.core.ui.R.drawable.ic_close),
                    contentDescription = ""
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                if (messageList.isEmpty()) {
                    EmptyChatContainer(dimens)
                } else {
                    MessageChatContainer(messageList)
                }
            }
            ChatTextField(dimens, isTextFieldFocused, onSend)

        }
    }
}

@Composable
private fun MessageChatContainer(
    messageList: List<ChatBotMessage>,
    modifier: Modifier = Modifier
) {
    val listState = rememberLazyListState()

    // Animate scroll to the bottom when a new message is added
    LaunchedEffect(messageList.size) {
        if (messageList.isNotEmpty()) {
            listState.animateScrollToItem(messageList.size - 1)
        }
    }

    LazyColumn(
        state = listState,
        modifier = modifier.fillMaxSize(),
        contentPadding = PaddingValues(vertical = 8.dp)
    ) {
        items(messageList) { message ->
            MessageBubble(chatMessage = message)
        }
    }
}

@Composable
private fun MessageBubble(chatMessage: ChatBotMessage) {
    // Determine alignment based on the transmitter
    val isUserMessage = chatMessage.transmitter == TransmitterMessage.USER
    val horizontalArrangement = if (isUserMessage) Arrangement.End else Arrangement.Start

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 8.dp, vertical = 4.dp),
        horizontalArrangement = horizontalArrangement
    ) {
        // Determine colors and shape based on the transmitter
        val bubbleColor = if (isUserMessage) {
            MaterialTheme.colorScheme.onSurfaceVariant
        } else {
            MaterialTheme.colorScheme.surface
        }
        val textColor = if (isUserMessage) {
            MaterialTheme.colorScheme.onPrimary
        } else {
            MaterialTheme.colorScheme.onPrimary
        }
        val bubbleShape = if (isUserMessage) {
            RoundedCornerShape(topStart = 16.dp, topEnd = 4.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
        } else {
            RoundedCornerShape(topStart = 4.dp, topEnd = 16.dp, bottomStart = 16.dp, bottomEnd = 16.dp)
        }

        Surface(
            color = bubbleColor,
            shape = bubbleShape
        ) {
            Text(
                text = chatMessage.message,
                color = textColor,
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 10.dp)
            )
        }
    }
}


@Composable
private fun ChatTextField(
    dimens: Dimens,
    isTextFieldFocused: MutableState<Boolean>,
    onSend: (String) -> Unit
) {
    val text = remember { mutableStateOf("") }
    TextField(
        value = text.value,
        onValueChange = { newText -> text.value = newText },
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = dimens.space2x)
            .onFocusChanged { focusState ->
                isTextFieldFocused.value = focusState.isFocused
            },
        placeholder = {
            Text(
                text = "Escribe un mensaje...",
                style = TextStyle(
                    color = Color.LightGray,
                    fontSize = 16.sp
                )
            )
        },
        colors = TextFieldDefaults.colors(
            unfocusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedContainerColor = MaterialTheme.colorScheme.surface,
            focusedIndicatorColor = MaterialTheme.colorScheme.surface,
            unfocusedIndicatorColor = MaterialTheme.colorScheme.surface
        ),
        shape = RoundedCornerShape(12.dp),
        textStyle = TextStyle(color = Color.White, fontSize = 16.sp),
        singleLine = true,
        trailingIcon = {
            if (isTextFieldFocused.value && text.value.isNotEmpty()) {
                IconButton(
                    onClick = {
                        onSend(text.value)
                        text.value = ""
                    }
                ) {
                    Image(
                        painter = painterResource(R.drawable.ic_arrow_right),
                        contentDescription = "Send Message"
                    )
                }
            }
        }
    )
}

@Composable
private fun EmptyChatContainer(dimens: Dimens) {
    Image(
        modifier = Modifier
            .size(dimens.space10x)
            .padding(bottom = dimens.space3x),
        painter = painterResource(R.drawable.ic_chat_recommendation),
        contentDescription = ""
    )
    Text(
        modifier = Modifier.padding(bottom = dimens.space3x),
        text = "Hola, estoy aqui para escucharte.",
        textAlign = TextAlign.Center,
        color = Color.LightGray
    )
    Text(
        modifier = Modifier.padding(horizontal = dimens.space6x),
        text = "Me puedes decir que esta sucediendo, preguntame cualquier cosa o simplemente comienza con un \"Hola\"",
        textAlign = TextAlign.Center,
        color = Color.LightGray
    )
}