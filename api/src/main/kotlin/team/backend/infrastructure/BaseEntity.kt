package team.backend.infrastructure

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners
abstract class BaseEntity {
    @CreatedDate
    var createdAt: LocalDateTime = LocalDateTime.now()

    @CreatedBy
    var createdBy: String = SYSTEM_PROPERTY

    @LastModifiedDate
    var lastModifiedAt: LocalDateTime = LocalDateTime.now()

    @LastModifiedBy
    var lastModifiedBy: String = SYSTEM_PROPERTY
}

const val SYSTEM_PROPERTY = "SYSTEM"