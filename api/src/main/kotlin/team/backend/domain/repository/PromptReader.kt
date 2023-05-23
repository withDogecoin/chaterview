package team.backend.domain.repository

import team.backend.domain.entity.PositionType
import team.backend.domain.entity.Prompt
import team.backend.domain.entity.PromptType

interface PromptReader {

    suspend fun get(promptType: PromptType, positionType: PositionType): Prompt
}