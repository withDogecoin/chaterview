package team.backend.infrastructure.repository.member

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.domain.entity.member.MemberQuizAnswer
import team.backend.domain.repository.member.MemberQuizAnswerStore

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