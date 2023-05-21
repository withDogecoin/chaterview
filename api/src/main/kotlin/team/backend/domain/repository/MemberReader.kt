package team.backend.domain.repository

import team.backend.domain.entity.Member

interface MemberReader {

    suspend fun get(id: Long): Member
}