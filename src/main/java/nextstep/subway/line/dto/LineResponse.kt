package nextstep.subway.line.dto

import nextstep.subway.line.domain.Line
import nextstep.subway.station.dto.StationResponse
import java.time.LocalDateTime

class LineResponse(
    var id: Long,
    var name: String,
    var color: String,
    var stations: List<StationResponse>,
    var createdDate: LocalDateTime,
    var modifiedDate: LocalDateTime
) {
    companion object {
        fun of(line: Line, stations: List<StationResponse>): LineResponse {
            return LineResponse(line.id, line.name, line.color, stations, line.createdDate, line.modifiedDate)
        }
    }
}
