package team.backend.domain.entity

import team.backend.infrastructure.BaseEntity
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table

@Entity
@Table(name = "tbl_quiz")
class Quiz(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "quiz_id")
    val id: Long? = null,

    @Column(nullable = false, columnDefinition="TEXT")
    val question: String = "",

    @Column(nullable = false)
    val active: Boolean = true,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val level: QuizLevel? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_id", nullable = false)
    val job: Job? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "subject_id", nullable = false)
    val subject: Subject? = null,
): BaseEntity()

enum class QuizLevel {
    EASY, NORMAL, HARD, EXPERT
}