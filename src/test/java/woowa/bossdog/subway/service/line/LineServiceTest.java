package woowa.bossdog.subway.service.line;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import woowa.bossdog.subway.domain.Line;
import woowa.bossdog.subway.repository.LineRepository;
import woowa.bossdog.subway.service.line.dto.LineRequest;
import woowa.bossdog.subway.service.line.dto.LineResponse;
import woowa.bossdog.subway.service.line.dto.UpdateLineRequest;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@SpringBootTest
class LineServiceTest {

    private LineService lineService;

    @Mock
    private LineRepository lineRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        lineService = new LineService(lineRepository);
    }

    @DisplayName("지하철 노선 추가")
    @Test
    void createLine() {
        // given
        final Line line = new Line("2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);
        given(lineRepository.save(any())).willReturn(line);

        // when
        final LineResponse response = lineService.createLine(new LineRequest("2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10));

        // then
        verify(lineRepository).save(any());
        assertThat(response.getId()).isEqualTo(line.getId());
        assertThat(response.getName()).isEqualTo(line.getName());
    }

    @DisplayName("지하철 노선 목록 조회")
    @Test
    void listLine() {
        // given
        final List<Line> lines = new ArrayList<>();
        lines.add(new Line("2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10));
        lines.add(new Line("3호선", LocalTime.of(5, 50), LocalTime.of(23, 50), 8));
        given(lineRepository.findAll()).willReturn(lines);

        // when
        final List<LineResponse> responses = lineService.listLines();

        // then
        verify(lineRepository).findAll();
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("2호선");
        assertThat(responses.get(1).getName()).isEqualTo("3호선");
    }

    @DisplayName("지하철 노선 단건 조회")
    @Test
    void findLine() {
        // given
        final Line line = new Line(1L, "2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);
        given(lineRepository.findById(any())).willReturn(Optional.of(line));

        // when
        final LineResponse response = lineService.findLine(line.getId());

        // then
        verify(lineRepository).findById(eq(1L));
        assertThat(response.getId()).isEqualTo(line.getId());
        assertThat(response.getName()).isEqualTo(line.getName());
        assertThat(response.getStartTime()).isEqualTo(line.getStartTime());
        assertThat(response.getEndTime()).isEqualTo(line.getEndTime());
        assertThat(response.getIntervalTime()).isEqualTo(line.getIntervalTime());
    }

    @DisplayName("지하철 노선 정보 수정")
    @Test
    void updateLine() {
        // given
        Line line = new Line(10L,"2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);
        UpdateLineRequest updateRequest = new UpdateLineRequest("신분당선", LocalTime.of(6, 30), LocalTime.of(22, 30), 15);

        Line updatedLine = new Line("신분당선", LocalTime.of(6, 30), LocalTime.of(22, 30), 15);
        given(lineRepository.findById(any())).willReturn(Optional.of(updatedLine));

        // when
        lineService.updateLine(line.getId(), updateRequest);
        final Line findLine = lineRepository.findById(line.getId())
                .orElseThrow(NoSuchElementException::new);

        // then
        assertThat(findLine.getName()).isEqualTo(updatedLine.getName());
        assertThat(findLine.getStartTime()).isEqualTo(updatedLine.getStartTime());
        assertThat(findLine.getEndTime()).isEqualTo(updatedLine.getEndTime());
        assertThat(findLine.getIntervalTime()).isEqualTo(updatedLine.getIntervalTime());
    }

    @DisplayName("지하철 노선 삭제")
    @Test
    void removeLine() {
        // given
        final Line line = new Line(1L, "2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);

        // when
        lineService.deleteLine(line.getId());

        // then
        verify(lineRepository).deleteById(eq(1L));
    }

}