package com.centroi.alsuper.feature.chat

data class ChatBotMessage(
    val transmitter: TransmitterMessage,
    val message: String,
    val isLoading: Boolean = false
)

enum class TransmitterMessage {
    USER,
    BOT,
    LOADING
}
