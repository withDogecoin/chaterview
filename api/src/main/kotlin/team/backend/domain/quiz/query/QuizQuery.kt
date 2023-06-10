package team.backend.domain.quiz.query

class QuizQuery {

    data class Base(
        val question: String,
        val level: String,
        val job: String,
        val subject: String
    )

    data class RandomResponse(
        val quizzes: List<Base> = listOf()
    )
}