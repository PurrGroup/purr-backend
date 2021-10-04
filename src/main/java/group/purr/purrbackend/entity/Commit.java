package group.purr.purrbackend.entity;

import lombok.*;
import org.hibernate.Hibernate;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Date;
import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@DynamicInsert
@DynamicUpdate
public class Commit {
    @Id
    @Column(columnDefinition = "DATE")
    private Date commitDate;

    @Column(columnDefinition = "DATETIME")
    private Date commitTime;

    @Column(columnDefinition = "INTEGER UNSIGNED")
    private Integer commitCount;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Commit commit = (Commit) o;
        return Objects.equals(commit, commit.commitDate);
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
