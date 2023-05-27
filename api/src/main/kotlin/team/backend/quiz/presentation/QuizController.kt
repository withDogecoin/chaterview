package team.backend.quiz.presentation

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import team.backend.quiz.domain.mapper.QuizMapper
import team.backend.quiz.domain.service.QuizService
import team.backend.quiz.presentation.dto.QuizDto
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