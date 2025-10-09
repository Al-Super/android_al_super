package com.centroi.alsuper.core.data.services

import com.centroi.alsuper.core.data.models.chat.ChatQuestionRequest
import com.centroi.alsuper.core.data.models.chat.ChatQuestionResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ChatService {
    @POST("/api/rag/user-question")
    suspend fun askQuestion(@Body request: ChatQuestionRequest): Response<ChatQuestionResponse>
}