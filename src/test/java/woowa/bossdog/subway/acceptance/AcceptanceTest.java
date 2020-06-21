package woowa.bossdog.subway.acceptance;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import woowa.bossdog.subway.service.line.dto.*;
import woowa.bossdog.subway.service.station.dto.StationResponse;

import javax.transaction.Transactional;
import java.time.LocalTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class AcceptanceTest {

    @LocalServerPort
    public int port;

    @BeforeEach
    public void setUp() {
        RestAssured.port = port;
    }

    public static RequestSpecification given() {
        return RestAssured.given().log().all();
    }

    void createLine(final String name, final LocalTime startTime, final LocalTime endTime, final int intervalTime) {
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

    List<LineResponse> listLines() {
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

    void updateLine(final Long id, final UpdateLineRequest updateRequest) {
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

    LineResponse findLine(final Long id) {
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

    void removeLine(final Long id) {
        // @formatter:off
        given().
        when().
                delete("/lines/" + id).
        then().
                log().all().
                statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }

    void createStation(final String name) {
        final Map<String, String> request = new HashMap<>();
        request.put("name", name);

        // @formatter:off
        given().
                body(request).
                contentType(MediaType.APPLICATION_JSON_VALUE).
                accept(MediaType.APPLICATION_JSON_VALUE).
        when().
                post("/stations").
        then().
                log().all().
                statusCode(HttpStatus.CREATED.value());
        // @formatter:on
    }

    List<StationResponse> listStations() {
        // @formatter:off
        return given().
                when().
                        get("/stations").
                then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().
                        jsonPath().
                        getList(".", StationResponse.class);
        // @formatter:on
    }

    StationResponse findStation(final Long id) {
        // @formatter:off
        return given().
                when().
                        get("/stations/" + id).
                then().
                        log().all().
                        statusCode(HttpStatus.OK.value()).
                        extract().
                        as(StationResponse.class);
        // @formatter:on
    }

    void removeStation(final Long id) {
        // @formatter:off
        given().
        when().
                delete("/stations/" + id).
        then().
                log().all().
                statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
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

    void removeLineStation(final Long lineId, final Long stationId) {
        // @formatter:off
        given().
        when().
                delete("/lines/" + lineId + "/stations/" + stationId).
        then().
                log().all().
                statusCode(HttpStatus.OK.value());
        // @formatter:on
    }

    LineDetailResponse findLineWithStations(final Long lineId) {
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
