package team.backend.client

import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.http.MediaType
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import reactor.core.publisher.Mono
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

    fun chat(body: ChatDto.Request): Mono<ChatDto.Response> {
        return webClient.post()
            .uri(openAiProperties.endpoint.chat)
            .contentType(MediaType.APPLICATION_JSON)
            .header("Authorization", "Bearer ${openAiProperties.token}")
            .bodyValue(objectMapper.writeValueAsString(body))
            .retrieve()
            .bodyToMono(ChatDto.Response::class.java)
    }
}