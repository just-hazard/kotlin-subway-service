package nextstep.subway.line.domain

import nextstep.subway.BaseEntity
import nextstep.subway.station.domain.Station
import java.util.ArrayList
import javax.persistence.*

@Entity
class Line(
    @Column(unique = true)
    var name: String = "",

    var color: String = "",

    @OneToMany(mappedBy = "line", cascade = [CascadeType.PERSIST, CascadeType.MERGE], orphanRemoval = true)
    private var sections: MutableList<Section> = mutableListOf()

) : BaseEntity() {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0

    constructor(name: String, color: String, upStation: Station, downStation: Station, distance: Int) : this(name, color,
        ArrayList<Section>()) {
        this.name = name
        this.color = color
        sections.add(Section(this, upStation, downStation, distance))
    }

    constructor(name: String, color: String): this(name, color,
        ArrayList<Section>()) {
        this.name = name
        this.color = color
    }

    fun update(line: Line) {
        name = line.name
        color = line.color
    }

    fun getSections(): MutableList<Section> {
        return sections
    }
}
