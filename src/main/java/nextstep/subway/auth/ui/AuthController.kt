package nextstep.subway.auth.ui

import org.springframework.web.bind.annotation.RestController
import nextstep.subway.auth.application.AuthService
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import nextstep.subway.auth.dto.TokenRequest
import org.springframework.http.ResponseEntity
import nextstep.subway.auth.dto.TokenResponse

@RestController
class AuthController(private val authService: AuthService) {
    @PostMapping("/login/token")
    fun login(@RequestBody request: TokenRequest?): ResponseEntity<TokenResponse> {
        val token = authService.login(request!!)
        return ResponseEntity.ok().body(token)
    }
}
