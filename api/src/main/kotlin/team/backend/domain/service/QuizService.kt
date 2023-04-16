package team.backend.domain.service

import org.springframework.stereotype.Service
import team.backend.domain.repository.QuizRepository

@Service
class QuizService(
    private val quizRepository: QuizRepository
) {
}