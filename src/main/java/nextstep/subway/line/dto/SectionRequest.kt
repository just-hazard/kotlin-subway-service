package nextstep.subway.line.dto

class SectionRequest {
    var upStationId: Long? = null
        private set
    var downStationId: Long? = null
        private set
    var distance = 0
        private set

    constructor() {}
    constructor(upStationId: Long?, downStationId: Long?, distance: Int) {
        this.upStationId = upStationId
        this.downStationId = downStationId
        this.distance = distance
    }
}
