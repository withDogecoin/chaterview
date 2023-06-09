package team.backend.domain.member.entity

import jakarta.persistence.*
import team.backend.domain.entity.BaseEntity
import team.backend.domain.job.entity.Job
import team.backend.domain.quiz.entity.QuizLevel

@Entity
@Table(name = "tbl_member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    val id: Long? = null,

    @Column(name = "member_name", nullable = false)
    val name: String,

    @Enumerated(value = EnumType.STRING)
    @Column(name = "tier", nullable = false)
    val tier: Tier = Tier.BEGINNER,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    val job: Job,
): BaseEntity()

enum class Tier {
    BEGINNER,
    JUNIOR,
    SENIOR,
    ;

    fun matchedQuizLevels(): List<QuizLevel> {
        return when (this) {
            BEGINNER -> {
                listOf(QuizLevel.EASY)
            }
            JUNIOR -> {
                listOf(QuizLevel.EASY, QuizLevel.ADVANCED)
            }
            SENIOR -> {
                listOf(QuizLevel.ADVANCED, QuizLevel.EXPERT)
            }
        }
    }
}