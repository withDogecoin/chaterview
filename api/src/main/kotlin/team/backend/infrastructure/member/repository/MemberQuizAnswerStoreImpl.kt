package team.backend.infrastructure.member.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.domain.member.entity.MemberQuizAnswer
import team.backend.domain.member.repository.MemberQuizAnswerStore

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