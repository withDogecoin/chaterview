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

    INVALID_DOMAIN("999", "INVALID DOMAIN", HttpStatus.BAD_REQUEST)
}