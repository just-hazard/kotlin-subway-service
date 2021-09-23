package nextstep.subway.station.domain

import nextstep.subway.BaseEntity
import nextstep.subway.station.domain.Station
import java.util.*
import javax.persistence.*

@Entity
class Station(
    @Column(unique = true)
    var name: String
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0
}
