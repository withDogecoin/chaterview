package team.backend.domain.service

import org.springframework.stereotype.Service
import team.backend.domain.entity.Job
import team.backend.domain.entity.JobType
import team.backend.domain.repository.JobRepository
import team.backend.domain.repository.QuizRepository

@Service
class QuizService(
    private val quizRepository: QuizRepository,
    private val jobRepository: JobRepository
) {

    suspend fun createJob() {
        val job = Job(type = JobType.BACKEND_ENGINEER)
        jobRepository.save(job)
    }

    suspend fun findJob() {
        val result = jobRepository.findById(1L)
        result?.let {
            println(result.type)
        }
    }
}