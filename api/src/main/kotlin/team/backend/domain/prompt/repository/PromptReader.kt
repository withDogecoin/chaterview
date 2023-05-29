package team.backend.domain.prompt.repository

import team.backend.domain.prompt.entity.PositionType
import team.backend.domain.prompt.entity.Prompt
import team.backend.domain.prompt.entity.PromptType

interface PromptReader {

    suspend fun get(promptType: PromptType, positionType: PositionType): Prompt

    suspend fun find(promptType: PromptType): List<Prompt>
}