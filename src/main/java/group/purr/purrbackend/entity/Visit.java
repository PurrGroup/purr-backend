package group.purr.purrbackend.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class Visit {
    @Id
    @Column(columnDefinition = "DATE")
    private Date visitDate;

    @Column(columnDefinition = "DATETIME")
    private Date visitTime;

    @Column(columnDefinition = "INTEGER UNSIGNED")
    private Integer visitCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Visit visit = (Visit) o;
        return Objects.equals(visitDate, visit.visitDate);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
