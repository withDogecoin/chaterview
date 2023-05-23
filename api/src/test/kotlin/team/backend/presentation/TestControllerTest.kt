package team.backend.presentation

import com.epages.restdocs.apispec.WebTestClientRestDocumentationWrapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.ApplicationContext
import org.springframework.restdocs.RestDocumentationContextProvider
import org.springframework.restdocs.RestDocumentationExtension
import org.springframework.restdocs.payload.PayloadDocumentation.*
import org.springframework.restdocs.webtestclient.WebTestClientRestDocumentation
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.reactive.function.BodyInserters
import team.backend.domain.service.QuizServiceImpl

//@AutoConfigureRestDocs
//@ExtendWith(RestDocumentationExtension::class, SpringExtension::class)
//@WebFluxTest(controllers = [TestController::class])
//class TestControllerTest {
//
//    @MockBean
//    private lateinit var quizServiceImpl: QuizServiceImpl
//
//    private lateinit var webTestClient: WebTestClient
//
//    @BeforeEach
//    internal fun setUp(context: ApplicationContext, restDocumentation: RestDocumentationContextProvider) {
//        this.webTestClient = WebTestClient.bindToApplicationContext(context)
//            .configureClient()
//            .filter(WebTestClientRestDocumentation.documentationConfiguration(restDocumentation))
//            .build()
//    }
//
//    @Test
//    fun createJobTest() {
//        webTestClient.post().uri("/jobs")
//            .body(BodyInserters.fromValue(TestController.TestDto(jobName = "Backend Developer")))
//            .exchange()
//            .expectStatus()
//            .isOk()
//            .expectBody(TestController.ResponseDto::class.java)
//            .consumeWith(
//                WebTestClientRestDocumentationWrapper.document(
//                    "Create Job",
//                    snippets = arrayOf(
//                        requestFields(
//                            fieldWithPath("jobName")
//                                .description("직업 이름"),
//                        ),
//                        responseFields(
//                            fieldWithPath("id")
//                                .description("아이디"),
//                            fieldWithPath("jobName")
//                                .description("직업 이름"),
//                        )
//                    )
//                )
//            )
//    }
//}