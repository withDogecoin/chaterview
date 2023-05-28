package team.backend.domain.repository.member

import team.backend.domain.entity.member.MemberQuizAnswer

interface MemberQuizAnswerStore {

    suspend fun save(entity: MemberQuizAnswer)
}