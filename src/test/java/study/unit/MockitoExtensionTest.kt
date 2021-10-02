//package study.unit
//
//import com.google.common.collect.Lists
//import nextstep.subway.line.application.LineService
//import nextstep.subway.line.domain.Line
//import nextstep.subway.line.domain.LineRepository
//import nextstep.subway.station.application.StationService
//import org.assertj.core.api.Assertions
//import org.junit.jupiter.api.DisplayName
//import org.junit.jupiter.api.Test
//import org.junit.jupiter.api.extension.ExtendWith
//import org.mockito.Mock
//import org.mockito.Mockito
//import org.mockito.junit.jupiter.MockitoExtension
//
//@DisplayName("단위 테스트 - mockito의 MockitoExtension을 활용한 가짜 협력 객체 사용")
//@ExtendWith(MockitoExtension::class)
//class MockitoExtensionTest {
//    @Mock
//    private val lineRepository: LineRepository? = null
//
//    @Mock
//    private val stationService: StationService? = null
//    @Test
//    fun findAllLines() {
//        // given
//        Mockito.`when`(lineRepository!!.findAll()).thenReturn(Lists.newArrayList<Line>(Line()))
//        val lineService = LineService(lineRepository, stationService!!)
//
//        // when
//        val responses = lineService.findLines()
//
//        // then
//        Assertions.assertThat(responses).hasSize(1)
//    }
//}
