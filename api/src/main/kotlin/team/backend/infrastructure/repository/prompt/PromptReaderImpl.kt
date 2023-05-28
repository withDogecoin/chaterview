package team.backend.infrastructure.repository.prompt

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.listQuery
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import org.springframework.stereotype.Repository
import team.backend.domain.entity.prompt.PositionType
import team.backend.domain.entity.prompt.Prompt
import team.backend.domain.entity.prompt.PromptType
import team.backend.domain.repository.prompt.PromptReader

@Repository
class PromptReaderImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
): PromptReader {

    override suspend fun get(promptType: PromptType, positionType: PositionType): Prompt {
        return queryFactory.singleQuery {
            select(entity(Prompt::class))
            from(entity(Prompt::class))
            where(col(Prompt::promptType).equal(promptType))
            where(col(Prompt::positionType).equal(positionType))
        }
    }

    override suspend fun find(promptType: PromptType): List<Prompt> {
        return queryFactory.listQuery<Prompt> {
            select(entity(Prompt::class))
            from(entity(Prompt::class))
            where(col(Prompt::promptType).equal(promptType))
        }
    }
}