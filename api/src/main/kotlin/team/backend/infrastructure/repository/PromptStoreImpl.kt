package team.backend.infrastructure.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import jakarta.annotation.PostConstruct
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.domain.entity.*
import team.backend.domain.repository.PromptStore

@Repository
class PromptStoreImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
    private val mutinySessionFactory: MutinySessionFactoryImpl,
): PromptStore {

    @PostConstruct
    fun init() {
        val interviewPrefixPrompt = Prompt(
            promptType = PromptType.INTERVIEW_ANSWER,
            positionType = PositionType.PREFIX,
            command = "I said the following as an answer to the previous question."
            )
        val interviewSuffixPrompt = Prompt(
            promptType = PromptType.INTERVIEW_ANSWER,
            positionType = PositionType.SUFFIX,
            command = "Please provide the correctness of the answer to the question as the first sentence, stating whether it is correct or incorrect, and then proceed to explain."
        )

        mutinySessionFactory.withSession { session ->
            session.persist(interviewPrefixPrompt)
                .flatMap { session.flush() } }
            .subscribeAsCompletionStage()

        mutinySessionFactory.withSession { session ->
            session.persist(interviewSuffixPrompt)
                .flatMap { session.flush() } }
            .subscribeAsCompletionStage()
    }
}