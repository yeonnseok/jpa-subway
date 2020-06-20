package woowa.bossdog.subway.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowa.bossdog.subway.service.line.LineService;
import woowa.bossdog.subway.service.line.dto.*;

import java.net.URI;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lines")
public class LineApiController {

    private final LineService lineService;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody LineRequest request) {
        final LineResponse lineResponse = lineService.createLine(request);
        return ResponseEntity
                .created(URI.create("/lines/" + lineResponse.getId()))
                .build();
    }

    @GetMapping
    public ResponseEntity<List<LineResponse>> list() {
        return ResponseEntity.ok().body(lineService.listLines());
    }

    @GetMapping("/{id}")
    public ResponseEntity<LineResponse> find(@PathVariable("id") Long id) {
        return ResponseEntity.ok().body(lineService.findLine(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable("id") Long id,
            @RequestBody UpdateLineRequest updateRequest
    ) {
        lineService.updateLine(id, updateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        lineService.deleteLine(id);
        return ResponseEntity.noContent().build();
    }

    /*
    구간 추가
     */
    @PostMapping("/{id}/stations")
    public ResponseEntity<Void> addLineStation(
            @PathVariable("id") Long id,
            @RequestBody LineStationRequest request
    ) {
        lineService.addLineStation(id, request);
        return ResponseEntity.ok().build();
    }

    /*
    구간 삭제
     */
    @DeleteMapping("/{id}/stations/{stationId}")
    public ResponseEntity<Void> deleteLineStation(
            @PathVariable("id") Long id,
            @PathVariable("stationId") Long stationId
    ) {
        lineService.deleteLineStation(id, stationId);
        return ResponseEntity.ok().build();
    }

    /*
    구간 포함 디테일 노선
     */
    @GetMapping("/{id}/stations")
    public ResponseEntity<LineDetailResponse> findLineDetail(
        @PathVariable("id") Long id
    ) {
        final LineDetailResponse response = lineService.findLineDetail(id);
        return ResponseEntity.ok().body(response);
    }

    /*
    모든 구간 포함 전체 노선
     */
    @GetMapping("/detail")
    public ResponseEntity<WholeSubwayResponse> listLineDetail() {
        WholeSubwayResponse response = lineService.listLineDetail();
        return ResponseEntity.ok().body(response);
    }
}
