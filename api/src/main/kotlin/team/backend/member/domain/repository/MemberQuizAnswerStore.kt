package team.backend.member.domain.repository

import team.backend.member.domain.entity.MemberQuizAnswer

interface MemberQuizAnswerStore {

    suspend fun save(entity: MemberQuizAnswer)
}