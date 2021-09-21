package nextstep.subway.station.dto

import nextstep.subway.station.domain.Station

class StationRequest {
    var name: String? = null
        private set

    constructor() {}
    constructor(name: String?) {
        this.name = name
    }

    fun toStation(): Station {
        return Station(name)
    }
}
