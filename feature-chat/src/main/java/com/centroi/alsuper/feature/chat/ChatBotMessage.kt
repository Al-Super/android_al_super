package com.centroi.alsuper.feature.chat

data class ChatBotMessage(
    val transmitter: TransmitterMessage,
    val message: String,
)

enum class TransmitterMessage {
    USER,
    BOT
}
