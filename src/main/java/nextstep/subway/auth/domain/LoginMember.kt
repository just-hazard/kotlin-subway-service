package nextstep.subway.auth.domain

class LoginMember {
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
}
