package team.backend.infrastructure.member.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import jakarta.annotation.PostConstruct
import org.hibernate.reactive.mutiny.impl.MutinySessionFactoryImpl
import org.springframework.stereotype.Repository
import team.backend.domain.member.repository.MemberStore
import team.backend.domain.member.entity.Member
import team.backend.domain.job.entity.Job
import team.backend.domain.job.entity.JobType

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