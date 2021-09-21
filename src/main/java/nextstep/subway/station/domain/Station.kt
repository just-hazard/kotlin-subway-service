package nextstep.subway.station.domain

import nextstep.subway.BaseEntity
import nextstep.subway.station.domain.Station
import java.util.*
import javax.persistence.*

@Entity
class Station : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(unique = true)
    var name: String? = null
        private set

    constructor() {}
    constructor(name: String?) {
        this.name = name
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val station = o as Station
        return id == station.id &&
                name == station.name
    }

    override fun hashCode(): Int {
        return Objects.hash(id, name)
    }
}
