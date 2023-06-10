package team.backend.exception

import team.backend.response.ResponseCode

class InvalidAnswerException: BaseException(ResponseCode.INVALID_CHATGPT_ANSWER)

class NotFoundMemberException: BaseException(ResponseCode.NOT_FOUND_RESROURCES)