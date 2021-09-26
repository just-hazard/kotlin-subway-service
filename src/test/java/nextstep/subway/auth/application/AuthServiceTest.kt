package nextstep.subway.auth.application

import nextstep.subway.auth.dto.TokenRequest
import nextstep.subway.auth.infrastructure.JwtTokenProvider
import nextstep.subway.member.domain.Member
import nextstep.subway.member.domain.MemberRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.ArgumentMatchers
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.jupiter.MockitoExtension
import java.util.*

@ExtendWith(MockitoExtension::class)
class AuthServiceTest {
    private var authService: AuthService? = null

    @Mock
    private val memberRepository: MemberRepository? = null

    @Mock
    private val jwtTokenProvider: JwtTokenProvider? = null
    @BeforeEach
    fun setUp() {
        authService = AuthService(memberRepository!!, jwtTokenProvider!!)
    }

    @Test
    fun login() {
        Mockito.`when`(memberRepository!!.findByEmail(ArgumentMatchers.anyString()))
            .thenReturn(Optional.of(Member(EMAIL, PASSWORD, AGE)))
        Mockito.`when`(jwtTokenProvider!!.createToken(ArgumentMatchers.anyString())).thenReturn("TOKEN")
        val token = authService!!.login(TokenRequest(EMAIL, PASSWORD))
        Assertions.assertThat(token.accessToken).isNotBlank
    }

    companion object {
        const val EMAIL = "email@email.com"
        const val PASSWORD = "password"
        const val AGE = 10
    }
}
