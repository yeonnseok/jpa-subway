package woowa.bossdog.subway.service.line;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import woowa.bossdog.subway.domain.Line;
import woowa.bossdog.subway.repository.LineRepository;
import woowa.bossdog.subway.service.line.dto.LineRequest;
import woowa.bossdog.subway.service.line.dto.LineResponse;
import woowa.bossdog.subway.service.line.dto.UpdateLineRequest;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LineService {

    private final LineRepository lineRepository;

    @Transactional
    public LineResponse createLine(final LineRequest request) {
        final Line line = request.toLine();
        lineRepository.save(line);
        return LineResponse.from(line);
    }

    public List<LineResponse> listLines() {
        return lineRepository.findAll().stream()
                .map(LineResponse::from)
                .collect(Collectors.toList());
    }

    public LineResponse findLine(final Long id) {
        final Line line = lineRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        return LineResponse.from(line);
    }

    @Transactional
    public void updateLine(final Long id, final UpdateLineRequest request) {
        final Line line = lineRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
        line.update(request);
    }

    @Transactional
    public void deleteLine(final Long id) {
        lineRepository.deleteById(id);
    }
}
