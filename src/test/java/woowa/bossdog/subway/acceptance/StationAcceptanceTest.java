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
import woowa.bossdog.subway.service.station.dto.StationResponse;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Transactional
public class StationAcceptanceTest {

    /**
     * 시나리오
     * 1. 지하철 역을 추가한다. [강남역 - 선릉역 - 역삼역]
     * 2. 전체 지하철 역 목록을 조회한다.
     * 3. 특정 지하철 역의 정보를 상세보기 한다.
     * 4. 특정 지하철 역을 삭제한다.
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
    public void manageStation() {
        createStation("강남역");
        createStation("선릉역");
        createStation("역삼역");

        List<StationResponse> responses = listStations();
        assertThat(responses).hasSize(3);
        assertThat(responses.get(0).getName()).isEqualTo("강남역");
        assertThat(responses.get(1).getName()).isEqualTo("선릉역");
        assertThat(responses.get(2).getName()).isEqualTo("역삼역");

        final StationResponse findStation = findStation(responses.get(1).getId());
        assertThat(findStation.getId()).isEqualTo(responses.get(1).getId());
        assertThat(findStation.getName()).isEqualTo(responses.get(1).getName());

        removeStation(responses.get(1).getId());
        responses = listStations();
        assertThat(responses).hasSize(2);
        assertThat(responses.get(0).getName()).isEqualTo("강남역");
        assertThat(responses.get(1).getName()).isEqualTo("역삼역");
    }

    private void createStation(final String name) {
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

    private List<StationResponse> listStations() {
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

    private StationResponse findStation(final Long id) {
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

    private void removeStation(final Long id) {
        // @formatter:off
        given().
        when().
                delete("/stations/" + id).
        then().
            log().all().
            statusCode(HttpStatus.NO_CONTENT.value());
        // @formatter:on
    }

}
