package woowa.bossdog.subway.domain;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import java.time.LocalDateTime;

@MappedSuperclass
public class BaseEntity {

    @CreatedDate
    @Column(updatable = false)
    public LocalDateTime createdAt;

    @LastModifiedDate
    public LocalDateTime updatedAt;
}
