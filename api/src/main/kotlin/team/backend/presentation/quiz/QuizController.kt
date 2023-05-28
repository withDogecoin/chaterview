package team.backend.presentation.quiz

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import team.backend.domain.mapper.quiz.QuizMapper
import team.backend.infrastructure.service.quiz.QuizService
import team.backend.presentation.quiz.dto.QuizDto
import team.backend.response.SuccessResponse

@RestController
class QuizController(
    private val quizService: QuizService,

    private val quizMapper: QuizMapper,
) {

    @PostMapping("/v1/quiz/answer")
    suspend fun answer(@Valid @RequestBody request: QuizDto.AnswerRequest): SuccessResponse<QuizDto.AnswerResponse> {
        val answer = quizService.answer(quizMapper.from(request))
        return SuccessResponse.from(quizMapper.from(answer))
    }
}