package team.backend.domain.quiz.command

class QuizCommand {

    data class AnswerRequest(
        val quizId: Long,
        val answer: String,
    )

    data class AnswerResponse(
        val isCorrect: Boolean,
        val answer: String,
    )
}