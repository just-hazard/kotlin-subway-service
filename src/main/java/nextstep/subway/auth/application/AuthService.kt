package nextstep.subway.auth.application

import nextstep.subway.member.domain.MemberRepository
import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.auth.dto.TokenRequest
import nextstep.subway.auth.dto.TokenResponse
import nextstep.subway.auth.application.AuthorizationException
import nextstep.subway.auth.domain.LoginMember
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class AuthService(private val memberRepository: MemberRepository, private val jwtTokenProvider: JwtTokenProvider) {
    fun login(request: TokenRequest): TokenResponse {
        val member = memberRepository.findByEmail(request.email).orElseThrow { AuthorizationException() }
        member.checkPassword(request.password)
        val token = jwtTokenProvider.createToken(request.email)
        return TokenResponse(token)
    }

    fun findMemberByToken(credentials: String?): LoginMember {
        if (!jwtTokenProvider.validateToken(credentials)) {
            return LoginMember()
        }
        val email = jwtTokenProvider.getPayload(credentials)
        val member = memberRepository.findByEmail(email).orElseThrow { RuntimeException() }
        return LoginMember(member.id, member.email, member.age)
    }
}
