package com.centroi.alsuper.core.data.usecases.chat

import com.centroi.alsuper.core.data.repositories.chat.ChatRepositoryInt
import javax.inject.Inject

class AskChatQuestionUseCase @Inject constructor(
    private val repository: ChatRepositoryInt
) {
    operator fun invoke(question: String) =
        repository.askQuestion(question)
}