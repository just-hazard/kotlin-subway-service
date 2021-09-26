package nextstep.subway.member.domain

import nextstep.subway.BaseEntity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import nextstep.subway.auth.application.AuthorizationException
import org.apache.commons.lang3.StringUtils
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Member(
    var email: String = "",
    var password: String = "",
    var age: Int = 0
) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    fun update(member: Member) {
        email = member.email
        password = member.password
        age = member.age
    }

    fun checkPassword(password: String) {
        if (!StringUtils.equals(this.password, password)) {
            throw AuthorizationException()
        }
    }
}
