package team.backend.dto

import com.fasterxml.jackson.annotation.JsonInclude

class ChatDto {

    companion object {
        private const val chatModel = "gpt-3.5-turbo"
        private const val chatRole = "user"
    }

    data class Request(
        val model: String,
        val messages: List<Message>
    ) {
        companion object {
            fun from(content: String): Request {
                return Request(
                    model = chatModel,
                    messages = listOf(
                        Message(
                            role = chatRole,
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

    data class Choice(
        val index: Int,
        val message: Message,
        val finish_reason: String
    )

    data class Usage(
        val prompt_tokens: Int,
        val completion_tokens: Int,
        val total_tokens: Int
    )
}