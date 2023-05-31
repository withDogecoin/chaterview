package team.backend.presentation.quiz

import jakarta.validation.Valid
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import team.backend.constants.Headers
import team.backend.context.RequestContext
import team.backend.domain.quiz.mapper.QuizMapper
import team.backend.domain.quiz.service.QuizService
import team.backend.presentation.quiz.dto.QuizDto
import team.backend.response.SuccessResponse

@RestController
class QuizController(
    private val quizService: QuizService,

    private val quizMapper: QuizMapper,
) {

    @GetMapping("/v1/quiz/random")
    suspend fun randomQuizzes(): String {
        val result = RequestContext.getValue(Headers.AUTHORIZATION)
        println("111Result: $result")

        return result
    }

    @PostMapping("/v1/quiz/answer")
    suspend fun answer(@Valid @RequestBody request: QuizDto.AnswerRequest): SuccessResponse<QuizDto.AnswerResponse> {
        val answer = quizService.answer(quizMapper.from(request))

        return SuccessResponse.from(quizMapper.from(answer))
    }
}