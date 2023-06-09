package team.backend.infrastructure.quiz.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
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

    override suspend fun getIds(): List<Long> {
        return queryFactory.singleQuery {
            selectMulti(col(Quiz::id))
            from(entity(Quiz::class))
        }
    }

    override suspend fun getRandomly(job: Job, tier: Tier, ids: List<Long>): List<Quiz> {
        return queryFactory.singleQuery {
            selectMulti(entity(Quiz::class))
            from(entity(Quiz::class))
            where(col(Quiz::id).`in`(ids))
            and(col(Job::id).equal(job.id))
            and(col(Quiz::active).equal(true))
            and(col(Quiz::level).`in`(tier.matchedQuizLevels()))
        }
    }
}