package team.backend.infrastructure.repository

import com.linecorp.kotlinjdsl.querydsl.expression.column
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import io.smallrye.mutiny.coroutines.awaitSuspending
import org.springframework.stereotype.Repository
import team.backend.domain.entity.Job
import team.backend.domain.repository.JobRepository

@Repository
class JobRepositoryImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
): JobRepository {
    override suspend fun save(job: Job) {
        queryFactory.transactionWithFactory { session, factory ->
            session.persist(job).awaitSuspending()
            session.flush().awaitSuspending()
        }
    }

    override suspend fun findById(id: Long): Job? {
        return queryFactory.singleQuery {
            select(entity(Job::class))
            from(entity(Job::class))
            where(column(Job::id).equal(id))
        }
    }
}