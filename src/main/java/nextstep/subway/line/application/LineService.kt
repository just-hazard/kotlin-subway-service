package nextstep.subway.line.application

import nextstep.subway.line.domain.Line
import nextstep.subway.line.domain.LineRepository
import nextstep.subway.line.domain.Section
import nextstep.subway.station.application.StationService
import nextstep.subway.line.dto.LineRequest
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.station.domain.Station
import nextstep.subway.station.dto.StationResponse
import java.util.stream.Collectors
import java.lang.RuntimeException
import nextstep.subway.line.dto.SectionRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
@Transactional
class LineService(private val lineRepository: LineRepository, private val stationService: StationService) {
    fun saveLine(request: LineRequest): LineResponse {
        val upStation = stationService.findById(request.upStationId)
        val downStation = stationService.findById(request.downStationId)
        val persistLine =
            lineRepository.save(Line(request.name, request.color, upStation, downStation, request.distance))
        val stations = getStations(persistLine).stream()
            .map { StationResponse.of(it) }
            .collect(Collectors.toList())
        return LineResponse.of(persistLine, stations)
    }

    fun findLines(): List<LineResponse> {
        val persistLines = lineRepository.findAll()
        return persistLines.stream()
            .map { line ->
                val stations = getStations(line).stream()
                    .map { StationResponse.of(it) }
                    .collect(Collectors.toList())
                LineResponse.of(line, stations)
            }
            .collect(Collectors.toList())
    }

    fun findLineById(id: Long): Line {
        return lineRepository.findById(id).orElseThrow { RuntimeException() }
    }

    fun findLineResponseById(id: Long): LineResponse {
        val persistLine = findLineById(id)
        val stations = getStations(persistLine).stream()
            .map { StationResponse.of(it) }
            .collect(Collectors.toList())
        return LineResponse.of(persistLine, stations)
    }

    fun updateLine(id: Long, lineUpdateRequest: LineRequest) {
        val persistLine = lineRepository.findById(id).orElseThrow { RuntimeException() }
        persistLine.update(Line(lineUpdateRequest.name, lineUpdateRequest.color))
    }

    fun deleteLineById(id: Long) {
        lineRepository.deleteById(id)
    }

    fun addLineStation(lineId: Long, request: SectionRequest) {
        val line = findLineById(lineId)
        val upStation = stationService.findStationById(request.upStationId)
        val downStation = stationService.findStationById(request.downStationId)
        val stations = getStations(line)
        val isUpStationExisted = stations.stream().anyMatch { it === upStation }
        val isDownStationExisted = stations.stream().anyMatch { it === downStation }
        if (isUpStationExisted && isDownStationExisted) {
            throw RuntimeException("이미 등록된 구간 입니다.")
        }
        if (!stations.isEmpty() && stations.stream().noneMatch { it === upStation } &&
            stations.stream().noneMatch { it === downStation }) {
            throw RuntimeException("등록할 수 없는 구간 입니다.")
        }
        if (stations.isEmpty()) {
            line.getSections().add(Section(line, upStation, downStation, request.distance))
            return
        }
        if (isUpStationExisted) {
            line.getSections().stream()
                .filter { it.upStation === upStation }
                .findFirst()
                .ifPresent { it.updateUpStation(downStation, request.distance) }
            line.getSections().add(Section(line, upStation, downStation, request.distance))
        } else if (isDownStationExisted) {
            line.getSections().stream()
                .filter { it.downStation === downStation }
                .findFirst()
                .ifPresent { it.updateDownStation(upStation, request.distance) }
            line.getSections().add(Section(line, upStation, downStation, request.distance))
        } else {
            throw RuntimeException()
        }
    }

    fun removeLineStation(lineId: Long, stationId: Long) {
        val line = findLineById(lineId)
        val station = stationService.findStationById(stationId)
        if (line.getSections().size <= 1) {
            throw RuntimeException()
        }
        val upLineStation = line.getSections().stream()
            .filter { it: Section -> it.upStation === station }
            .findFirst()
        val downLineStation = line.getSections().stream()
            .filter { it: Section -> it.downStation === station }
            .findFirst()
        if (upLineStation.isPresent && downLineStation.isPresent) {
            val newUpStation = downLineStation.get().upStation
            val newDownStation = upLineStation.get().downStation
            val newDistance = upLineStation.get().distance + downLineStation.get().distance
            line.getSections().add(Section(line, newUpStation, newDownStation, newDistance))
        }
        upLineStation.ifPresent { line.getSections().remove(it) }
        downLineStation.ifPresent { line.getSections().remove(it) }
    }

    fun getStations(line: Line): List<Station> {
        if (line.getSections().isEmpty()) {
            return Arrays.asList()
        }
        val stations: MutableList<Station> = ArrayList()
        var downStation = findUpStation(line)
        stations.add(downStation)
        while (downStation != null) {
            val finalDownStation: Station = downStation
            val nextLineStation = line.getSections().stream()
                .filter { it.upStation === finalDownStation }
                .findFirst()
            if (!nextLineStation.isPresent) {
                break
            }
            downStation = nextLineStation.get().downStation
            stations.add(downStation)
        }
        return stations
    }

    private fun findUpStation(line: Line): Station {
        var downStation = line.getSections()[0].upStation
        while (downStation != null) {
            val finalDownStation = downStation
            val nextLineStation = line.getSections().stream()
                .filter { it.downStation === finalDownStation }
                .findFirst()
            if (!nextLineStation.isPresent) {
                break
            }
            downStation = nextLineStation.get().upStation
        }
        return downStation
    }
}
