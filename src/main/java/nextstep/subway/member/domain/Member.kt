package nextstep.subway.member.domain

import nextstep.subway.BaseEntity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import nextstep.subway.auth.application.AuthorizationException
import org.apache.commons.lang3.StringUtils
import javax.persistence.Entity
import javax.persistence.Id

@Entity
class Member : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null
    var email: String? = null
        private set
    var password: String? = null
        private set
    var age: Int? = null
        private set

    constructor() {}
    constructor(email: String?, password: String?, age: Int?) {
        this.email = email
        this.password = password
        this.age = age
    }

    fun update(member: Member) {
        email = member.email
        password = member.password
        age = member.age
    }

    fun checkPassword(password: String?) {
        if (!StringUtils.equals(this.password, password)) {
            throw AuthorizationException()
        }
    }
}
