package team.backend.domain.dto

class QuizCommand {

    class AnswerRequest(
        val quizId: Long,
        val answer: String,
    )

    class AnswerResponse(
        val isCorrect: Boolean,
        val answer: String,
    )
}