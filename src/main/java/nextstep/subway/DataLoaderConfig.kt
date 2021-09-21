package nextstep.subway

import com.google.common.collect.Lists
import nextstep.subway.line.domain.Line
import nextstep.subway.line.domain.LineRepository
import nextstep.subway.member.domain.Member
import nextstep.subway.member.domain.MemberRepository
import org.springframework.boot.CommandLineRunner
import kotlin.Throws
import nextstep.subway.station.domain.Station
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component
import java.lang.Exception

@Component
@Profile("!test")
class DataLoaderConfig(private val lineRepository: LineRepository, private val memberRepository: MemberRepository) :
    CommandLineRunner {
    @Throws(Exception::class)
    override fun run(vararg args: String) {
        val 강남역 = Station("강남역")
        val 교대역 = Station("교대역")
        val 양재역 = Station("양재역")
        val 남부터미널역 = Station("남부터미널역")
        val 신분당선 = Line("신분당선", "red lighten-1", 강남역, 양재역, 10)
        val 이호선 = Line("2호선", "green lighten-1", 교대역, 강남역, 10)
        val 삼호선 = Line("3호선", "orange darken-1", 교대역, 양재역, 10)
        lineRepository.saveAll(Lists.newArrayList(신분당선, 이호선, 삼호선))
        memberRepository.save(Member("probitanima11@gmail.com", "11", 10))
    }
}
