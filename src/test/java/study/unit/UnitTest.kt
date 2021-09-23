package study.unit

import nextstep.subway.line.domain.Line
import nextstep.subway.station.domain.Station
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test

@DisplayName("단위 테스트")
class UnitTest {
    @Test
    fun update() {
        // given
        val newName = "구분당선"
        val upStation = Station("강남역")
        val downStation = Station("광교역")
        val line = Line("신분당선", "RED", upStation, downStation, 10)
        val newLine = Line(newName, "GREEN")

        // when
        line.update(newLine)

        // then
        Assertions.assertThat(line.name).isEqualTo(newName)
    }
}
