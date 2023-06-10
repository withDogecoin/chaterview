package team.backend.infrastructure.quiz.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.listQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import org.springframework.stereotype.Repository
import team.backend.domain.job.entity.Job
import team.backend.domain.member.entity.Tier
import team.backend.domain.quiz.entity.Quiz
import team.backend.domain.quiz.repository.QuizReader

@Repository
class QuizReaderImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
): QuizReader {

    override suspend fun get(id: Long): Quiz {
        return queryFactory.singleQuery {
            select(entity(Quiz::class))
            from(entity(Quiz::class))
            where(col(Quiz::id).equal(id))
        }
    }

    override suspend fun getQuizIds(job: Job, tier: Tier): List<Long> {
        return queryFactory.listQuery<Long> {
            selectMulti(col(Quiz::id))
            from(entity(Quiz::class))
            join(
                Quiz::class,
                Job::class,
                on(Quiz::job),
            )
            whereAnd(
                col(Quiz::level).`in`(tier.matchedQuizLevels()),
                col(Quiz::job).equal(job),
                col(Quiz::active).equal(true),
            )
        }
    }

    override suspend fun getQuizByIds(ids: List<Long>): List<Quiz> {
        return queryFactory.listQuery {
            select(entity(Quiz::class))
            from(entity(Quiz::class))
            join(
                Quiz::class,
                Job::class,
                on(Quiz::job),
            )
            where(col(Quiz::id).`in`(ids))
        }
    }
}