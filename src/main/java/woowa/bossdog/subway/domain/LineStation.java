package woowa.bossdog.subway.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@Table(name = "line_station")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LineStation extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "line_station_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "line_id")
    private Line line;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "station_id")
    private Station preStation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "station_id")
    private Station station;
    private int distance;
    private int duration;

    public LineStation(final Line line, final Station preStation, final Station station, final int distance, final int duration) {
        this.line = line;
        this.preStation = preStation;
        this.station = station;
        this.distance = distance;
        this.duration = duration;
    }

}
