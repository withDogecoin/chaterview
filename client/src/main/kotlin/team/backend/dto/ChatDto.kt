package team.backend.dto

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.PropertyNamingStrategies
import com.fasterxml.jackson.databind.annotation.JsonNaming
import team.backend.model.ChatModel
import team.backend.model.ChatRole

class ChatDto {

    data class Request(
        val model: String,
        val messages: List<Message>
    ) {
        companion object {
            fun of(model: ChatModel, role: ChatRole, content: String): Request {
                return Request(
                    model = model.key,
                    messages = listOf(
                        Message(
                            role = role.key,
                            content = content
                        )
                    )
                )
            }
        }
    }

    data class Response(
        val id: String,
        val `object`: String,
        val created: Long,
        val choices: List<Choice>,
        val usage: Usage
    )

    @JsonInclude(JsonInclude.Include.NON_NULL)
    data class Message(
        val role: String,
        val content: String,
        val name: String? = null
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Choice(
        val index: Int,
        val message: Message,
        val finishReason: String
    )

    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy::class)
    data class Usage(
        val promptTokens: Int,
        val completionTokens: Int,
        val totalTokens: Int
    )
}