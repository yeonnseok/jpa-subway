package woowa.bossdog.subway.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Line extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "line_id")
    private Long id;

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private int intervalTime;

    @OneToMany(mappedBy = "line", cascade = CascadeType.ALL)
    private List<LineStation> lineStations = new ArrayList<>();


    public Line(final String name, final LocalTime startTime, final LocalTime endTime, final int intervalTime) {
        this.name = name;
        this.startTime = startTime;
        this.endTime = endTime;
        this.intervalTime = intervalTime;
    }
}
