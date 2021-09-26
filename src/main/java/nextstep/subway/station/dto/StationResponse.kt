package nextstep.subway.station.dto

import nextstep.subway.station.domain.Station
import java.time.LocalDateTime

class StationResponse(
    var id: Long,
    var name: String,
    var createdDate: LocalDateTime,
    var modifiedDate: LocalDateTime
) {

    companion object {
        fun of(station: Station): StationResponse {
            return StationResponse(station.id, station.name, station.createdDate, station.modifiedDate)
        }
    }
}
