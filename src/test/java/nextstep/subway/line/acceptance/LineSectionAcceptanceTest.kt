package nextstep.subway.line.acceptance

import io.restassured.RestAssured
import io.restassured.response.ExtractableResponse
import io.restassured.response.Response
import nextstep.subway.AcceptanceTest
import nextstep.subway.line.acceptance.LineAcceptanceTest.Companion.지하철_노선_등록되어_있음
import nextstep.subway.line.acceptance.LineAcceptanceTest.Companion.지하철_노선_조회_요청
import nextstep.subway.line.dto.LineRequest
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.line.dto.SectionRequest
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

@DisplayName("지하철 구간 관련 기능")
class LineSectionAcceptanceTest : AcceptanceTest() {
    private lateinit var 강남역: StationResponse
    private lateinit var 신분당선: LineResponse
    private lateinit var 양재역: StationResponse
    private lateinit var 정자역: StationResponse
    private lateinit var 광교역: StationResponse
    @BeforeEach
    override fun setUp() {
        super.setUp()
        강남역 = StationAcceptanceTest.지하철역_등록되어_있음("강남역").`as`(StationResponse::class.java)
        양재역 = StationAcceptanceTest.지하철역_등록되어_있음("양재역").`as`(StationResponse::class.java)
        정자역 = StationAcceptanceTest.지하철역_등록되어_있음("정자역").`as`(StationResponse::class.java)
        광교역 = StationAcceptanceTest.지하철역_등록되어_있음("광교역").`as`(StationResponse::class.java)
        val lineRequest = LineRequest("신분당선", "bg-red-600", 강남역.id, 광교역.id, 10)
        신분당선 = 지하철_노선_등록되어_있음(lineRequest).`as`(LineResponse::class.java)
    }

    @DisplayName("지하철 구간을 등록한다.")
    @Test
    fun addLineSection() {
        // when
        지하철_노선에_지하철역_등록_요청(신분당선, 강남역, 양재역, 3)

        // then
        val response: ExtractableResponse<Response> = 지하철_노선_조회_요청(신분당선)
        지하철_노선에_지하철역_등록됨(response)
        지하철_노선에_지하철역_순서_정렬됨(response, Arrays.asList(강남역, 양재역, 광교역))
    }

    @DisplayName("지하철 노선에 여러개의 역을 순서 상관 없이 등록한다.")
    @Test
    fun addLineSection2() {
        // when
        지하철_노선에_지하철역_등록_요청(신분당선, 강남역, 양재역, 2)
        지하철_노선에_지하철역_등록_요청(신분당선, 정자역, 강남역, 5)

        // then
        val response: ExtractableResponse<Response> = 지하철_노선_조회_요청(신분당선)
        지하철_노선에_지하철역_등록됨(response)
        지하철_노선에_지하철역_순서_정렬됨(response, Arrays.asList(정자역, 강남역, 양재역, 광교역))
    }

    @DisplayName("지하철 노선에 이미 등록되어있는 역을 등록한다.")
    @Test
    fun addLineSectionWithSameStation() {
        // when
        val response = 지하철_노선에_지하철역_등록_요청(신분당선, 강남역, 광교역, 3)

        // then
        지하철_노선에_지하철역_등록_실패됨(response)
    }

    @DisplayName("지하철 노선에 등록되지 않은 역을 기준으로 등록한다.")
    @Test
    fun addLineSectionWithNoStation() {
        // when
        val response = 지하철_노선에_지하철역_등록_요청(신분당선, 정자역, 양재역, 3)

        // then
        지하철_노선에_지하철역_등록_실패됨(response)
    }

    @DisplayName("지하철 노선에 등록된 지하철역을 제외한다.")
    @Test
    fun removeLineSection1() {
        // given
        지하철_노선에_지하철역_등록_요청(신분당선, 강남역, 양재역, 2)
        지하철_노선에_지하철역_등록_요청(신분당선, 양재역, 정자역, 2)

        // when
        val removeResponse = 지하철_노선에_지하철역_제외_요청(신분당선, 양재역)

        // then
        지하철_노선에_지하철역_제외됨(removeResponse)
        val response: ExtractableResponse<Response> = 지하철_노선_조회_요청(신분당선)
        지하철_노선에_지하철역_순서_정렬됨(response, Arrays.asList(강남역, 정자역, 광교역))
    }

    @DisplayName("지하철 노선에 등록된 지하철역이 두개일 때 한 역을 제외한다.")
    @Test
    fun removeLineSection2() {
        // when
        val removeResponse = 지하철_노선에_지하철역_제외_요청(신분당선, 강남역)

        // then
        지하철_노선에_지하철역_제외_실패됨(removeResponse)
    }

    companion object {
        fun 지하철_노선에_지하철역_등록_요청(
            line: LineResponse?,
            upStation: StationResponse?,
            downStation: StationResponse?,
            distance: Int
        ): ExtractableResponse<Response> {
            val sectionRequest = SectionRequest(upStation!!.id, downStation!!.id, distance)
            return RestAssured
                .given().log().all()
                .contentType(MediaType.APPLICATION_JSON_VALUE)
                .body(sectionRequest)
                .`when`().post("/lines/{lineId}/sections", line!!.id)
                .then().log().all()
                .extract()
        }

        fun 지하철_노선에_지하철역_등록됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        }

        fun 지하철_노선에_지하철역_등록_실패됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        }

        fun 지하철_노선에_지하철역_순서_정렬됨(response: ExtractableResponse<Response>, expectedStations: List<StationResponse?>) {
            val line = response.`as`(LineResponse::class.java)
            val stationIds = line.stations!!.stream()
                .map { it: StationResponse -> it.id }
                .collect(Collectors.toList())
            val expectedStationIds = expectedStations.stream()
                .map { it: StationResponse? -> it!!.id }
                .collect(Collectors.toList())
            Assertions.assertThat(stationIds).containsExactlyElementsOf(expectedStationIds)
        }

        fun 지하철_노선에_지하철역_제외_요청(line: LineResponse?, station: StationResponse?): ExtractableResponse<Response> {
            return RestAssured
                .given().log().all()
                .`when`().delete("/lines/{lineId}/sections?stationId={stationId}", line!!.id, station!!.id)
                .then().log().all()
                .extract()
        }

        fun 지하철_노선에_지하철역_제외됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.OK.value())
        }

        fun 지하철_노선에_지하철역_제외_실패됨(response: ExtractableResponse<Response>) {
            Assertions.assertThat(response.statusCode()).isEqualTo(HttpStatus.INTERNAL_SERVER_ERROR.value())
        }
    }
}
