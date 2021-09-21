package nextstep.subway.member.dto

import nextstep.subway.member.domain.Member

class MemberResponse {
    var id: Long? = null
        private set
    var email: String? = null
        private set
    var age: Int? = null
        private set

    constructor() {}
    constructor(id: Long?, email: String?, age: Int?) {
        this.id = id
        this.email = email
        this.age = age
    }

    companion object {
        fun of(member: Member): MemberResponse {
            return MemberResponse(member.id, member.email, member.age)
        }
    }
}
