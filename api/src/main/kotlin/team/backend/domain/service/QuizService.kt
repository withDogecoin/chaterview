package team.backend.domain.service

import team.backend.domain.dto.QuizCommand

interface QuizService {

    suspend fun answer(command: QuizCommand.AnswerRequest): QuizCommand.AnswerResponse
}