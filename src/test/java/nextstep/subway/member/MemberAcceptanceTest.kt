package nextstep.subway.member

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.member.dto.MemberRequest
import nextstep.subway.member.dto.MemberResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType

class MemberAcceptanceTest : AcceptanceTest() {
    @DisplayName("회원 정보를 관리한다.")
    @Test
    fun manageMember() {
        // when
        val createResponse = 회원_생성을_요청(EMAIL, PASSWORD, AGE)
        // then
        회원_생성됨(createResponse)

        // when
        val findResponse = 회원_정보_조회_요청(createResponse)
        // then
        회원_정보_조회됨(findResponse, EMAIL, AGE)

        // when
        val updateResponse = 회원_정보_수정_요청(createResponse, NEW_EMAIL, NEW_PASSWORD, NEW_AGE)
        // then
        회원_정보_수정됨(updateResponse)

        // when
        val deleteResponse = 회원_삭제_요청(createResponse)
        // then
        회원_삭제됨(deleteResponse)
    }

    @DisplayName("나의 정보를 관리한다.")
    @Test
    fun manageMyInfo() {
    }

    companion object {
        const val EMAIL = "email@email.com"
        const val PASSWORD = "password"
        const val NEW_EMAIL = "newemail@email.com"
        const val NEW_PASSWORD = "newpassword"
        const val AGE = 20
        const val NEW_AGE = 21
        fun 회원_생성을_요청(email: String?, password: String?, age: Int?): ExtractableResponse<Response> {
            val memberRequest = MemberRequest(email!!, password!!, age!!)
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .`when`().post("/members")
                .then().log().all()
                .extract()
        }

        fun 회원_정보_조회_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
            val uri = response.header("Location")
            return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()[uri]
                .then().log().all()
                .extract()
        }

        fun 회원_정보_수정_요청(
            response: ExtractableResponse<Response>,
            email: String?,
            password: String?,
            age: Int?
        ): ExtractableResponse<Response> {
            val uri = response.header("Location")
            val memberRequest = MemberRequest(email!!, password!!, age!!)
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(memberRequest)
                .`when`().put(uri)
                .then().log().all()
                .extract()
        }

        fun 회원_삭제_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
            val uri = response.header("Location")
            return RestAssured
                .given().log().all()
                .`when`().delete(uri)
                .then().log().all()
                .extract()
        }

        fun 회원_생성됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
        }

        fun 회원_정보_조회됨(response: ExtractableResponse<Response>, email: String?, age: Int) {
            val memberResponse = response.`as`(MemberResponse::class.java)
            Assertions.assertThat(memberResponse.id).isNotNull
            Assertions.assertThat(memberResponse.email).isEqualTo(email)
            Assertions.assertThat(memberResponse.age).isEqualTo(age)
        }

        fun 회원_정보_수정됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        }

        fun 회원_삭제됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        }
    }
}
