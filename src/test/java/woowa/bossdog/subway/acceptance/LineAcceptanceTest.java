package woowa.bossdog.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.transaction.annotation.Transactional;
import woowa.bossdog.subway.service.line.dto.LineRequest;
import woowa.bossdog.subway.service.line.dto.LineResponse;
import woowa.bossdog.subway.service.line.dto.UpdateLineRequest;

import java.time.LocalTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class LineAcceptanceTest {

    /**
     * 시나리오
     * 1. 지하철 노선을 추가한다. [2호선, 3호선, 신분당선]
     * 2. 전체 지하철 노선 목록을 조회한다.
     * 3. 특정 지하철 노선의 정보를 상세 보기 한다.
     * 4. 트정 지하철 노선의 정보를 수정한다.
     * 5. 특정 지하철 노선을 삭제한다.
     */

    @LocalServerPort
    public int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    @Test
    public void manageLine() {
        // 1. 노선 생성
        createLine("2호선", LocalTime.of(5, 30), LocalTime.of(23, 30), 10);
        createLine("3호선", LocalTime.of(5, 50), LocalTime.of(23, 50), 12);
        createLine("신분당선", LocalTime.of(5, 10), LocalTime.of(22, 30), 15);

        // 2. 노선 목록 조회
        List<LineResponse> responses = listLines();
        assertThat(responses).hasSize(3);
        assertThat(responses.get(0).getName()).isEqualTo("2호선");
        assertThat(responses.get(1).getName()).isEqualTo("3호선");
        assertThat(responses.get(2).getName()).isEqualTo("신분당선");

        // 3. 노선 단건 조회
        LineResponse findLine = findLine(responses.get(1).getId());
        assertThat(findLine.getId()).isEqualTo(responses.get(1).getId());
        assertThat(findLine.getName()).isEqualTo(responses.get(1).getName());

        // 4. 노선 정보 수정
        UpdateLineRequest updateRequest = new UpdateLineRequest("9호선", LocalTime.of(4, 10), LocalTime.of(11, 40), 9);
        updateLine(responses.get(1).getId(), updateRequest);

        LineResponse updatedLine = findLine(responses.get(1).getId());
        assertThat(updatedLine.getName()).isEqualTo(updateRequest.getName());
        assertThat(updatedLine.getStartTime()).isEqualTo(updateRequest.getStartTime());
        assertThat(updatedLine.getEndTime()).isEqualTo(updateRequest.getEndTime());
        assertThat(updatedLine.getIntervalTime()).isEqualTo(updateRequest.getIntervalTime());

        // 5. 노선 삭제
        removeLine(responses.get(1).getId());
        responses = listLines();
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("2호선");
        assertThat(responses.get(1).getName()).isEqualTo("신분당선");
    }

    private void createLine(final String name, final LocalTime startTime, final LocalTime endTime, final int intervalTime) {
        final LineRequest request = new LineRequest(name, startTime, endTime, intervalTime);

        // @formatter:off
        given().
                body(request).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                post("/lines").
        then().
                log().all().
                statusCode(HttpStatus.CREATED.value());
        // @formatter:on
    }

    private List<LineResponse> listLines() {
        // @formatter:off
        return given().
                when().
                        get("/lines").
                then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().
                        jsonPath().
                        getList(".", LineResponse.class);
        // @formatter:on
    }

    private void updateLine(final Long id, final UpdateLineRequest updateRequest) {
        // @formatter:off
        given().
                body(updateRequest).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                put("/lines/" + id).
        then().
                log().all().
                statusCode(HttpStatus.OK.value());
        // @formatter:on
    }

    private LineResponse findLine(final Long id) {
        // @formatter:off
        return given().
                when().
                        get("/lines/" + id).
                then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().
                        as(LineResponse.class);
        // @formatter:on
    }

    private void removeLine(final Long id) {
        // @formatter:off
        given().
        when().
                delete("/lines/" + id).
        then().
                log().all().
                statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }
}
