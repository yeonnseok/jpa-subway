package woowa.bossdog.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowa.bossdog.subway.domain.Station;

public interface StationRepository extends JpaRepository<Station, Long> {

}

