package woowa.bossdog.subway.service.station.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import woowa.bossdog.subway.domain.Station;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationRequest {
    private String name;

    public StationRequest(final String name) {
        this.name = name;
    }

    public static StationRequest from(final Station station) {
        return new StationRequest(station.getName());
    }
}