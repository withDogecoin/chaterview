package team.backend.domain.quiz.repository

import team.backend.domain.job.entity.Job
import team.backend.domain.member.entity.Tier
import team.backend.domain.quiz.entity.Quiz

interface QuizReader {

    suspend fun get(id: Long): Quiz

    suspend fun getIds(): List<Long>

    suspend fun getRandomly(job: Job, tier: Tier, ids: List<Long>): List<Quiz>
}