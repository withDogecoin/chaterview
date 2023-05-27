package team.backend.quiz.domain.service

import team.backend.quiz.domain.command.QuizCommand

interface QuizService {

    suspend fun answer(command: QuizCommand.AnswerRequest): QuizCommand.AnswerResponse
}