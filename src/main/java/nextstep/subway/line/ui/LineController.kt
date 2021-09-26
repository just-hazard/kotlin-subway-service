package nextstep.subway.line.ui

import nextstep.subway.line.application.LineService
import nextstep.subway.line.dto.LineRequest
import nextstep.subway.line.dto.LineResponse
import nextstep.subway.line.dto.SectionRequest
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.net.URI

@RestController
@RequestMapping("/lines")
class LineController(private val lineService: LineService) {
    @PostMapping
    fun createLine(@RequestBody lineRequest: LineRequest?): ResponseEntity<*> {
        val line = lineService.saveLine(lineRequest!!)
        return ResponseEntity.created(URI.create("/lines/" + line.id)).body(line)
    }

    @GetMapping
    fun findAllLines(): ResponseEntity<List<LineResponse>> {
        return ResponseEntity.ok(lineService.findLines())
    }

    @GetMapping("/{id}")
    fun findLineById(@PathVariable id: Long?): ResponseEntity<LineResponse> {
        return ResponseEntity.ok(lineService.findLineResponseById(id!!))
    }

    @PutMapping("/{id}")
    fun updateLine(@PathVariable id: Long?, @RequestBody lineUpdateRequest: LineRequest?): ResponseEntity<*> {
        lineService.updateLine(id!!, lineUpdateRequest!!)
        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/{id}")
    fun deleteLine(@PathVariable id: Long?): ResponseEntity<*> {
        lineService.deleteLineById(id!!)
        return ResponseEntity.noContent().build<Any>()
    }

    @PostMapping("/{lineId}/sections")
    fun addLineStation(@PathVariable lineId: Long?, @RequestBody sectionRequest: SectionRequest?): ResponseEntity<*> {
        lineService.addLineStation(lineId!!, sectionRequest!!)
        return ResponseEntity.ok().build<Any>()
    }

    @DeleteMapping("/{lineId}/sections")
    fun removeLineStation(@PathVariable lineId: Long?, @RequestParam stationId: Long): ResponseEntity<*> {
        lineService.removeLineStation(lineId!!, stationId)
        return ResponseEntity.ok().build<Any>()
    }

    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleIllegalArgsException(e: DataIntegrityViolationException?): ResponseEntity<*> {
        return ResponseEntity.badRequest().build<Any>()
    }
}
