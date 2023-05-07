package team.backend.domain.repository

import team.backend.domain.entity.Job

interface JobRepository {
    suspend fun save(job: Job)

    suspend fun findById(id: Long): Job?
}