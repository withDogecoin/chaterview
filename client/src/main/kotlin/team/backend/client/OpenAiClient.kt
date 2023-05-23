package team.backend.client

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import team.backend.dto.ChatDto
import team.backend.properties.OpenAiProperties

@Component
class OpenAiClient(
    private val openAiProperties: OpenAiProperties,
    private val objectMapper: ObjectMapper,
) {

    private val webClient = WebClient.builder()
        .baseUrl(openAiProperties.endpoint.base)
        .build()

    suspend fun chat(body: ChatDto.Request): ChatDto.Response {
        return webClient.post()
            .uri(openAiProperties.endpoint.chat)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${openAiProperties.token}")
            .bodyValue(objectMapper.writeValueAsString(body))
            .retrieve()
            .awaitBody<ChatDto.Response>()
    }
}