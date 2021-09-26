package nextstep.subway.line.dto

import nextstep.subway.line.domain.Line
import nextstep.subway.station.dto.StationResponse
import java.time.LocalDateTime

class LineResponse {
    var id: Long? = null
        private set
    var name: String? = null
        private set
    var color: String? = null
        private set
    var stations: List<StationResponse>? = null
        private set
    var createdDate: LocalDateTime? = null
        private set
    var modifiedDate: LocalDateTime? = null
        private set

    constructor(
        id: Long?,
        name: String?,
        color: String?,
        stations: List<StationResponse>?,
        createdDate: LocalDateTime?,
        modifiedDate: LocalDateTime?
    ) {
        this.id = id
        this.name = name
        this.color = color
        this.stations = stations
        this.createdDate = createdDate
        this.modifiedDate = modifiedDate
    }

    companion object {
        fun of(line: Line, stations: List<StationResponse>): LineResponse {
            return LineResponse(line.id, line.name, line.color, stations, line.createdDate, line.modifiedDate)
        }
    }
}
