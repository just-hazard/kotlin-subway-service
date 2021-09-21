package nextstep.subway.auth.dto

class TokenResponse {
    var accessToken: String? = null
        private set

    constructor() {}
    constructor(accessToken: String?) {
        this.accessToken = accessToken
    }
}
