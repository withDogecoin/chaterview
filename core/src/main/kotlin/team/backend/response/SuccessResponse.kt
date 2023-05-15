package team.backend.response

class SuccessResponse<T> private constructor(
    val code: String,
    val message: String,
    val payload: T? = null
){

    companion object {

        fun from(): SuccessResponse<Unit> {
            return SuccessResponse(
                code = ResponseCode.SUCCESS.code,
                message = ResponseCode.SUCCESS.message
            )
        }

        fun <T> from(payload: T): SuccessResponse<T> {
            return SuccessResponse(
                code = ResponseCode.SUCCESS.code,
                message = ResponseCode.SUCCESS.message,
                payload = payload
            )
        }
    }
}