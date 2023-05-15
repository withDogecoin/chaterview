package team.backend.exception

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import reactor.core.publisher.Mono
import team.backend.response.CommonResponse
import team.backend.response.ResponseCode

@RestControllerAdvice
class CommonExceptionHandler {

    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): Mono<ResponseEntity<CommonResponse<Unit>>> {
        val errorCode = ResponseCode.INTERNAL_SERVER_ERROR
        return Mono.just(ResponseEntity(CommonResponse.fail(errorCode), errorCode.httpStatus))
    }

    @ExceptionHandler(BaseException::class)
    fun baseException(e: BaseException): Mono<ResponseEntity<CommonResponse<Unit>>> {
        val errorCode = e.errorCode
        return Mono.just(ResponseEntity(CommonResponse.fail(errorCode), errorCode.httpStatus))
    }
}