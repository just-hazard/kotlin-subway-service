package nextstep.subway.member.dto

import nextstep.subway.member.domain.Member

class MemberRequest(
    var email: String,
    var password: String,
    var age: Int
) {

    fun toMember(): Member {
        return Member(email, password, age)
    }
}
