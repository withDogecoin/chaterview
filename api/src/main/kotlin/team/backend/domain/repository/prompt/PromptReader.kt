package team.backend.domain.repository.prompt

import team.backend.domain.entity.prompt.PositionType
import team.backend.domain.entity.prompt.Prompt
import team.backend.domain.entity.prompt.PromptType

interface PromptReader {

    suspend fun get(promptType: PromptType, positionType: PositionType): Prompt

    suspend fun find(promptType: PromptType): List<Prompt>
}