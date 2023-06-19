package team.backend.presentation.quiz

import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper
import com.ninjasquad.springmockk.MockkBean
import io.mockk.coEvery
import org.junit.jupiter.api.Test
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.http.MediaType
import org.springframework.restdocs.headers.HeaderDocumentation.*
import org.springframework.restdocs.operation.preprocess.Preprocessors.*
import org.springframework.restdocs.payload.PayloadDocumentation.*
import team.backend.common.constants.QUIZ_ANSWER
import team.backend.common.constants.RAMDOM_QUIZ
import team.backend.domain.quiz.command.QuizCommand
import team.backend.domain.quiz.mapper.QuizMapper
import team.backend.domain.quiz.query.QuizQuery
import team.backend.domain.quiz.service.QuizService
import team.backend.presentation.AbstractControllerTest
import team.backend.presentation.quiz.dto.QuizDto

@WebFluxTest(controllers = [QuizController::class])
class QuizControllerTest: AbstractControllerTest() {

    @MockkBean private lateinit var quizService: QuizService
    @MockkBean private lateinit var quizMapper: QuizMapper

    @Test
    fun random() {
        coEvery { quizService.random(any()) } returns QuizQuery.RandomResponse(listOf(QuizQuery.Base(question = "Question 1", level = "EASY", job = "BACKEND_ENGINEER", subject = "SPRING")))

        webTestClient.get().uri(RAMDOM_QUIZ)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Member Identity")
            .exchange()
            .expectStatus().isOk()
            .expectBody(QuizDto.RandomResponse::class.java)
            .consumeWith(
                WebTestClientRestDocumentationWrapper.document(
                    "random-quiz",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName("Authorization").description("Member Identity (e.g token, id, etc)")
                    ),
                    responseFields(
                        fieldWithPath("code").description("Response Code"),
                        fieldWithPath("message").description("Response Message"),
                        fieldWithPath("payload.quizzes[].question").description("문제"),
                        fieldWithPath("payload.quizzes[].level").description("난이도"),
                        fieldWithPath("payload.quizzes[].job").description("직업"),
                        fieldWithPath("payload.quizzes[].subject").description("주제"),
                    )
                )
            )
    }

    @Test
    fun answerTest() {
        coEvery { quizService.answer(any()) } returns QuizCommand.AnswerResponse(isCorrect = true, "Java runs on JVM.")
        coEvery { quizMapper.from(any()) } returns QuizDto.AnswerResponse(isCorrect = true, answer = "Java runs on JVM.")
        coEvery { quizMapper.from(any(), any()) } returns QuizCommand.AnswerRequest(quizId = 1L, answer = "Java runs on JVM.", authorization = "1")

        val request = QuizDto.AnswerRequest(
            quizId = 1L,
            answer = "Java runs on JVM."
        )

        webTestClient.post().uri(QUIZ_ANSWER)
            .accept(MediaType.APPLICATION_JSON)
            .header("Authorization", "Member Identity")
            .bodyValue(request)
            .exchange()
            .expectStatus().isOk()
            .expectBody(QuizDto.AnswerResponse::class.java)
            .consumeWith(
                WebTestClientRestDocumentationWrapper.document(
                    "quiz-answer",
                    preprocessRequest(prettyPrint()),
                    preprocessResponse(prettyPrint()),
                    requestHeaders(
                        headerWithName("Authorization").description("Member Identity (e.g token, id, etc)")
                    ),
                    responseFields(
                        fieldWithPath("code").description("Response Code"),
                        fieldWithPath("message").description("Response Message"),
                        fieldWithPath("payload.isCorrect").description("Whether it's correct"),
                        fieldWithPath("payload.answer").description("The answer to the quiz"),
                    )
                )
            )

    }
}