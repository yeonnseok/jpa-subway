package woowa.bossdog.subway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import woowa.bossdog.subway.domain.Member;

public interface MemberRepository extends JpaRepository<Member, Long> {
}
