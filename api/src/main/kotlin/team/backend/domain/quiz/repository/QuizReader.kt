package team.backend.domain.quiz.repository

import team.backend.domain.job.entity.Job
import team.backend.domain.member.entity.Tier
import team.backend.domain.quiz.entity.Quiz

interface QuizReader {

    suspend fun get(id: Long): Quiz

    suspend fun getQuizIds(job: Job, tier: Tier): List<Long>

    suspend fun getQuizByIds(ids: List<Long>): List<Quiz>
}