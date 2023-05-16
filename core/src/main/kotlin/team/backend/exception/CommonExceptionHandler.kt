package team.backend.exception

import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException
import reactor.core.publisher.Mono
import team.backend.response.Error
import team.backend.response.FailResponse
import team.backend.response.ResponseCode

@RestControllerAdvice
class CommonExceptionHandler {

    @ExceptionHandler(WebExchangeBindException::class)
    fun webExchangeBindException(e: WebExchangeBindException): Mono<ResponseEntity<FailResponse>> {
        val errorCode = ResponseCode.BAD_REQUEST
        val errors = e.allErrors.map {
            Error(
                field = (it as FieldError).field,
                message = it.defaultMessage,
                value = it.rejectedValue
            )
        }
        return Mono.just(ResponseEntity(FailResponse.of(errorCode, errors), errorCode.httpStatus))
    }

    @ExceptionHandler(BaseException::class)
    fun baseException(e: BaseException): Mono<ResponseEntity<FailResponse>> {
        val errorCode = e.errorCode
        return Mono.just(ResponseEntity(FailResponse.from(errorCode), errorCode.httpStatus))
    }

    @ExceptionHandler(Exception::class)
    fun exception(e: Exception): Mono<ResponseEntity<FailResponse>> {
        return Mono.just(ResponseEntity(FailResponse.from(), ResponseCode.INTERNAL_SERVER_ERROR.httpStatus))
    }
}