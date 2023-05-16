package team.backend.exception

import team.backend.response.ResponseCode

open class BaseException(
    open val errorCode: ResponseCode = ResponseCode.BAD_REQUEST,
): RuntimeException()