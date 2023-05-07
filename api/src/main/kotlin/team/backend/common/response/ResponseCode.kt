package team.backend.common.response

import org.springframework.http.HttpStatus

enum class ResponseCode(
    val code: String,
    val message: String,
    val httpStatus: HttpStatus
) {

    SUCCESS("100", "SUCCESS", HttpStatus.OK),
    INTERNAL_SERVER_ERROR("101", "INTERNAL_SERVER_ERROR", HttpStatus.INTERNAL_SERVER_ERROR),
    INVALID_DOMAIN("102", "INVALID DOMAIN", HttpStatus.BAD_REQUEST)
}