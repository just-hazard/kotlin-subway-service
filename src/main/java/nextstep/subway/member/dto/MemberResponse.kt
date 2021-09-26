package nextstep.subway.member.dto

import nextstep.subway.member.domain.Member

class MemberResponse(
    var id: Long,
    var email: String,
    var age: Int
) {

    companion object {
        fun of(member: Member): MemberResponse {
            return MemberResponse(member.id, member.email, member.age)
        }
    }
}
