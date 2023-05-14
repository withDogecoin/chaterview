package team.backend.exception

import team.backend.response.ResponseCode

open class CommonException(
    open val errorCode: ResponseCode = ResponseCode.BAD_REQUEST,
): RuntimeException()

open class DomainException(
    override val errorCode: ResponseCode = ResponseCode.BAD_REQUEST
) : CommonException(errorCode)

open class InfraException(
    override val errorCode: ResponseCode = ResponseCode.BAD_REQUEST
) : CommonException(errorCode)

open class PresentationException(
    override val errorCode: ResponseCode = ResponseCode.BAD_REQUEST
) : CommonException(errorCode)