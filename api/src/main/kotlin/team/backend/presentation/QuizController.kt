package team.backend.presentation

import jakarta.validation.Valid
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono
import reactor.util.context.ContextView
import team.backend.constants.Headers
import team.backend.context.RequestContext
import team.backend.domain.dto.command.QuizCommand
import team.backend.domain.service.QuizService
import team.backend.presentation.dto.QuizDto


@RestController
class QuizController(
    private val quizService: QuizService
) {

    @GetMapping("/v1/quiz/random")
    suspend fun randomQuizzes(): String {
        val result = RequestContext.getValue(Headers.AUTHORIZATION)
        println("111Result: $result")

        return result
    }

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