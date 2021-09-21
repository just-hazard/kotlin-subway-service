package nextstep.subway.line.domain

import nextstep.subway.BaseEntity
import nextstep.subway.station.domain.Station
import java.util.ArrayList
import javax.persistence.*

@Entity
class Line : BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @Column(unique = true)
    var name: String? = null
        private set
    var color: String? = null
        private set

    @OneToMany(mappedBy = "line", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private val sections: MutableList<Section> = ArrayList()

    constructor() {}
    constructor(name: String?, color: String?) {
        this.name = name
        this.color = color
    }

    constructor(name: String?, color: String?, upStation: Station?, downStation: Station?, distance: Int) {
        this.name = name
        this.color = color
        sections.add(Section(this, upStation, downStation, distance))
    }

    fun update(line: Line) {
        name = line.name
        color = line.color
    }

    fun getSections(): List<Section> {
        return sections
    }
}
