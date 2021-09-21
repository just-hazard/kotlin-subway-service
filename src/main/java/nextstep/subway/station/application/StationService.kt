package nextstep.subway.station.application

import nextstep.subway.station.domain.StationRepository
import nextstep.subway.station.dto.StationRequest
import nextstep.subway.station.dto.StationResponse
import nextstep.subway.station.domain.Station
import org.springframework.stereotype.Service
import java.util.stream.Collectors
import java.lang.RuntimeException

@Service
class StationService(private val stationRepository: StationRepository) {
    fun saveStation(stationRequest: StationRequest): StationResponse {
        val persistStation = stationRepository.save(stationRequest.toStation())
        return StationResponse.of(persistStation)
    }

    fun findAllStations(): List<StationResponse> {
        val stations = stationRepository.findAll()
        return stations.stream()
            .map { station: Station? -> StationResponse.of(station) }
            .collect(Collectors.toList())
    }

    fun deleteStationById(id: Long) {
        stationRepository.deleteById(id)
    }

    fun findStationById(id: Long): Station {
        return stationRepository.findById(id).orElseThrow { RuntimeException() }
    }

    fun findById(id: Long): Station {
        return stationRepository.findById(id).orElseThrow { RuntimeException() }
    }
}
