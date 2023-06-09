package team.backend.presentation.quiz.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull
import team.backend.domain.quiz.query.QuizQuery

class QuizDto {

    data class RandomResponse(
        val quizzes: List<QuizQuery.Base> = listOf()
    )

    data class AnswerRequest(
        @field:NotNull
        val quizId: Long,
        @field:NotEmpty(message = "Blank spaces are not possible.")
        val answer: String,
    )

    data class AnswerResponse(
        val isCorrect: Boolean,
        val answer: String,
    )
}