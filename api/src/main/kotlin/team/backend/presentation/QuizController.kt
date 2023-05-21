package team.backend.presentation

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import team.backend.domain.dto.QuizCommand
import team.backend.domain.entity.Job
import team.backend.domain.entity.Quiz
import team.backend.domain.service.QuizService
import team.backend.presentation.dto.QuizDto

@RestController
class QuizController(
    private val quizService: QuizService
) {

    @PostMapping("/v1/quiz/answer")
    suspend fun answer(@Valid @RequestBody request: QuizDto.AnswerRequest): QuizDto.AnswerResponse {
        val answer = quizService.answer(
            QuizCommand.AnswerRequest(
                quizId = request.quizId,
                answer = request.answer
            )
        )
        return QuizDto.AnswerResponse(
            isCorrect = answer.isCorrect,
            answer = answer.answer
        )
    }
}