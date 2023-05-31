package team.backend.presentation

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