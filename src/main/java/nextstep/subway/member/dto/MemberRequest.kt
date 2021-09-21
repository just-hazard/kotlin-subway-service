package nextstep.subway.member.dto

import nextstep.subway.member.domain.Member

class MemberRequest {
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

    fun toMember(): Member {
        return Member(email, password, age)
    }
}
