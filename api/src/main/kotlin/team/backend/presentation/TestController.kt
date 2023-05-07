package team.backend.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RestController
import team.backend.domain.service.QuizService

@RestController
class TestController(
    private val quizService: QuizService
) {

    @PostMapping("/jobs")
    suspend fun createJob() {
        quizService.createJob()
    }

    @GetMapping("/jobs")
    suspend fun findJob() {
        quizService.findJob()
    }

}