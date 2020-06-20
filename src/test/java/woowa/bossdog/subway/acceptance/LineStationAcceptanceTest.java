package woowa.bossdog.subway.acceptance;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowa.bossdog.subway.service.line.dto.LineDetailResponse;
import woowa.bossdog.subway.service.line.dto.LineResponse;
import woowa.bossdog.subway.service.line.dto.LineStationRequest;
import woowa.bossdog.subway.service.station.dto.StationResponse;

import java.time.LocalTime;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

public class LineStationAcceptanceTest extends AcceptanceTest {

    /**
     * 시나리오
     * 1. 지하철 역을 추가한다. [강남역, 선릉역, 역삼역, 삼성역]
     * 2. 지하철 노선을 추가한다. [2호선]
     * 3. 구간을 추가한다. [2호선-강남역-선릉역, 2호선-선릉역-역삼역, 2호선-역삼역-삼성역]
     * 4. 특정 구간을 삭제한다.
     * 5. 데이터 롤백
     */

    @DisplayName("지하철 구간 관리")
    @Test
    void manageLineStation() {

        // 1. 지하철 역을 추가한다.
        createStation("강남역");
        createStation("선릉역");
        createStation("역삼역");

        // 2. 지하철 노선을 추가한다.
        createLine("2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);

        // 3. 구간을 추가한다.
        List<LineResponse> lineResponses = listLines();
        Long 이호선 = lineResponses.get(0).getId();

        List<StationResponse> stationResponses = listStations();
        Long 강남역 = stationResponses.get(0).getId();
        Long 선릉역 = stationResponses.get(1).getId();
        Long 역삼역 = stationResponses.get(2).getId();

        addLineStation(이호선, null, 강남역, 10, 10);
        addLineStation(이호선, 강남역, 선릉역, 10, 10);
        addLineStation(이호선, 선릉역, 역삼역, 10, 10);

        // 4. 구간을 삭제한다.
        removeLineStation(이호선, 선릉역);

        LineDetailResponse response = findLineWithStations(이호선);
        assertThat(response.getStations()).hasSize(2);
        assertThat(response.getStations().get(0).getName()).isEqualTo("강남역");
        assertThat(response.getStations().get(1).getName()).isEqualTo("역삼역");

        // 5. 데이터 롤백
        removeLine(lineResponses.get(0).getId());
        IntStream.range(0, 3)
                .forEach(i -> removeStation(stationResponses.get(i).getId()));
    }

    void addLineStation(final Long lineId, final Long preStationId, final Long stationId, final int distance, final int duration) {
        LineStationRequest request = new LineStationRequest(preStationId, stationId, distance, duration);

        // @formatter:off
        given().
                body(request).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                post("/lines/" + lineId + "/stations").
        then().
                log().all().
                statusCode(HttpStatus.OK.value());
        // @formatter:on
    }

    private void removeLineStation(final Long lineId, final Long stationId) {
        // @formatter:off
        given().
        when().
                delete("/lines/" + lineId + "/stations/" + stationId).
        then().
                log().all().
                statusCode(HttpStatus.OK.value());
        // @formatter:on
    }

    private LineDetailResponse findLineWithStations(final Long lineId) {
        // @formatter:off
        return given().
                when().
                        get("/lines/" + lineId + "/stations").
                then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().as(LineDetailResponse.class);
        // @formatter:on
    }

}
