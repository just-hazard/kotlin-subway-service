package nextstep.subway.line.domain

import nextstep.subway.station.domain.Station
import java.lang.RuntimeException
import javax.persistence.*

@Entity
class Section(
    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "line_id")
    var line: Line,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "up_station_id")
    var upStation: Station,

    @ManyToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "down_station_id")
    var downStation: Station,

    var distance: Int
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0


    fun updateUpStation(station: Station, newDistance: Int) {
        if (distance <= newDistance) {
            throw RuntimeException("역과 역 사이의 거리보다 좁은 거리를 입력해주세요")
        }
        upStation = station
        distance -= newDistance
    }

    fun updateDownStation(station: Station, newDistance: Int) {
        if (distance <= newDistance) {
            throw RuntimeException("역과 역 사이의 거리보다 좁은 거리를 입력해주세요")
        }
        downStation = station
        distance -= newDistance
    }
}
