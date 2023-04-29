package team.backend.model

enum class ChatRole(
    val key: String
) {
    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant")
}