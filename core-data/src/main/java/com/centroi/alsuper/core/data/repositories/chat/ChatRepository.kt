package com.centroi.alsuper.core.data.repositories.chat

import com.centroi.alsuper.core.data.ResultState
import com.centroi.alsuper.core.data.extensions.ApiCall
import com.centroi.alsuper.core.data.models.chat.ChatQuestionRequest
import com.centroi.alsuper.core.data.models.chat.ChatQuestionResponse
import com.centroi.alsuper.core.data.services.ChatService
import kotlinx.coroutines.flow.Flow
import retrofit2.Retrofit
import javax.inject.Inject

interface ChatRepositoryInt {
    fun askQuestion(question: String): Flow<ResultState<ChatQuestionResponse>>
}

class ChatRepository @Inject constructor(
    retrofitNetwork: Retrofit
) : ChatRepositoryInt {

    private val chatService: ChatService by lazy {
        retrofitNetwork.create(ChatService::class.java)
    }

    override fun askQuestion(question: String): Flow<ResultState<ChatQuestionResponse>> {
        return ApiCall.safeApiCall {
            chatService.askQuestion(ChatQuestionRequest(question))
        }
    }
}