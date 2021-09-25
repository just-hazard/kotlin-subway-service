package nextstep.subway

import javax.persistence.EntityListeners
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.data.annotation.CreatedDate
import java.time.LocalDateTime
import org.springframework.data.annotation.LastModifiedDate
import javax.persistence.MappedSuperclass

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {
    @CreatedDate
    val createdDate: LocalDateTime = LocalDateTime.now()

    @LastModifiedDate
    val modifiedDate: LocalDateTime = LocalDateTime.now()
}
