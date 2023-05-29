package team.backend.domain.prompt.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import team.backend.domain.entity.BaseEntity

@Entity
@Table(name = "tbl_prompt")
class Prompt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prompt_id")
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "prompt_type", nullable = false)
    val promptType: PromptType,

    @Enumerated(EnumType.STRING)
    @Column(name = "position_type", nullable = false)
    val positionType: PositionType,

    @Column(nullable = false)
    val command: String,
): BaseEntity()

enum class PromptType {
    INTERVIEW_ANSWER
}

enum class PositionType {
    PREFIX, SUFFIX
}