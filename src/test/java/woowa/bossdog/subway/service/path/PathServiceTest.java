package woowa.bossdog.subway.service.path;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import woowa.bossdog.subway.repository.LineRepository;
import woowa.bossdog.subway.repository.StationRepository;
import woowa.bossdog.subway.service.path.dto.PathRequest;
import woowa.bossdog.subway.service.path.dto.PathResponse;
import woowa.bossdog.subway.service.path.dto.PathType;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class PathServiceTest {

    private PathService pathService;

    @Mock private StationRepository stationRepository;
    @Mock private LineRepository lineRepository;
    @Mock private GraphService graphService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);

        pathService = new PathService(lineRepository, stationRepository, graphService);
    }

    @DisplayName("최단 거리 경로 조회")
    @Test
    void findPathByDistance() {
        // given
        final PathRequest request = new PathRequest("강남역", "서울역", PathType.DISTANCE);

        // when
        final PathResponse response = pathService.findPath(request);

        // then
        assertThat(response.getStations().get(0).getName()).isEqualTo("강남역");
        assertThat(response.getStations().get(1).getName()).isEqualTo("선릉역");
        assertThat(response.getStations().get(2).getName()).isEqualTo("사당역");
        assertThat(response.getStations().get(3).getName()).isEqualTo("서울역");
        assertThat(response.getDistance()).isEqualTo(30);
        assertThat(response.getDuration()).isEqualTo(30);


    }
}