package team.backend.properties

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "openai")
data class OpenAiProperties(
    var endpoint: Endpoint = Endpoint(),
    var token: String = ""
) {

    data class Endpoint(
        var base: String = "",
        var chat: String = ""
    )
}