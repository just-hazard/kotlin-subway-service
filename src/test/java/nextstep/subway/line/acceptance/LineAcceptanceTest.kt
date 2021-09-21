package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.dto.LineRequest
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.station.StationAcceptanceTest
import nextstep.subway.station.dto.StationResponse
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import java.util.*
import java.util.stream.Collectors

@DisplayName("지하철 노선 관련 기능")
class LineAcceptanceTest : AcceptanceTest() {
    private lateinit var 강남역: StationResponse
    private lateinit var 광교역: StationResponse
    private lateinit var lineRequest1: LineRequest
    private lateinit var lineRequest2: LineRequest
    @BeforeEach
    override fun setUp() {
        super.setUp()

        // given
        강남역 = StationAcceptanceTest.지하철역_등록되어_있음("강남역").`as`(StationResponse::class.java)
        광교역 = StationAcceptanceTest.지하철역_등록되어_있음("광교역").`as`(StationResponse::class.java)
        lineRequest1 = LineRequest("신분당선", "bg-red-600", 강남역.id, 광교역.id, 10)
        lineRequest2 = LineRequest("구신분당선", "bg-red-600", 강남역.id, 광교역.id, 15)
    }

    @DisplayName("지하철 노선을 생성한다.")
    @Test
    fun createLine() {
        // when
        val response = 지하철_노선_생성_요청(lineRequest1)

        // then
        지하철_노선_생성됨(response)
    }

    @DisplayName("기존에 존재하는 지하철 노선 이름으로 지하철 노선을 생성한다.")
    @Test
    fun createLineWithDuplicateName() {
        // given
        지하철_노선_등록되어_있음(lineRequest1)

        // when
        val response = 지하철_노선_생성_요청(lineRequest1)

        // then
        지하철_노선_생성_실패됨(response)
    }

    // given
    @get:Test
    @get:DisplayName("지하철 노선 목록을 조회한다.")
    val lines:

    // when

    // then
            Unit
        get() {
            // given
            val createResponse1 = 지하철_노선_등록되어_있음(lineRequest1)
            val createResponse2 = 지하철_노선_등록되어_있음(lineRequest2)

            // when
            val response = 지하철_노선_목록_조회_요청()

            // then
            지하철_노선_목록_응답됨(response)
            지하철_노선_목록_포함됨(response, Arrays.asList(createResponse1, createResponse2))
        }

    // given
    @get:Test
    @get:DisplayName("지하철 노선을 조회한다.")
    val line:

    // when

    // then
            Unit
        get() {
            // given
            val createResponse = 지하철_노선_등록되어_있음(lineRequest1)

            // when
            val response = 지하철_노선_목록_조회_요청(createResponse)

            // then
            지하철_노선_응답됨(response, createResponse)
        }

    @DisplayName("지하철 노선을 수정한다.")
    @Test
    fun updateLine() {
        // given
        val name = "신분당선"
        val createResponse = 지하철_노선_등록되어_있음(lineRequest1)

        // when
        val response = 지하철_노선_수정_요청(createResponse, lineRequest2)

        // then
        지하철_노선_수정됨(response)
    }

    @DisplayName("지하철 노선을 제거한다.")
    @Test
    fun deleteLine() {
        // given
        val createResponse = 지하철_노선_등록되어_있음(lineRequest1)

        // when
        val response = 지하철_노선_제거_요청(createResponse)

        // then
        지하철_노선_삭제됨(response)
    }

    companion object {
        @JvmStatic
        fun 지하철_노선_등록되어_있음(params: LineRequest?): ExtractableResponse<Response> {
            return 지하철_노선_생성_요청(params)
        }

        fun 지하철_노선_생성_요청(params: LineRequest?): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .`when`().post("/lines")
                .then().log().all().extract()
        }

        fun 지하철_노선_목록_조회_요청(): ExtractableResponse<Response> {
            return 지하철_노선_목록_조회_요청("/lines")
        }

        fun 지하철_노선_목록_조회_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
            val uri = response.header("Location")
            return 지하철_노선_목록_조회_요청(uri)
        }

        private fun 지하철_노선_목록_조회_요청(uri: String): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()[uri]
                .then().log().all()
                .extract()
        }

        @JvmStatic
        fun 지하철_노선_조회_요청(response: LineResponse): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .accept(MediaType.APPLICATION_JSON_VALUE)
                .`when`()["/lines/{lineId}", response.id]
                .then().log().all()
                .extract()
        }

        fun 지하철_노선_수정_요청(response: ExtractableResponse<Response>, params: LineRequest?): ExtractableResponse<Response> {
            val uri = response.header("Location")
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(params)
                .`when`().put(uri)
                .then().log().all()
                .extract()
        }

        fun 지하철_노선_제거_요청(response: ExtractableResponse<Response>): ExtractableResponse<Response> {
            val uri = response.header("Location")
            return RestAssured
                .given().log().all()
                .`when`().delete(uri)
                .then().log().all()
                .extract()
        }

        fun 지하철_노선_생성됨(response: ExtractableResponse<*>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.CREATED.value())
            Assertions.assertThat(response.header("Location")).isNotBlank
        }

        fun 지하철_노선_생성_실패됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.BAD_REQUEST.value())
        }

        fun 지하철_노선_목록_응답됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        }

        fun 지하철_노선_응답됨(response: ExtractableResponse<Response>, createdResponse: ExtractableResponse<Response>?) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
            Assertions.assertThat(response.`as`(LineResponse::class.java)).isNotNull
        }

        fun 지하철_노선_목록_포함됨(
            response: ExtractableResponse<Response>,
            createdResponses: List<ExtractableResponse<Response>>
        ) {
            val expectedLineIds = createdResponses.stream()
                .map { it: ExtractableResponse<Response> ->
                    it.header("Location").split("/").toTypedArray()[2].toLong()
                }
                .collect(Collectors.toList())
            val resultLineIds = response.jsonPath().getList(".", LineResponse::class.java).stream()
                .map(LineResponse::id)
                .collect(Collectors.toList())
            Assertions.assertThat(resultLineIds).containsAll(expectedLineIds)
        }

        fun 지하철_노선_수정됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        }

        fun 지하철_노선_삭제됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.NO_CONTENT.value())
        }
    }
}
