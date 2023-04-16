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
@Table(name = "tbl_subject")
class Subject(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "subject_id")
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, insertable = false, updatable = false)
    val type: SubjectType? = null
): BaseEntity()

enum class SubjectType {
    SPRING,
    JAVA,
    NETWORK,
    OS,
}