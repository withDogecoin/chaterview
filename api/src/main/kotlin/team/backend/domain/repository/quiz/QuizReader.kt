package team.backend.domain.repository.quiz

import team.backend.domain.entity.quiz.Quiz

interface QuizReader {

    suspend fun get(id: Long): Quiz
}