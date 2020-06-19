package woowa.bossdog.subway.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import woowa.bossdog.subway.service.line.dto.UpdateLineRequest;

import java.time.LocalTime;

import static org.assertj.core.api.Assertions.assertThat;

class LineTest {

    @DisplayName("지하철 노선 정보 수정")
    @Test
    void update() {
        // given
        Line line = new Line("2호선", LocalTime.of(5,30), LocalTime.of(23,30), 10);
        UpdateLineRequest request = new UpdateLineRequest("신분당선", LocalTime.of(4,30), LocalTime.of(22,30), 15);

        // when
        line.update(request);

        // then
        assertThat(line.getName()).isEqualTo(request.getName());
        assertThat(line.getStartTime()).isEqualTo(request.getStartTime());
        assertThat(line.getEndTime()).isEqualTo(request.getEndTime());
        assertThat(line.getIntervalTime()).isEqualTo(request.getIntervalTime());
    }



}