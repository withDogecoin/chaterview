package team.backend.domain.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import team.backend.infrastructure.BaseEntity

@Entity
@Table(name = "tbl_member_quiz_answer")
class MemberQuizAnswer(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_quiz_answer_id")
    val id: Long? = null,

    @Column(nullable = false)
    val correct: Boolean = true,

    @Column(nullable = false, columnDefinition = "TEXT")
    val answer: String = "",

    @Column(nullable = false, columnDefinition = "TEXT")
    val feedback: String = "",

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member? = null,

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quiz_id", nullable = false)
    val quiz: Quiz? = null,
): BaseEntity()