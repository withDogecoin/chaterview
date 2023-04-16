package team.backend.infrastructure.repository

import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import org.springframework.stereotype.Repository
import team.backend.domain.entity.Quiz
import team.backend.domain.repository.QuizRepository

@Repository
class QuizRepositoryImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory
): QuizRepository {

    override suspend fun findById(id: Long): Quiz {
        TODO("Not yet implemented")
    }
}