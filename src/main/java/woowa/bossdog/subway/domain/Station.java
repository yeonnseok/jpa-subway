package woowa.bossdog.subway.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Station extends BaseEntity {

    @Id @GeneratedValue
    @Column(name = "station_id")
    private Long id;

    private String name;

    public Station(final String name) {
        this.name = name;
    }
}
