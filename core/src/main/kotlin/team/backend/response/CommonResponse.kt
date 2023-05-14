package team.backend.response

class CommonResponse<T> private constructor(
    val code: String,
    val message: String,
    val payload: T? = null
){

    companion object {

        fun success(): CommonResponse<Unit> {
            return CommonResponse(
                code = ResponseCode.SUCCESS.code,
                message = ResponseCode.SUCCESS.message
            )
        }

        fun <T> success(payload: T): CommonResponse<T> {
            return CommonResponse(
                code = ResponseCode.SUCCESS.code,
                message = ResponseCode.SUCCESS.message,
                payload = payload
            )
        }

        fun fail(): CommonResponse<Unit> {
            return CommonResponse(
                code = ResponseCode.INTERNAL_SERVER_ERROR.code,
                message = ResponseCode.INTERNAL_SERVER_ERROR.message
            )
        }

        fun fail(responseCode: ResponseCode): CommonResponse<Unit> {
            return CommonResponse(
                code = responseCode.code,
                message = responseCode.message
            )
        }
    }
}