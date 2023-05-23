package team.backend.domain.repository

import team.backend.domain.entity.Quiz

interface QuizReader {

    suspend fun get(id: Long): Quiz
}