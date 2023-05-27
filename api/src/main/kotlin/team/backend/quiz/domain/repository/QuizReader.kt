package team.backend.quiz.domain.repository

import team.backend.quiz.domain.entity.Quiz

interface QuizReader {

    suspend fun get(id: Long): Quiz
}