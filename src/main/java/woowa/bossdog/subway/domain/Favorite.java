package woowa.bossdog.subway.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Favorite {

    @Id @GeneratedValue
    @Column(name = "favorite_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "station_id")
    private Station sourceStation;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "station_id")
    private Station targetStation;

    public Favorite(final Member member, final Station sourceStation, final Station targetStation) {
        this.member = member;
        this.sourceStation = sourceStation;
        this.targetStation = targetStation;
    }
}
