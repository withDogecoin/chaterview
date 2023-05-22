package team.backend.presentation.dto

import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

class QuizDto {

    class AnswerRequest(
        @field:NotNull
        val quizId: Long,
        @field:NotEmpty(message = "Blank spaces are not possible.")
        val answer: String,
    )

    class AnswerResponse(
        val isCorrect: Boolean,
        val answer: String,
    )
}