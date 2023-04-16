package team.backend.domain.repository

import team.backend.domain.entity.Quiz

interface QuizRepository {

    suspend fun findById(id: Long): Quiz
}