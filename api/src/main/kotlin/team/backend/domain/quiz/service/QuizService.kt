package team.backend.domain.quiz.service

import team.backend.domain.quiz.command.QuizCommand

interface QuizService {

    suspend fun answer(command: QuizCommand.AnswerRequest): QuizCommand.AnswerResponse
}