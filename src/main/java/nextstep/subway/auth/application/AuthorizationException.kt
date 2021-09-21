package nextstep.subway.auth.application

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ResponseStatus
import java.lang.RuntimeException

@ResponseStatus(HttpStatus.UNAUTHORIZED)
class AuthorizationException : RuntimeException {
    constructor() {}
    constructor(message: String?) : super(message) {}
}
