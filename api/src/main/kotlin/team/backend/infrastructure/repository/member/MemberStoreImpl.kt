package team.backend.infrastructure.repository.member

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import jakarta.annotation.PostConstruct
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.domain.repository.member.MemberStore
import team.backend.domain.entity.member.Member
import team.backend.common.entity.Job
import team.backend.common.entity.JobType

@Repository
class MemberStoreImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
    private val mutinySessionFactory: MutinySessionFactoryImpl,
): MemberStore {

    @PostConstruct
    fun init() {
        val job = Job(type = JobType.BACKEND_ENGINEER)

        val savedJob =
            mutinySessionFactory.withSession { session ->
                session.persist(job)
                    .flatMap { session.flush() } }
                .map { job }
                .subscribeAsCompletionStage()

        val member = Member(
            name = "tester",
            job = savedJob.get()
        )

        mutinySessionFactory.withSession { session ->
            session.persist(member)
                .flatMap { session.flush() } }
            .subscribeAsCompletionStage()
    }
}