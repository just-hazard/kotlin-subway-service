package nextstep.subway.station.dto

import nextstep.subway.station.domain.Station

class StationRequest {
    var name: String
        private set

    constructor(name: String) {
        this.name = name
    }

    fun toStation(): Station {
        return Station(name)
    }
}
