package team.backend.response

class FailResponse private constructor(
    val code: String,
    val message: String,
    val errors: List<Error>? = null,
){

    companion object {
        fun from(): FailResponse {
            return FailResponse(
                code = ResponseCode.INTERNAL_SERVER_ERROR.code,
                message = ResponseCode.INTERNAL_SERVER_ERROR.message
            )
        }

        fun from(errorCode: ResponseCode): FailResponse {
            return FailResponse(
                code = errorCode.code,
                message = errorCode.message
            )
        }

        fun of(errorCode: ResponseCode, errors: List<Error>?): FailResponse {
            return FailResponse(
                code = errorCode.code,
                message = errorCode.message,
                errors = errors
            )
        }
    }
}

data class Error(
    var field: String? = null,
    var message: String? = null,
    var value: Any? = null
)