package team.backend.response

import org.springframework.http.HttpStatus

enum class ResponseCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus
) {

    SUCCESS("100", "SUCCESS", HttpStatus.OK),
    BAD_REQUEST("102", "BAD REQUEST", HttpStatus.BAD_REQUEST),
    INTERNAL_SERVER_ERROR("103", "INTERNAL SERVER ERROR", HttpStatus.INTERNAL_SERVER_ERROR),

    INVALID_CHATGPT_ANSWER("200", "Error in chatGPT response.", HttpStatus.BAD_REQUEST),
    NOT_FOUND_RESROURCES("201", "Not Found Resources", HttpStatus.BAD_REQUEST),

    INVALID_DOMAIN("999", "INVALID DOMAIN", HttpStatus.BAD_REQUEST)
}