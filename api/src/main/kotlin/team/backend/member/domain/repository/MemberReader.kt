package team.backend.member.domain.repository

import team.backend.member.domain.entity.Member

interface MemberReader {

    suspend fun get(id: Long): Member
}