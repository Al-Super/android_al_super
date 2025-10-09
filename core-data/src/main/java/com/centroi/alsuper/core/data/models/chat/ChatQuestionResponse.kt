package com.centroi.alsuper.core.data.models.chat

data class ChatQuestionResponse(
    val state: String,
    val payload: ChatPayload
)

data class ChatPayload(
    val question: String,
    val response: ChatResponseMessage
)

data class ChatResponseMessage(
    val message: String
)