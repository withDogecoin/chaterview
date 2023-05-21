package team.backend.infrastructure.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.domain.entity.MemberQuizAnswer
import team.backend.domain.repository.MemberQuizAnswerStore

@Repository
class MemberQuizAnswerStoreImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
    private val mutinySessionFactory: MutinySessionFactoryImpl,
): MemberQuizAnswerStore {

    override suspend fun save(entity: MemberQuizAnswer) {
        mutinySessionFactory.withSession { session ->
            session.persist(entity)
                .flatMap { session.flush() } }
            .subscribeAsCompletionStage()
    }
}