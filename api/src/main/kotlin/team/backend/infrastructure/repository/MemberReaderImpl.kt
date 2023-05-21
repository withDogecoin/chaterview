package team.backend.infrastructure.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQuery
import org.springframework.stereotype.Repository
import team.backend.domain.entity.Member
import team.backend.domain.repository.MemberReader

@Repository
class MemberReaderImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
): MemberReader {

    override suspend fun get(id: Long): Member {
        return queryFactory.singleQuery {
            select(entity(Member::class))
            from(entity(Member::class))
            where(col(Member::id).equal(id))
        }
    }
}