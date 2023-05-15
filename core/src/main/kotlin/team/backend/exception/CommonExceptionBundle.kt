package team.backend.exception

import team.backend.response.ResponseCode

class InvalidDomainException: BaseException(ResponseCode.INVALID_DOMAIN)