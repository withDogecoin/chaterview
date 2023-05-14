package team.backend.exception

import team.backend.response.ResponseCode

class InvalidDomainException: CommonException(ResponseCode.INVALID_DOMAIN)