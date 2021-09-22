package nextstep.subway.line.dto

import nextstep.subway.line.domain.Line

class LineRequest(
    var name: String,
    var color: String,
    var upStationId: Long,
    var downStationId: Long,
    var distance: Int

) {

    fun toLine(): Line {
        return Line(name, color)
    }
}
