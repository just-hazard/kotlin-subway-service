package study.unit

import com.google.common.collect.Lists
import nextstep.subway.line.application.LineService
import nextstep.subway.line.domain.Line
import nextstep.subway.line.domain.LineRepository
import nextstep.subway.station.application.StationService
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.mockito.Mockito

@DisplayName("단위 테스트 - mockito를 활용한 가짜 협력 객체 사용")
class MockitoTest {
    @Test
    fun findAllLines() {
        // given
        val lineRepository = Mockito.mock(LineRepository::class.java)
        val stationService = Mockito.mock(StationService::class.java)
        Mockito.`when`(lineRepository.findAll()).thenReturn(Lists.newArrayList<Line>(Line()))
        val lineService = LineService(lineRepository, stationService)

        // when
        val responses = lineService.findLines()

        // then
        Assertions.assertThat(responses).hasSize(1)
    }
}
