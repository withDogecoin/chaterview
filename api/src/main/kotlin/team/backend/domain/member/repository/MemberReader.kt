package team.backend.domain.member.repository

import team.backend.domain.member.entity.Member

interface MemberReader {

    suspend fun get(id: Long): Member
}