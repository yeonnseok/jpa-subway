package woowa.bossdog.subway.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowa.bossdog.subway.service.line.LineService;
import woowa.bossdog.subway.service.line.dto.LineRequest;
import woowa.bossdog.subway.service.line.dto.LineResponse;
import woowa.bossdog.subway.service.line.dto.UpdateLineRequest;

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
            @RequestBody UpdateLineRequest updateRequest) {
        lineService.updateLine(id, updateRequest);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        lineService.deleteLine(id);
        return ResponseEntity.noContent().build();
    }
}
