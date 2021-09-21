package nextstep.subway.line.dto

import nextstep.subway.line.domain.Line

class LineRequest {
    var name: String? = null
        private set
    var color: String? = null
        private set
    var upStationId: Long? = null
        private set
    var downStationId: Long? = null
        private set
    var distance = 0
        private set

    constructor() {}
    constructor(name: String?, color: String?, upStationId: Long?, downStationId: Long?, distance: Int) {
        this.name = name
        this.color = color
        this.upStationId = upStationId
        this.downStationId = downStationId
        this.distance = distance
    }

    fun toLine(): Line {
        return Line(name, color)
    }
}
