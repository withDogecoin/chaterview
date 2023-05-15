package team.backend.presentation

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import team.backend.domain.service.QuizService

@RestController
class TestController(
    private val quizService: QuizService
) {

    data class TestDto(
        val jobName: String
    )

    data class ResponseDto(
        val id: Long = 1L,
        val jobName: String = "Backend Developer"
    )

    @PostMapping("/jobs")
    suspend fun createJob(@RequestBody request: TestDto): ResponseDto {
        quizService.createJob()
        return ResponseDto()
    }

    @GetMapping("/jobs")
    suspend fun findJob() {
        quizService.findJob()
    }

}