package team.backend.domain.member.repository

import team.backend.domain.member.entity.Member

interface MemberReader {

    suspend fun find(id: Long): Member?
}