package nextstep.subway.auth.acceptance

import nextstep.subway.AcceptanceTest
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

class AuthAcceptanceTest : AcceptanceTest() {
    @DisplayName("Bearer Auth")
    @Test
    fun myInfoWithBearerAuth() {
    }

    @DisplayName("Bearer Auth 로그인 실패")
    @Test
    fun myInfoWithBadBearerAuth() {
    }

    @DisplayName("Bearer Auth 유효하지 않은 토큰")
    @Test
    fun myInfoWithWrongBearerAuth() {
    }
}
