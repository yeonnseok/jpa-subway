package woowa.bossdog.subway.domain;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowa.bossdog.subway.service.Member.dto.UpdateMemberRequest;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Member extends BaseEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id")
    private Long id;
    private String email;
    private String name;
    private String password;

    public Member(final String email, final String name, final String password) {
        this(null, email, name, password);
    }

    public Member(final Long id, final String email, final String name, final String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
    }

    public void update(final UpdateMemberRequest request) {
        this.name = request.getName();
        this.password = request.getPassword();
    }
}
