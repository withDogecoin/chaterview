package team.backend.infrastructure.service.quiz

import team.backend.domain.command.quiz.QuizCommand

interface QuizService {

    suspend fun answer(command: QuizCommand.AnswerRequest): QuizCommand.AnswerResponse
}