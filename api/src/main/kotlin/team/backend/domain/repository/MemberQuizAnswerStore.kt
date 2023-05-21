package team.backend.domain.repository

import team.backend.domain.entity.MemberQuizAnswer

interface MemberQuizAnswerStore {

    suspend fun save(entity: MemberQuizAnswer)
}