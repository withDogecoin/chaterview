package team.backend.domain.quiz.repository

import team.backend.domain.quiz.entity.Quiz

interface QuizReader {

    suspend fun get(id: Long): Quiz
}