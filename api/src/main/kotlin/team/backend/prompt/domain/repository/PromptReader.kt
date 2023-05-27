package team.backend.prompt.domain.repository

import team.backend.prompt.domain.entity.PositionType
import team.backend.prompt.domain.entity.Prompt
import team.backend.prompt.domain.entity.PromptType

interface PromptReader {

    suspend fun get(promptType: PromptType, positionType: PositionType): Prompt

    suspend fun find(promptType: PromptType): List<Prompt>
}