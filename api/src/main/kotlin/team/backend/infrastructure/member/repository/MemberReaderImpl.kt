package team.backend.infrastructure.member.repository

import com.linecorp.kotlinjdsl.querydsl.expression.col
import com.linecorp.kotlinjdsl.spring.data.reactive.query.SpringDataHibernateMutinyReactiveQueryFactory
import com.linecorp.kotlinjdsl.spring.data.reactive.query.singleQueryOrNull
import org.springframework.stereotype.Repository
import team.backend.domain.member.entity.Member
import team.backend.domain.member.repository.MemberReader

@Repository
class MemberReaderImpl(
    private val queryFactory: SpringDataHibernateMutinyReactiveQueryFactory,
): MemberReader {

    override suspend fun find(id: Long): Member? {
        return queryFactory.singleQueryOrNull{
            select(entity(Member::class))
            from(entity(Member::class))
            where(col(Member::id).equal(id))
        }
    }
}