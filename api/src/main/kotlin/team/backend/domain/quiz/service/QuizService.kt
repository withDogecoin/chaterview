package team.backend.domain.quiz.service

import team.backend.domain.quiz.command.QuizCommand
import team.backend.domain.quiz.query.QuizQuery

interface QuizService {

    suspend fun random(authorization: String): QuizQuery.RandomResponse

    suspend fun answer(command: QuizCommand.AnswerRequest): QuizCommand.AnswerResponse
}