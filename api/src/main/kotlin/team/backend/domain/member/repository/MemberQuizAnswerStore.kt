package team.backend.domain.member.repository

import team.backend.domain.member.entity.MemberQuizAnswer

interface MemberQuizAnswerStore {

    suspend fun save(entity: MemberQuizAnswer)
}