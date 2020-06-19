package woowa.bossdog.subway.service.station.dto;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import woowa.bossdog.subway.domain.Station;

@Data
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class StationResponse {
    private Long id;
    private String name;

    private StationResponse(final Long id, final String name) {
        this.id = id;
        this.name = name;
    }

    public static StationResponse from(Station station) {
        return new StationResponse(station.getId(), station.getName());
    }

}
