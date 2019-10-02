package org.blackdread.lib.restfilter.demo;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import java.math.BigDecimal;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.util.Objects;
import java.util.UUID;

@MappedSuperclass
public class BaseEntity {
    @Id
    @GeneratedValue
    Long id;

    @Column(nullable = false)
    String name;

    @Column(nullable = false)
    Instant createTime;

    @Column(nullable = false)
    BigDecimal total;

    @Column(nullable = true)
    Integer count;

    @Column(nullable = false)
    LocalDate localDate;

    @Column(nullable = false)
    Short aShort;

    @Column(nullable = false)
    Boolean active;

    @Column(nullable = false)
    UUID uuid;

    @Column(nullable = false)
    Duration duration;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BaseEntity that = (BaseEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
