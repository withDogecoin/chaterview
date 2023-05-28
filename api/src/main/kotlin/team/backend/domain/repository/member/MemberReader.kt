package team.backend.domain.repository.member

import team.backend.domain.entity.member.Member

interface MemberReader {

    suspend fun get(id: Long): Member
}