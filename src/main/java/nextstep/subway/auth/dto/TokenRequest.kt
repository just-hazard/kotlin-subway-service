package nextstep.subway.auth.dto

class TokenRequest {
    var email: String
        private set
    var password: String
        private set

    constructor(email: String, password: String) {
        this.email = email
        this.password = password
    }
}
