package team.backend.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import team.backend.infrastructure.BaseEntity

@Entity
@Table(name = "tbl_prompt")
class Prompt(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "prompt_id")
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val type: PromptType? = null,

    @Column(nullable = false)
    val command: String? = ""
): BaseEntity()

enum class PromptType {
    INVERVIEW_ANSWER
}