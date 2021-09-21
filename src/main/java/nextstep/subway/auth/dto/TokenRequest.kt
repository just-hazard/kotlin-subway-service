package nextstep.subway.auth.dto

class TokenRequest {
    var email: String? = null
        private set
    var password: String? = null
        private set

    constructor() {}
    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }
}
