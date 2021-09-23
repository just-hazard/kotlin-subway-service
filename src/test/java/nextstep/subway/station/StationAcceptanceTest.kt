package nextstep.subway.station

import nextstep.subway.AcceptanceTest
import io.restassured.response.ExtractableResponse
import nextstep.subway.station.StationAcceptanceTest
import nextstep.subway.station.dto.StationRequest
import io.restassured.RestAssured
import io.restassured.response.Response
import java.util.stream.Collectors
import nextstep.subway.station.dto.StationResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.util.*

@DisplayName("지하철역 관련 기능")
class StationAcceptanceTest : AcceptanceTest() {
    @DisplayName("지하철역을 생성한다.")
    @Test
    fun createStation() {
        // when
        val response = 지하철역_생성_요청(강남역)

        // then
        지하철역_생성됨(response)
    }

    @DisplayName("기존에 존재하는 지하철역 이름으로 지하철역을 생성한다.")
    @Test
    fun createStationWithDuplicateName() {
        //given
        지하철역_등록되어_있음(강남역)

        // when
        val response = 지하철역_생성_요청(강남역)

        // then
        지하철역_생성_실패됨(response)
    }

    // given
    @get:Test
    @get:DisplayName("지하철역을 조회한다.")
    val stations:

    // when

    // then
            Unit
        get() {
            // given
            val createResponse1 = 지하철역_등록되어_있음(강남역)
            val createResponse2 = 지하철역_등록되어_있음(역삼역)

            // when
            val response = 지하철역_목록_조회_요청()

            // then
            지하철역_목록_응답됨(response)
            지하철역_목록_포함됨(response, Arrays.asList(createResponse1, createResponse2))
        }

    @DisplayName("지하철역을 제거한다.")
    @Test
    fun deleteStation() {
        // given
        val createResponse = 지하철역_등록되어_있음(강남역)

        // when
        val response = 지하철역_제거_요청(createResponse)

        // then
        지하철역_삭제됨(response)
    }

    companion object {
        private const val 강남역 = "강남역"
        private const val 역삼역 = "역삼역"
        fun 지하철역_등록되어_있음(name: String?): ExtractableResponse<Response> {
            return 지하철역_생성_요청(name)
        }

        fun 지하철역_생성_요청(name: String?): ExtractableResponse<Response> {
            val stationRequest = StationRequest(name)
            return RestAssured
                .given().log().all()
                .body(stationRequest)
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .`when`().post("/stations")
                .then().log().all()
                .extract()
        }

        fun 지하철역_목록_조회_요청(): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .`when`()["/stations"]
                .then().log().all()
                .extract()
        }

        fun 지하철역_제거_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
            val uri = response.header("Location")
            return RestAssured
                .given().log().all()
                .`when`().delete(uri)
                .then().log().all()
                .extract()
        }

        fun 지하철역_생성됨(response: ExtractableResponse<*>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
            Assertions.assertThat(response.header("Location")).isNotBlank
        }

        fun 지하철역_생성_실패됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        }

        fun 지하철역_목록_응답됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        }

        fun 지하철역_삭제됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        }

        fun 지하철역_목록_포함됨(
            response: ExtractableResponse<Response>,
            createdResponses: List<ExtractableResponse<Response>>
        ) {
            val expectedLineIds = createdResponses.stream()
                .map { it: ExtractableResponse<Response> ->
                    it.header("Location").split("/").toTypedArray()[2].toLong()
                }
                .collect(Collectors.toList())
            val resultLineIds = response.jsonPath().getList(".", StationResponse::class.java).stream()
                .map(StationResponse::id)
                .collect(Collectors.toList())
            Assertions.assertThat(resultLineIds).containsAll(expectedLineIds)
        }
    }
}
