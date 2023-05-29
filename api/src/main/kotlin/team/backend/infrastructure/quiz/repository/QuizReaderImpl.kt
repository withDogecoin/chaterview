package team.backend.infrastructure.quiz.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import org.springframework.stereotype.Repository
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
}