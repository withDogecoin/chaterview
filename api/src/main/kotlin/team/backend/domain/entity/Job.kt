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
@Table(name = "tbl_job")
class Job(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "job_id")
    val id: Long? = null,

    @Enumerated(EnumType.STRING)
    @Column(name = "job_type", nullable = false, insertable = true, updatable = false)
    val type: JobType,
): BaseEntity()

enum class JobType {
    BACKEND_ENGINEER,
    FRONTEND_ENGINEER,
    CLOUD_ENGINEER
}